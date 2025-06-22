variable "DOCKER_IMAGE" {
  type        = string
  description = "The docker image used for task."
}

variable "PROFILE" {
  type        = string
  description = "The active spring profile."
}

job "cmis-mock" {
  datacenters = ["dc-ur"]

  group "cmis" {

    count = 1

    network {
      port "http" {}
    }

    task "broker" {
      driver = "docker"

      config {
        ports = ["http"]
        image = var.DOCKER_IMAGE
      }

      env {
        SPRING_PROFILES_ACTIVE = var.PROFILE
      }

      resources {
        cpu    = 250 # MHz
        memory = 512 # MB
      }

      service {
        # This tells Consul to monitor the service on the port
        # labelled "http". Since Nomad allocates high dynamic port
        # numbers, we use labels to refer to them.

        name = "cmis-mock"
        port = "http"

        tags = [
          "traefik.enable=true",
          "traefik.http.routers.cmis-mock.rule=Host(`cmis-mock.theproxy.home`)",
          "cmis-mock",
        ]

        check {
          type     = "http"
          protocol = "http"
          path     = "/actuator/health"
          interval = "10s"
          timeout  = "2s"
        }
      }
    }
  }
}