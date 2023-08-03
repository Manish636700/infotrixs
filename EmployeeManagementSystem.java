package employee;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    private int id;
    private String name;
    private int age;
    private String department;
    private double salary;

    public Employee(int id, String name, int age, String department, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Department: " + department + ", Salary: " + salary;
    }
}
public class EmployeeManagementSystem {
    private static final String FILE_PATH = "employees.txt";

    private static ArrayList<Employee> employees = new ArrayList<>();
    private static int nextId = 1;

    public static void main(String[] args) {
        loadEmployeesFromFile();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Employee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Search Employee by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    updateEmployee(scanner);
                    break;
                case 4:
                    searchEmployee(scanner);
                    break;
                case 5:
                    saveEmployeesToFile();
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void loadEmployeesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String department = parts[3];
                double salary = Double.parseDouble(parts[4]);
                employees.add(new Employee(id, name, age, department, salary));
                nextId = Math.max(nextId, id + 1);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void saveEmployeesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," + employee.getName() + "," +
                        employee.getAge() + "," + employee.getDepartment() + "," +
                        employee.getSalary());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void addEmployee(Scanner scanner) {
        System.out.println("Enter employee name:");
        String name = scanner.nextLine();
        System.out.println("Enter employee age:");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.println("Enter employee department:");
        String department = scanner.nextLine();
        System.out.println("Enter employee salary:");
        double salary = scanner.nextDouble();

        Employee employee = new Employee(nextId, name, age, department, salary);
        employees.add(employee);
        nextId++;
        System.out.println("Employee added successfully!");
    }

    private static void viewAllEmployees() {
        System.out.println("All Employees:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static void updateEmployee(Scanner scanner) {
        System.out.println("Enter employee ID to update:");
        int idToUpdate = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        boolean found = false;
        for (Employee employee : employees) {
            if (employee.getId() == idToUpdate) {
                System.out.println("Enter updated employee name:");
                String name = scanner.nextLine();
                System.out.println("Enter updated employee age:");
                int age = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.println("Enter updated employee department:");
                String department = scanner.nextLine();
                System.out.println("Enter updated employee salary:");
                double salary = scanner.nextDouble();

                employee = new Employee(idToUpdate, name, age, department, salary);
                employees.removeIf(e -> e.getId() == idToUpdate);
                employees.add(employee);

                found = true;
                System.out.println("Employee updated successfully!");
                break;
            }
        }

        if (!found) {
            System.out.println("Employee with ID " + idToUpdate + " not found.");
        }
    }

    private static void searchEmployee(Scanner scanner) {
        System.out.println("Enter employee ID to search:");
        int idToSearch = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        boolean found = false;
        for (Employee employee : employees) {
            if (employee.getId() == idToSearch) {
                System.out.println("Employee Found:");
                System.out.println(employee);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Employee with ID " + idToSearch + " not found.");
        }
    }
}