# Exercise: Deploy Quotes UI to Elastic Beanstalk

## Goals

* Create a React Docker image for deployment.
* Docker compose React application.
* Deploy a React application to Elastic Beanstalk.

## Set Up

The `quote-api` must be deployed and "healthy". Complete _Exercise: Deploy Quotes API to Elastic Beanstalk_ before attempting this exercise.

## Quote UI

The `quote-ui` app has undergone some changes. It is configured to run in a Docker container. The API URL is expected to be passed to the container as an environment variable at runtime.

Review _Lesson: Creating a React Image_. This project follows the example written in the _Advanced: Starting a Container with Environment Variables_ section.

Below are the new and updated files. Check them out, and get a feel for how it works.

```
quote-ui        
  │
  ├───public  
  │     │
  │     ├───config.js               # added a global const API_URL
  │     └───index.html              # included `config.js` in a script tag
  │
  ├───public  
  │     │
  │     └───api.js                  # updated `apiUrl` to reference the new global
  │
  ├───.dockerignore                 # added.
  ├───85-set-environment-vars.sh    # bash script to write the API_URL from env variable
  ├───Dockerfile                    # added, expects an API_URL env variable
  └───nginx.conf                    # added to handle SPA routing
```

## Phase 1: React Docker Image

1. Build the new version of the `quote-ui` application. This is a big update, tag it with a version `2.0`.

```sh
> docker build -t quote-ui:2.0 .
```

2. Test the new image locally. Set the API_URL to the URL of the deployed `quote-api` application from the previous exercise. Once it is running, confirm it is working at [http://localhost:3001](http://localhost:3001). Stop the container.

```sh
> docker run -p 3001:80 -e API_URL=<your-quote-api-url>/quote quote-ui:2.0
```

3. Push the image to Dockerhub.

```sh
> docker tag quote-ui:2.0 <your-dockerhub-name>/quote-ui:2.0
> docker push <your-dockerhub-name>/quote-ui:2.0
```

4. Create a `docker-compose.yml` file in the root of the `quote-ui` folder. Configure the `quote-ui` as a service.

```yml
version: "3.9"

services:
  quote-api:
    image: <your-dockerhub-name>/quote-ui:2.0
    environment:
      - API_URL=<your-quote-api-url>/quote
    ports:
      - 80:80
```

5. Run the compose file. Confirm it is working at [http://localhost:80](http://localhost:80).

```sh
> docker compose up
```

6. Stop the container and remove it.

```sh
> docker compose down
```

## Phase 2: Deploy React to Elastic Beanstalk

1. Go to the Elastic Beanstalk dashboard in AWS.
2. Click the **Environments** link in the sidebar, then click **Create a new environment**.
3. Select **Web server environment** and click **Select**.
4. For **Application name** enter `quote-ui`.
5. In the _Platform_ section, select the `Docker` in the **Platform** dropdown. Leave the rest as their default values.
6. In the _Application code_ section, select **Upload your code**. Upload the newly created `docker-compose.yml` file.
7. Click **Configure more options**.
8. Scroll up to the top of the page to the _Presets_ section. Choose **Custom configuration**. This will default to using an Application Load Balancer so that our application can scale.
9. No other changes are needed. Click **Create environment** and enjoy the show.
10. Once the application is done spinning up, go to the URL and confirm it works.

### Troubleshooting

* Blank screen? Check the network tab in the browser for clues.
* Check the Elastic Beanstalk logs.

## Investigate the Stack

With 2 Elastic Beanstalk applications running, there are a lot of resources that have been automatically created. Check some of them out.

1. **Elastic Beanstalk** - Navigate to Elastic Beanstalk environments to see the 2 running apps.
2. **CloudFormation** - Navigate to CloudFormation, pick one of the generated stacks and look at the _Template_ tab. Look at the _Resources_ tab.
3. **EC2** - At least 2 EC2 instances are running, 1 for each application environment. There are Load Balancers, Target Groups, and Auto Scaling groups.
4. **S3** - Files uploaded to Elastic Beanstalk are added to a regional S3 bucket.
5. **IAM** - IAM roles are added to execute tasks.

## Clean Up

* Go to each environment and select **Terminate environment** from the _Actions_ dropdown.
* Terminating an environment will remove all of the infrastructures that were automatically created, it takes a while.
* Sometimes it gets stuck and you have to manually delete something. Check back after a while to make sure the termination worked correctly.
* Delete the `quotes-db` RDS instance.
