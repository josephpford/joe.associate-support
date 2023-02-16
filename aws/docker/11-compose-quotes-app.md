# Exercise: Compose Quotes App

## Goals

- Create a Docker image from an existing Spring Boot REST API.
- Create a Docker image from an existing React application.
- Define Docker Compose services.
- Run Compose services.

## Set Up

<ol>
<li>
<blockquote class="icon-block-file-download">
Download the <a href="assets/quotes-app-stack.zip" download>Quotes application stack</a>.
</blockquote>
</li>
</li>
<li>Extract the zip contents. Inside you'll find a database schema, a Spring REST API, and a React application.</li>
</ol>

## Quotes App

The Quotes App is a bare-bones CRUD application that tracks famous quotes. It contains a Spring API, a React UI, and a database initialization script. Scan the directory structure below.

```
quotes-app-stack
├───database
│       schema-and-data.sql # SQL schema & data
│
├───quote-api               # Spring Boot REST API
│   │
│   ├───http                # HTTP test requests
│   │
│   └───src                 # API source code.
│
└───quote-ui                # CRA React UI
    │
    ├───public              # public assets
    │
    └───src                 # React source code.
```

## Phase 1: Test Locally

We've had some practice testing applications locally. Using course or external resources, run the Quotes App locally. 

Revisit:
- _Exercise: Build a Spring Boot Docker Image_
- _Exercise: Build a React Docker Image_

## Phase 2: Build the Image

We've had some practice building Docker images. Using course or external resources, create a `.dockerignore` file and a Dockerfile for each project. Use them to build a Spring API image and a React image.

Revisit:
- _Exercise: Build a Spring Boot Docker Image_
- _Exercise: Build a React Docker Image_
- _Lesson: Docker Compose_

**Stretch Goal**

Create a Dockerfile for a database image. Pre-populate a new container's database with schema and data.

## Phase 3: Docker Compose

Create a `docker-compose.yml` file and define two or three services:

1. `db` (stretch goal): self-contained database
2. `api`: Spring API
3. `ui`: React UI

Configure services appropriately. Service configuration should include:
- Environment variables
- Port publishing (don't expose ports to the host that don't need to be)
- `depends_on` for correct start up order
- `restart` to ensure the `api` doesn't fail and go away forever

**Stretch Goal**

Define a volume to be used by the database service. If a database service container is removed, the volume's data should persist.

### `image` or `build`

Services may be launched with either the `image` or `build` element. With `image`, Docker images must already exist. With `build`, images can be built on the fly. Consider trying both approaches.

### `up` and `down`

Start all services with `docker compose up -d`.

If you run into issues, troubleshoot with logging: `docker compose logs`.

Shut down with `docker compose down`.