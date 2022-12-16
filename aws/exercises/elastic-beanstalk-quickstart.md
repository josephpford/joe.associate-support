# Quick-start: Deploy to Elastic Beanstalk

## Introduction

This quick-start is a checklist of tasks for deploying an application stack consisting of a React client, Spring Boot API backend, and a MySQL database.

## Goals

* Deploy MySQL database to RDS.
* Deploy Spring Boot API as a docker container to Elastic Beanstalk.
* Deploy React UI as a docker container to Elastic Beanstalk.

## Database

* Create an RDS MySQL instance.
* Consider security, options:
  * Create a bastion host and leave public access off.
  * Allow public access but limit access to your IP address only.
* Once the Database is up, remove public access or the bastion host.
* Create a security group that the API can use and whitelist incoming traffic in the DB instance security group.

## Spring Boot API

### Prepare Artifact

Configure the Spring Boot Application for deployment. Test and validate between each step.

1. Add the Maven build plugin to the `pom.xml`.
2. Configure database connection properties as environment variables.
3. Add a REST endpoint for the AWS health check. This route must be unsecured.
4. Configure CORS to allow all origins.
5. Build a docker image.
6. Push the image to Dockerhub.
7. Create a `docker-compose.yml` file, and configure the API as a service.

### Deploy

Create a new Elastic Beanstalk environment with the following properties.

* Web server environment.
* Docker platform.
* Upload the `docker-compose.yml` file.
* Configure EC2 instances to use the API access security group whitelisted by the RDS security group.
* Configure the load balancer to forward port 80 (listener) to 8080 (process).
* Configure the load balancer to use the correct health check URL.

Test the deployed application with a `.http` file.

#### Troubleshooting

Check the logs in Elastic Beanstalk for clues. Some common issues:

* Database access is not correctly configured. Check the security groups.
* Load balancer is not configured correctly.

## React UI

Configure the React application for deployment. Test and validate between each step.

1. The API URL must be configurable. Solve this with an environment-specific build (`.env` files and `process.env.API_URL`) or using an environment variable in the Docker container (using a global `const API_URL` and a `.sh` script to overwrite at runtime). make sure there are no hard-coded references to `http://localhost...`.
2. Add an `nginx.conf` file to serve `index.html` as a fallback.
3. Build a docker image.
4. Push the image to Dockerhub.
5. Create a `docker-compose.yml` file, and configure the UI as a service. Set API_URL to your deployed API URL.

### Deploy

Create a new Elastic Beanstalk environment with the following properties.

* Web server environment.
* Docker platform.
* Upload the `docker-compose.yml` file.

#### Troubleshooting

Check the browser dev tools, _Network_ tab.

Common issues:

* CORS not configured for all origins.
* Hard-coded reference to `localhost`.
