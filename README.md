# HIV Life Expectancy Project

## Table of Contents

- [Description](#description)
- [Installation](#installation)
- [Usage](#usage)
- [Classes](#classes)

## Description

The HIV Life Expectancy project aims to provide functionalities for user registration, login, and managing user information related to HIV life expectancy.

## Installation

To run this project, follow these steps:

1. Navigate to the project directory:

    ```bash
    cd life-expectancy
    ```

2. Compile the Java files:

    ```bash
    javac src/App.java
    ```

3. Run the application:

    ```bash
    java App.class
    ```

## Usage

After starting the application, you will be presented with a home page menu where you can choose to login, register, or complete registration.
Login:
email `a@cmu.edu`
password `12345`

## Classes

### `Admin.java`

Handles the functionalities specific to admin users.

### `App.java`

The main entry point of the application which initializes the program and presents the home page menu.

### `Credentials.java`

Stores the email and hashed password for users.

### `Menus.java`

Contains methods to display different menus to the user.

### `Patient.java`

Represents a patient user with specific attributes.

### `Role.java`

Defines different roles within the system.

### `User.java`

Represents a general user with common attributes and methods.

### `UserManager.java`

Handles user management tasks such as registration and finding users.

### Team Gishwati 4

- Victor Meine
- Lucie Niyomutoni
- Attahiru Jibril
