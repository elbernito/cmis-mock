variable "DOCKER_IMAGE" {
  type        = string
  description = "The Docker image used for the task."
}

variable "PROFILE" {
  type        = string
  description = "The active Spring profile."
}

job "cmis-mock" {
  datacenters = ["dc-ur"]
  type        = "service"

  group "cmis" {
    count = 1

    network {
      mode = "bridge"
      port "http" { to = 8080 }
    }

    restart {
      attempts = 0
      interval = "5s"
      delay    = "0s"
      mode     = "fail"
    }

    reschedule {
      attempts       = 0
      delay          = "0s"
      delay_function = "constant"
      unlimited      = false
    }

    update {
      max_parallel     = 1
      health_check     = "checks"
      min_healthy_time = "5s"
      healthy_deadline = "60s"
      auto_revert      = false
      stagger          = "5s"
    }

    task "deployment" {
      driver = "docker"

      config {
        image = var.DOCKER_IMAGE
        ports = ["http"]
      }

      env {
        SPRING_PROFILES_ACTIVE = var.PROFILE
      }

      resources {
        cpu    = 250
        memory = 512
      }

      service {
        name = "cmis-mock"
        port = "http"

        tags = [
          "traefik.enable=true",
          "traefik.http.routers.cmis-mock.rule=Host(`cmis-mock.theproxy.home`)",
          "cmis-mock"
        ]

        check {
          type           = "http"
          protocol       = "http"
          path           = "/actuator/health"
          interval       = "3s"
          timeout        = "2s"
          port           = "http"
        }
      }
    }
  }
}
