
# Instructions for Setting Up and Running the Popcorn Palace Project

## Prerequisites

Before you begin, make sure you have the following installed on your machine:

- **Java** (JDK 11 or later)
- **Docker** (for running PostgreSQL with Docker Compose)
- **Maven** (for building the project)

## Setup

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/ShayMashiah/ATT-PopcornPalace.git
```

### 2. Setup PostgreSQL with Docker

The project uses PostgreSQL, which runs in a Docker container. To set it up, follow these steps:

1. Make sure Docker is installed and running on your machine.
2. Download PostgreSQL extension.
3. Setting up a Connection to PostgreSQL:
   1. Open the **Command Palette** (Cmd + Shift + P).
   2. Search for `PostgreSQL: Add Connection` and click on it.
   3. Enter the connection details:
      - **Host**: `localhost`
      - **Port**: `5432`
      - **Database Name**: popcorn-palace
      - **User**: popcorn-palace
      - **Password**: popcorn-palace

4. Run the following command to start the PostgreSQL container:

```bash
docker compose up
```

This will pull the necessary PostgreSQL image and start the database in the background.


### 3. Run the Project

To run the project, navigate to `popcorn-palace/src/main/java/com/att/tdp/popcorn_palace/PopcornPalaceApplication.java` and run the `main` method.

```java
public static void main(String[] args) {
    SpringApplication.run(PopcornPalaceApplication.class, args);
}

```
Now your server is up and running, and it's ready to listen for requests.


## 4. Running Tests in VS Code

To run the tests in your project, follow these steps:

1. **Open the Terminal in VS Code**
   - Open the **Terminal** in VS Code

2. **Run the Tests Using Maven**  
   - In the terminal run the following command to execute the tests:
     ```bash
     mvn test
     ```

3. **View the Test Results**
   - The test results will be displayed in the **Terminal**. You will see which tests passed or failed, as well as additional details about the test execution.

## 5. HTTP Requests Log File

1. Install the "REST Client" extension in VS Code

2. Navigate to http-requests-log.http

3. Click "Send Request" above each HTTP method

4. Responses will be shown in a separate panel 
    - Useful for testing API endpoints without external tools like Postman