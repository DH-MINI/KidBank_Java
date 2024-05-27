# Kid Bank Application

This is a simple children's banking application developed in Java. The app allows parents to manage transactions, tasks and users, and lets children learn how to use the bank, learn the basic concepts of banking and get paid for doing tasks.

## Prerequisites

- Java Development Kit (JDK) 21 or later
- Maven

## Setup and Configuration

1. Clone the repository to your local machine.

```bash
git clone https://github.com/DH-MINI/KidBank_Java.git
```

2. Navigate to the project directory.

```bash
cd KidBank_Java
```

3. Compile the project using Maven.

```bash
mvn compile
```

## Running the Application

1. Run the application using Maven.

```bash
mvn exec:java -Dexec.mainClass="com.group52.bank.GUI.ChildrensBankingApp"
```

## Application Features

- **User Management** : The app supports different types of users, including parents and children. Parents can directly register an account and manage all child users, and child accounts need to be registered in the parent interface after the parent account login.
-
- **Transaction Management** : Users can execute various transactions such as deposits, withdrawals and time deposits. The app also supports viewing transaction history and changing transaction status. Children's accounts allow you to view balances and set savings goals, which are divided into fixed-term and current accounts.
-
- **Task Management** : Parents can assign tasks to their children and manage them. The application supports viewing task history, changing task status, and receiving tasks. The child account also has the function of accepting tasks and marking tasks as completed, while the parent account side can set the interest rate of the fixed deposit, post new tasks, double confirm that the child's tasks have been completed and automatically remit money, and confirm the child's request to withdraw and withdraw money, and complete the increase and decrease of these amounts.

Note that applications use CSV files to store data. Make sure the CSV file is in the correct directory specified in the code. In addition, the app has a number of special designs, including a lot of error handling and the use of hash encryption to protect user passwords.

## JavaDoc

The JavaDoc for this project can be found in the `src/main/resources/JavaDoc` directory.


## Troubleshooting

If you encounter any issues while setting up or running the application, please check the error message in the console. Most common issues are related to file paths or missing dependencies.

## Contributing

If you wish to contribute to this project, please fork the repository and submit a pull request.

---

[English](README.md) | [中文](README_ZH.md)