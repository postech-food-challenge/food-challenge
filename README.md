# Food Challenge

## Prerequisites

Ensure you have the following installed on your local machine:

- Docker
- Java JDK

## Setting Up Your Local Environment

1. **Clone the repository:**
    First, clone the `food-challenge` repository to your local machine. You can do this by running the following command in your terminal:

    ```bash
    git clone git@github.com:dvdonadelli/food-challenge.git
    ```

    Navigate to the project directory:

    ```bash
    cd food-challenge
    ```

2. **Docker Compose:**
    We're using docker-compose to run our PostgreSQL database and application. To start both containers for the first time, ensure Docker is running and execute the following command:
   
    ```bash
    docker-compose up -d --build
    ```
    If you have already built the application and want to run it again, use the following command:

    ```bash
    docker-compose up -d
    ```

That's it! Your local development environment is set up, and you should be able to start working on the project.

## Swagger

To get a better view of the API endpoint, you can access the swagger endpoint in the following path:

    localhost:8080/swagger-ui/index.html
    