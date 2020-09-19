# Spring Boot Multi Auth
> This is spring boot basic auth,jwt auth,ouath2 auth and no auth example.
>
<img src="https://github.com/susimsek/spring-boot-multi-auth-ex/blob/master/images/spring-boot-multi-auth-ex.png" alt="Spring Boot Multi Auth Example" width="75%" height="75%"/> 

## Prerequisites

* Docker 19.03.x
* Docker Compose 1.25.x

## Installation
```sh
sudo bash -c 'cat << EOF >> /etc/hosts
127.0.0.1 keycloak
EOF'
```

```sh
docker-compose up -d
```

## Swagger

> You can access the swagger from the following url.

http://localhost:9090/api/documentation/swagger-ui/

<img src="https://github.com/susimsek/spring-boot-multi-auth-ex/blob/master/images/swagger.png" alt="Spring Boot Swagger" width="75%" height="75%"/>



## Notes

> Keycloak access must be 'keycloak' instead of localhost for token request

## Used Technologies

* Spring Boot 2.3.3
* Keycloak
* Postgresql
* Swagger
* Spring Boot Web
* Spring Boot Security
* Spring Boot Security Oauth2
* Spring Boot Validation
* Spring Boot Jpa
* Spring Boot Actuator
* Jjwt
* Model Mapper
* Lombok
* Dev Tools

