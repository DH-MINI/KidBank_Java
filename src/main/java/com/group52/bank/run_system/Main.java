package com.group52.bank.run_system;

import com.group52.bank.authentication.AuthenticationSystem;
import com.group52.bank.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String PARENT_CSV = "src/main/resources/datacsv/parents.csv";
    private static final String CHILD_CSV = "src/main/resources/datacsv/children.csv";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        AuthenticationSystem authSystem = new AuthenticationSystem(PARENT_CSV, CHILD_CSV);
        List<Child> children = new ArrayList<>(authSystem.loadChildrenData()); // No need to load children initially

        while (true) {
            System.out.println("Welcome to the Children's Banking App!");
            System.out.println("1. Login");
            System.out.println("2. Register (Parent)");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    User user = authSystem.login(username, password);
                    if (user != null) {
                        if (user instanceof Parent) {
                            handleParentMenu(scanner, (Parent) user, children, authSystem);
                        } else if (user instanceof Child) {
                            handleChildMenu(scanner, authSystem.findChildByUsername(user.getUsername()));
                        } else {
                            System.out.println("Invalid user type detected.");
                        }
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;
                case 2:
                    registerParent(scanner, authSystem);
                    break;
                case 3:
                    System.out.println("Exiting application.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerParent(Scanner scanner, AuthenticationSystem authSystem) throws Exception {
        System.out.println("Parent Registration:");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authSystem.register(new Parent(username, password))) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Username already exists.");
        }
    }

    private static void registerChild(Scanner scanner, AuthenticationSystem authSystem) throws Exception {
        System.out.println("Child Registration:");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authSystem.register(new Child(username, password))) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Username already exists.");
        }
    }


    private static void handleParentMenu(Scanner scanner, Parent parent, List<Child> children, AuthenticationSystem authSystem) throws Exception {
        while (true) {
            System.out.println("\nParent Menu:");
            System.out.println("1. View Children");
            System.out.println("2. Create Child Account");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    children = new ArrayList<>(authSystem.loadChildrenData());
                    if (children.isEmpty()) {
                        System.out.println("No children registered yet.");
                    } else {
                        for (int i = 0; i < children.size(); i++) {
                            Child child = children.get(i);
                            System.out.println((i + 1) + ". " + child.getUsername() + " (Balance: $" + child.viewBalance() + ")");
                        }
                    }
                    break;
                case 2:
                    registerChild(scanner, authSystem);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleChildMenu(Scanner scanner, Child child) {
        while (true) {
            System.out.println("\nChild Menu:");
            System.out.println("1. View Balance");
            System.out.println("2. View Tasks"); // Optional, if tasks are implemented
            System.out.println("3. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Your balance: $" + child.viewBalance());
                    break;
                case 2:
                    // Implement logic to display child's tasks (optional)
                    System.out.println("Task functionality not yet implemented.");
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

