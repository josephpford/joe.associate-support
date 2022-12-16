# Lesson: Image Creation with Dockerfiles

## Introduction

A Dockerfile allows us to create images that run our applications. That's essential.

It's great that we can manage official images and create containers from them. But without our own image, there's no reliable way to spin up containers that host our applications. Sure, we might be able to copy our application artifacts with `docker cp` and then configure our application by logging into the container with `docker exec`, but those are _container_ commands. It's not practical to install our application one container at a time. 

In production Docker environments, containers are always being created and destroyed. They're ephemeral. If 25 containers are created in the middle of the night, it's impossible to install our application on all of them. Even if a single container is created in the middle of the day, who would know to attend to the new container and perform an install? A custom image gives us a reliable way to create containers. No one has to attend to it.

The `docker build` command creates a new image based on a Dockerfile specification. Dockerfiles have a formal syntax. They have instructions: `FROM`, `COPY`, `RUN`, `CMD`, and more, that extend and alter an existing image to install our application. The `build` command also supports `.dockerignore` files. The `.dockerignore` file has its own formal syntax for ignoring files and directories during the build process.

### Outcomes

When you've finished this lesson and its exercises, you should be able to:
- create a custom image from a Dockerfile
- copy files, run shell commands, and set the starting application for a new image
- ignore files and directories with a `.dockerignore` file
- understand and use Dockerfile instructions

## Creating an Image

We need two things to create our own image.

### 1. A Dockerfile

A Dockerfile is a text document, just like code. By convention, it's named `Dockerfile`, without a file extension. If the file is not named `Dockerfile`, we can specify a different file name in the `docker build` command. The name is flexible.

A Dockerfile is a series of instructions and comments. Comments start with the `#` symbol and instructions start with a Dockerfile instruction name. Instructions and comments should not have leading whitespace. It's allowed, but not idiomatic.

```Dockerfile
# This line is a comment.
# Empty lines are allowed to chunk our thinking.

# FROM specifies a base image. It's the image starting point.
FROM alpine:3.15

# RUN executes a shell command. It's part of image creation, not container creation.
RUN echo 'This happens during image construction.'

# WORKDIR sets a directory as the working directory
# All instructions from here on will execute from the context of this directory.
# If the directory doesn't exist. It creates it.
WORKDIR /home/app

# CMD specifies the command or executable to run when
# the container first runs.
CMD [ "echo", "This happens the first time a container is run."]
```

### 2. `docker build`

<a class="icon-book" href="https://docs.docker.com/engine/reference/commandline/build/">docker build</a>

The second requirement for a custom image is the `docker build` command. `docker build` will construct a new image based on a Dockerfile.

Syntax:

<pre class="console" noheader>
docker build [options] path | url | -
</pre>

The phrase "` path | url | - `" means a path _or_ a URL _or_ the dash symbol. In this course, path is our go-to approach. The path argument is a relative or absolute path that specifies where to find the Dockerfile and any other files required to build the image. The path specifies a **build context**. A **context** is a whole directory, with children, that's passed along to the `docker build` command.

A URL can be a Git repository or a tarball download.

The dash option indicates there's no path or URL and the Dockerfile is provided via stdin, without a context.

We can also provide zero or more `[options]`. Build options allow us to specify a Dockerfile explicitly, bypass the build cache, always pull the latest version of an image, and more.

#### Walk-through Preview

<ol>
<li>Create a directory to hold your Dockerfiles.</li>
<li>Open your favorite text editor.</li>
<li>Create a new file named <code>Dockerfile</code> and save it to your directory.</li>
<li>Paste the contents of the Dockerfile above into <code>Dockerfile</code> and save it.</li>
<li>Open your terminal. Browser to your directory.</li>
<li>Execute <code>docker build .</code>. Note that the dot (<code>.</code>) is required. It means load everything in the current directory recursively. This is the most barebones <code>docker build</code> command.
<pre class="console" noheader>
> docker build .
[+] Building 1.1s (7/7) FINISHED
 => [internal] load build definition from Dockerfile                                                               0.1s
 => => transferring dockerfile: 516B                                                                               0.0s
 => [internal] load .dockerignore                                                                                  0.1s
 => => transferring context: 2B                                                                                    0.0s
 => [internal] load metadata for docker.io/library/alpine:3.15                                                     0.0s
 => [1/3] FROM docker.io/library/alpine:3.15                                                                       0.0s
 => [2/3] RUN echo 'This happens during image construction.'                                                       0.4s
 => [3/3] WORKDIR /home/app                                                                                        0.2s
 => exporting to image                                                                                             0.2s
 => => exporting layers                                                                                            0.2s
 => => writing image sha256:7a397d2324388a6c09ea2f35d424bbbca39aecef8453f1eabf29cb1f232f3af8                       0.0s
