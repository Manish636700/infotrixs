package employee;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;


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

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDepartment(String Department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Department: " + department + ", Salary: " + salary;
    }
}
public class EmployeeManagementSystem {
    private static final String FILE_PATH = "employees.txt";
    private static ArrayList<Employee> employees;
    private Scanner scanner;
    public EmployeeManagementSystem(){
        employees = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    public void run(){
        loadEmployees();
        boolean exit = false;
        while (!exit) {
            System.out.println("Employee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Search Employee by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    searchEmployee();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        saveEmployees();
        scanner.close();
    }
    private void addEmployee() {
        System.out.println("Enter employee Id:");
        int id = getIntInput();
        System.out.println("Enter employee name:");
        String name = scanner.nextLine();
        System.out.println("Enter employee age:");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter employee department:");
        String department = scanner.nextLine();
        System.out.println("Enter employee salary:");
        double salary = getDoubleInput();

        Employee newemployee = new Employee(id, name, age, department, salary);
        employees.add(newemployee);

        System.out.println("Employee added successfully!");
    }
    private void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("Employees :");
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }
    private void updateEmployee() {
        System.out.println("Enter employee ID to update:");
        int id = getIntInput();
        Employee employeeToUpdate = findEmployeeById(id);
        if (employeeToUpdate != null) {
            System.out.println("Enter updated employee name:");
            String newName = scanner.nextLine();
            employeeToUpdate.setName(newName);

            System.out.println("Enter updated employee age:");
            int newAge = scanner.nextInt();
            employeeToUpdate.setAge(newAge);

            System.out.println("Enter updated employee department:");
            String newDepartment = scanner.nextLine();
            scanner.nextLine();
            employeeToUpdate.setDepartment(newDepartment);

            System.out.println("Enter updated employee salary:");
            double newSalary = getDoubleInput();
            employeeToUpdate.setSalary(newSalary);

            System.out.println("Employee updated Successfully");
        } else {
            System.out.println("Employee not Found.");
        }
    }
    private void searchEmployee() {
        System.out.println("Enter employee ID to search:");
        int id = getIntInput();

        Employee employeeFound = findEmployeeById(id);
        if (employeeFound != null) {
            System.out.println("Employee found: " + employeeFound);
        } else {
            System.out.println("Employee not found.");
        }
    }
    private Employee findEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }
    private int getIntInput() {
        int input = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                input = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number .");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return input;
    }
    private double getDoubleInput() {
        double input = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                input = scanner.nextDouble();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number .");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return input;
    }
    private void loadEmployees() {
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
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    private void saveEmployees() {
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

    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        ems.run();
        }
    }








