# Exercise: Build a React Docker Image

## Goals

- Create a Docker image from an existing React application.
- Run a container from the image.

## Set Up

<ol>
<li>
<blockquote class="icon-block-file-download">
Download the <a href="assets/pokemon-ui.zip" download>Pokemon UI</a> React project.
</blockquote>
</li>
</li>
<li>Extract the zip contents. They're a CRA React project.</li>
<li>Open the project with VS Code.</li>
</ol>

## Pokemon UI

The Pokemon UI is a bare-bones React App. It uses React 18.

We don't need to know all of the details behind the project. We're interested in its configuration.

## Phase 1: Test Locally

1. If it's not running, start the `learn-mysql` container. 

2. If it's not running, start the `pokedex` container from _Exercise: Build a Spring Boot Docker Image_.

    <pre class="console" noheader>
    > docker start pokedex
    </pre>

    If `pokedex` doesn't exist, create it.

    <pre class="console" title="Powershell">
    > docker run `
    --name pokedex `
    -e DB_URL=jdbc:mysql://host.docker.internal:3306/pokedex `
    -e DB_USERNAME=your-db-username `
    -e DB_PASSWORD=your-db-password `
    -p 8080:8080 `
    -d `
    pokemon-api:1.0
    </pre>

    <pre class="console" title="Bash, Zsh">
    > docker run \
    --name pokedex \
    -e DB_URL=jdbc:mysql://host.docker.internal:3306/pokedex \
    -e DB_USERNAME=your-db-username \
    -e DB_PASSWORD=your-db-password \
    -p 8080:8080 \
    -d \
    pokemon-api:1.0
    </pre>

3. Open a terminal. Navigate to the React project root.

4. Install dependencies with `npm install`.

5. Run the app with `npm start`.

6. Confirm 10 Pokemon appear in http://localhost:3000.

7. Shut the app down with <kbd>Ctrl+C</kbd>.

## Phase 2: Build the Image

1. In `App.js`, change the hard-coded `fetch` URL to a CRA environment variable.

    **Before**

    ```js
    useEffect(() => {
        const getPokemon = async () => {
            const response = await fetch("http://localhost:8080/api/pokemon");
            if (response.status === 200) {
                setPokemon(await response.json());
            }
        };
        getPokemon();
    }, []);
    ```

    **After**

    ```js
    useEffect(() => {
        const getPokemon = async () => {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/pokemon`);
            if (response.status === 200) {
                setPokemon(await response.json());
            }
        };
        getPokemon();
    }, []);
    ```

2. Add an `.env` file to the project root with the following content.

    ```
    REACT_APP_API_URL=http://localhost:8080/api
    ```

3. Create an appropriate `.dockerignore` file and add it to the project root.

    Things to look out for:
    - Don't include Git assets.
    - Don't include the `node_modules` directory. (very important!)
    - Don't include Docker assets or documentation.

4. Add an Nginx configuration file to the project root that routes to `index.html` for all requests. See _Lesson: Creating a React Image_.

5. Create an appropriate `Dockerfile` and add it to the project root.

    Things to look out for: For each CRA environment variable, overwrite the environment variable on the command-line (or add an `.env` file that targets the [build command](https://create-react-app.dev/docs/adding-custom-environment-variables/#what-other-env-files-can-be-used)).

    ```Dockerfile
    FROM node:16-alpine as builder
    WORKDIR /home/app
    COPY . .
    RUN npm ci && npm cache clean --force
    # CHANGE: Set environment variables
    RUN REACT_APP_API_URL=http://localhost:8080/api npm run build

    FROM nginx:1.21-alpine
    COPY nginx.conf /etc/nginx/conf.d/default.conf

    COPY --from=builder /home/app/build /usr/share/nginx/html

    EXPOSE 80
    CMD ["nginx", "-g", "daemon off;"]
    ```

6. Open a terminal and browse to the project root.

7. Build the image.

    ```sh
    > docker build -t pokemon-ui:1.0 .
    ```

## Phase 3: Test the Container Locally

Create and start a new container with `docker run`.

<pre class="console" noheader>
> docker run --name pokemon-ui -d -p 3000:80 pokemon-ui:1.0
</pre>

Confirm 10 Pokemon appear in http://localhost:3000.