</pre>
</li>
<li>Now check your images. The image we created doesn't have a name/repository or a tag!
<pre class="console" noheader>
> docker images
REPOSITORY                       TAG           IMAGE ID       CREATED         SIZE
&lt;none&gt;                           &lt;none&gt;        7a397d232438   5 minutes ago   5.59MB
docker/getting-started           latest        bd9a9f733898   5 weeks ago     28.8MB
debian-apache                    1             7d13be5a1cd5   5 weeks ago     252MB
mongo                            latest        5285cb69ea55   6 weeks ago     698MB
</pre>
</li>
<li>That doesn't matter much. We can still run it using its image ID. Your image ID is unique so be sure to use it, not the lesson's image ID.
<pre class="console" noheader>
> docker run --rm 7a397d232438
This happens when a container is run.
</pre>
We see the results of our <code>CMD</code> instruction!
</li>
<li>When we're through, get rid of the image. It's not particularily useful.
<pre class="console" noheader>
> docker rmi 7a397d232438
Deleted: sha256:681d7f0e63da96d32d3af4cacec47c674011fb83cda936c8eccd2e0c5fc7e52f
</pre>
</li>
</ol>

## Dockerfile Instructions

The Dockerfile specification is a miniature programming language. **Instructions** are labeled actions that reference a base image (`FROM`), set variables (`ARG`, `ENV`), or extend a base image to make a new image.

The instructions `ADD`, `COPY`, and `RUN` create new layers. That's why developers try to pack as many things as they can into a single instruction. There's a limit on image layers.

The spec contains 18 instructions. At least one, `MAINTAINER`, is deprecated. A few others are uncommon. The instructions that follow are required or useful for building images for our applications.

### FROM

The `FROM` instruction needs to come early in the Dockerfile. The only instruction allowed before it is `ARG`.  `FROM` establishes the base image. Every new image requires a base image, even if it's the [scratch](https://hub.docker.com/_/scratch) image. The instruction must come early because every subsequent instruction, other than `ARG`, will modify the base image. We can't modify an image unless we know what it is.

Syntax

```
FROM [--platform=some-platform] image-name[:tag | @digest] [as alias]
```

where square braces indicate optional elements.

The `--platform` option is uncommon. It defaults to the current Docker platform and most organizations stick to one cohesive platform throughout their org.

`@digest` is uncommon.

When possible, specify both the image and tag to avoid any surprises.

An `as` alias is useful for multi-stage builds.

```Dockerfile
# Use Node.js installed on Alpine as a base.
FROM node:alpine3.14
```

```Dockerfile
# The scratch image is an empty image.
# It doesn't create a layer.
# We would have to build everything from 'scratch'.
FROM scratch
```

```Dockerfile
# Use Debian Buster as a base and give it an alias.
FROM debian:buster-slim as start
```

### RUN

The `RUN` instruction executes a program or script via the shell. It creates a new layer in our image.

Syntax

```
RUN shell-command -option arg1 arg2 
RUN ["executable", "arg1" "arg2"]
```

The first form is useful for anything that runs in Bash. The second form is useful for targeting a specific executable.

```Dockerfile
FROM alpine:3.15

# Any Bash command is fair game,
# Here we use mkdir to create a new directory.
RUN mkdir /home/app

# Create a new file in our new directory with touch.
RUN touch /home/app/file.ext
```

Since `RUN` creates a new layer, developers may bundle two or more shell commands in one `RUN`.

```Dockerfile
FROM alpine:3.15

# Bundle it.
RUN mkdir /home/app && touch /home/app/file.ext
```

### WORKDIR

The `WORKDIR` instruction sets the **working directory**. The working directory is the directory in context. If an instruction uses a relative path, it's the directory to which the path is relative.

Syntax

```
WORKDIR /some/path
```

If the directory doesn't exist, `WORKDIR` creates it.

