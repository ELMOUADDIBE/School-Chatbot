# Installation Guide for School-Chatbot-Assistant

This document provides detailed instructions on how to set up and run the School-Chatbot-Assistant on your local machine. Ensure that you have the necessary prerequisites installed before proceeding.

## Prerequisites

- Java JDK 11 or higher
- Git (for cloning the repository)
- Maven (for managing dependencies)

## Step 1: Cloning the Repository

1. Open your terminal or command prompt.
2. Navigate to the directory where you want to clone the repository.
3. Run the following command:
`git clone https://github.com/elmouaddibe/School-Chatbot-Assistant.git`
4. Navigate into the cloned repository:
`cd School-Chatbot-Assistant`

## Step 2: Installing JavaFX

Follow the instructions provided on the [JavaFX official website](https://openjfx.io/openjfx-docs/) to install JavaFX for your development environment.

## Step 3: Setting Up Maven Dependencies

1. Ensure that Maven is installed on your machine.
2. In the root directory of the project (where the `pom.xml` file is located), run the following command to download and install all dependencies:
`mvn install`

## Step 4: Referencing ChromaDB Documentation

For information on integrating and using ChromaDB, refer to the [official ChromaDB documentation](https://www.trychroma.com/).

## Step 5: Running the Chatbot

1. Locate the main Java file that contains the `public static void main(String[] args)` method in your IDE.
2. Run this file through your IDE.

## Troubleshooting

- **Dependency issues:** Ensure all dependencies are correctly listed and versions match in your `pom.xml` file.
- **JavaFX setup:** Verify that JavaFX SDK is properly set up and configured in your IDE.
- **ChromaDB integration:** Consult the ChromaDB documentation for any specific setup requirements.
- **Java version errors:** Confirm that you're using the correct version of Java (JDK 11 or higher).

## Need Help?

If you encounter any issues or need further assistance, please feel free to reach out by opening an issue on the GitHub repository.

---

Thank you for using the School-Chatbot-Assistant!
