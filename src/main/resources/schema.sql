DROP TABLE IF EXISTS statistics;
DROP TABLE IF EXISTS type_definitions;
DROP TABLE IF EXISTS objects;
DROP TABLE IF EXISTS relationships;
DROP TABLE IF EXISTS acls;
DROP TABLE IF EXISTS retention;
DROP TABLE IF EXISTS policies;
DROP TABLE IF EXISTS versions;
DROP TABLE IF EXISTS metadata;
DROP TABLE IF EXISTS folders;
DROP TABLE IF EXISTS documents;

CREATE TABLE documents (
                           id VARCHAR(36) PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           mime_type VARCHAR(100) NOT NULL,
                           content BLOB,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL
);

CREATE TABLE folders (
                         id VARCHAR(36) PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         parent_id VARCHAR(36),
                         created_at TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP NOT NULL
);

CREATE TABLE metadata (
                          id VARCHAR(36) PRIMARY KEY,
                          object_id VARCHAR(36) NOT NULL,
                          property_name VARCHAR(255) NOT NULL,
                          property_value VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP NOT NULL
);

CREATE TABLE versions (
                          id VARCHAR(36) PRIMARY KEY,
                          object_id VARCHAR(36) NOT NULL,
                          version_label VARCHAR(50) NOT NULL,
                          latest BOOLEAN NOT NULL,
                          created_at TIMESTAMP NOT NULL
);

CREATE TABLE policies (
                          id VARCHAR(36) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description VARCHAR(1000),
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP NOT NULL
);

CREATE TABLE retention (
                           id VARCHAR(36) PRIMARY KEY,
                           object_id VARCHAR(36) NOT NULL,
                           retention_policy_name VARCHAR(255) NOT NULL,
                           retain_until TIMESTAMP NOT NULL,
                           created_at TIMESTAMP NOT NULL
);

CREATE TABLE acls (
                      id VARCHAR(36) PRIMARY KEY,
                      object_id VARCHAR(36) NOT NULL,
                      principal VARCHAR(255) NOT NULL,
                      permission VARCHAR(100) NOT NULL,
                      granted BOOLEAN NOT NULL,
                      created_at TIMESTAMP NOT NULL
);

CREATE TABLE relationships (
                               id VARCHAR(36) PRIMARY KEY,
                               source_id VARCHAR(36) NOT NULL,
                               target_id VARCHAR(36) NOT NULL,
                               relation_type VARCHAR(100) NOT NULL,
                               created_at TIMESTAMP NOT NULL
);

CREATE TABLE objects (
                         id VARCHAR(36) PRIMARY KEY,
                         type VARCHAR(100) NOT NULL,
                         name VARCHAR(255) NOT NULL,
                         created_at TIMESTAMP NOT NULL
);

CREATE TABLE type_definitions (
                                  id VARCHAR(36) PRIMARY KEY,
                                  base_type VARCHAR(100) NOT NULL,
                                  type_id VARCHAR(100) NOT NULL,
                                  display_name VARCHAR(255) NOT NULL,
                                  description VARCHAR(1000),
                                  created_at TIMESTAMP NOT NULL
);

CREATE TABLE statistics (
                            id VARCHAR(36) PRIMARY KEY,
                            metric_name VARCHAR(255) NOT NULL,
                            metric_value DOUBLE PRECISION NOT NULL,
                            created_at TIMESTAMP NOT NULL
);