The `WORKDIR` instruction can be executed multiple times. Any instruction following will use the latest working directory. If we launch a container and log into its shell, it will also be the shell's starting directory.

```Dockerfile
FROM alpine:3.15

# Set the working directory and create it
# if it doesn't exist.
WORKDIR /home/app
# Prints /home/app during image construction.
RUN pwd


# Change the working directory and create.
WORKDIR /home/user/documents
# Prints /home/user/documents.
RUN pwd
```

### COPY

The `COPY` instruction copies files from the host OS into the new image. It's similar to `docker cp` but works on an image instead of a container. The `COPY` instruction and its relative `ADD` are required to move our application into an image so we can run it.

Syntax

```
COPY source-pattern destination-path
```

The `source-pattern` can be a relative file path or an absolute file path with optional wildcard patterns. The `*` matches any characters of any length. The `?` matches any single character. See [filepath.Match](https://pkg.go.dev/path/filepath#Match) for more details.

```Dockerfile
# Copy all files and folders recursively from the host
# into the image working directory.
COPY . .

# Replace an existing file in the image, `another-file.ext`
# with a file from the host, `file.ext`.
COPY file.ext /usr/share/app/another-file.ext

# Copy all files and folders recursively from the host
# into /home/app.
COPY . /home/app

# Copy all JavaScript files from the host into /home/app.
COPY *.js /home/app
```

### CMD

The `CMD` instruction launches an application when a container runs. It's the image's default application. It might be a shell, a web server, or one of our REST APIs. There can be only one `CMD` instruction per Dockerfile.

Base images, other than `scratch`, already have a `CMD`. When we add a `CMD` to the Dockerfile, it overwrites the base image's `CMD`.

If the `CMD` launches an application or script that will terminate, the container will stop on termination. We won't be able to start it again because default application is no longer running.

Like the `RUN` instruction, `CMD` has multiple forms.

Syntax

```
CMD ["executable", "arg1", "arg2"] (executable form)
CMD ["arg1", "arg2"]               (not used in this course)
CMD shell-command arg1 arg2        (shell form)
```

```Dockerfile
# Run a jar with the JRE.
CMD ["java", "-jar", "./app.jar"]
```

```Dockerfile
# Start Nginx in the foreground.
CMD ["nginx", "-g", "daemon off;"]
```

```Dockerfile
# Start the shell.
CMD ["/bin/sh"]
```

One surprising thing about the last example is that if we start the container interactively and `exit` the shell, the container will terminate.

<pre class="console" noheader>
> docker run --name alpine-shell -it alpine:3.15
/ # exit

> docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
# No running containers...

> docker start alpine-shell
alpine-shell

> docker ps
CONTAINER ID   IMAGE         COMMAND     CREATED          STATUS         PORTS     NAMES
6baa556bb345   alpine:3.15   "/bin/sh"   42 seconds ago   Up 2 seconds             alpine-shell
</pre>

### ENV

The `ENV` instruction sets an environment variable. The environment variable is available after its declaration during image construction and persists for the duration of the running container. That is, the variable exists both at the image level and the container level.

The `ENV` instruction can appear multiple times in a Dockerfile.

Never use the `ENV` instruction to configure an application. This hard-codes configuration into the image, making it inflexible. Configuration environment variables should be passed via `docker run`. e.g. `docker run -e KEY=value image:tag`.

The `ENV` instruction is useful for image construction data and for anything

Syntax

```
ENV KEY=value
ENV KEY value
```

```Dockerfile
# Set an explicit value.
ENV JAVA_HOME=/opt/java

# Use an existing environment variable to
# set an existing environment variable.
ENV PATH "${JAVA_HOME}/bin:${PATH}"
```

## `docker build` Details

### Build Context

Think back to how the Docker CLI, `docker`, coordinates with the Docker Daemon, `dockerd`. `docker` uses `dockerd`'s Docker Engine API. There's no requirement that both `docker` and `dockerd` live on the same physical machine. All interaction is managed via the API. That means `dockerd` isn't aware of `docker`'s local file system. Again, all requests and responses happen via the API.

This creates interesting dynamics in the `docker build` command. When we provide a path to `docker build`:

<pre class="console" noheader>
> docker build .
# dot (.): start at current directory, include all files and directories recursively.
</pre>

The build command must decide how to include all of those files. This is the build command's **build context**. The build context is all files and directories sent with the request. `docker build` packages the build context as a tarball. It doesn't matter which files are included in the `COPY` and `ADD` commands. `docker build` doesn't peek into the Dockerfile. It includes everything in its build context.

Now consider a typical project. A React app directory structure might be:

```
project/
│   .gitignore        # not needed
│   Dockerfile        # not needed
│   package-lock.json # needed
│   package.json      # needed
│   README.md         # not needed
│  
├───.git/             # not needed, DANGEROUS
│  
├───build/            # possibly needed?
│            
├───node_modules/     # not needed, HUGE
│           
├───public/
│       index.html    # needed
│       
└───src/
        App.css       # needed
        App.js        # needed
        index.js      # needed
        setupTests.js # possibly needed?
```

When we execute `docker build .`, the build context is the entire `project` directory and its subdirectories. That's not good. We don't need 99% of the files in the build context. There are two problems.
1. This creates an absolutely gigantic tarball. It's a waste of bandwidth. The `node_modules` directory can grow to nearly a gig of content. We don't want or need that. In a React app image, we either: 1. `COPY` the `build` directory or 2. `COPY` source code and package data and build our React app inside the image. `node_modules` is never needed.
2. An open build context is dangerous. For example, the `.git` directory may contain secrets. If we send it as part of the build context, we increase the chances that someone can intercept our secrets.

There's a solution to the build context problem.

#### .dockerignore

Use a `.dockerignore` file to prevent huge and dangerous build contexts. The `.dockerignore` file is a text file and works similarly to a `.gitignore` file. See syntax rules here: https://docs.docker.com/engine/reference/builder/#dockerignore-file. It uses path pattern entries to selectively ignore files and directories in the build context. Comments start with `#`. Path patterns can use `*` to match any number of "wild" characters and `?` to match a single wild character. Blank lines are ignored.

A `.dockerignore` file for a React app might look like:

```
# don't share .git secrets
.git

# don't need the build directory since we'll rebuild the project inside
build

# copying node_modules is slow and we'll need a fresh install anyway
node_modules

# no docker artifacts
*Dockerfile*
.dockerignore

# no documentation
*.md
```

### Options

The `docker build` command has many command-line options. To see them all, execute `docker build --help`. We cover a few options in details.

#### `-t` or `--tag`

Sets an image name and tag. If the tag is omitted, `latest` is the default. In our walk-through preview above, our image was built without a name and tag. It only had an image ID. We usually don't want that. Give it a name and tag with the `-t` option.

<pre class="console" noheader>
> docker build -t image-name:tag .
</pre>

#### `-f` or `--file`

Overrides a Dockerfile by providing a name other than `Dockerfile`. Since a Dockerfile is just a text file, we can use different file naming conventions and swap out different Dockerfiles per build. 

Be careful with this one. The beauty of a docker image is that it can be customized via configuration. We likely do not want different Dockerfiles per environment or version. Environment can be configured during the `docker run` step and versions can be managed with the `-t` option. The best use case for multiple Dockerfiles is when we're experimenting with two different approaches in our image build.

<pre class="console" noheader>
> docker build -t image-name:tag -f Dockerfile.multistage .
> docker build -t image-name:tag -f Dockerfile.buildinside .
> docker build -t image-name:tag -f Dockerfile.copyartifacts .
</pre>

#### `--no-cache`

Disable the build cache for this build. By default, the `docker build` command likes to cache its intermediate layers. This can greatly reduce the time required to build an image. If we constantly build layers that are identical, it's nice to speed up the build process. Rarely, there's something wrong with a cached layer. In that case, disable the build cache.

<pre class="console" noheader>
# Layers will not be cached.
> docker build -t image-name:tag --no-cache .

# Layers will be cached.
> docker build -t image-name:tag .
</pre>

#### `--pull`

Pull the current version of a tag specified in the Dockerfile `FROM` instruction. By default, if the build process finds a local image with a specific tag, it uses the image. The `--pull` option disables this behavior. The build process grabs the current version of that tag. For example, the `latest` tag represents the most current image. It's changing all the time. The `--pull` option tells the build to ignore a `latest` tag if it finds one locally and to pull the current `latest` tag instead.

<pre class="console" noheader>
# If there's a local image, ignores and pulls.
> docker build -t image-name:tag --pull .

# Does not ignore the local image.
> docker build -t image-name:tag .
</pre>
