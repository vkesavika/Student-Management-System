package com.student.main;

import com.student.dao.StudentDAO;
import com.student.dao.StudentDAOImpl;
import com.student.db.DatabaseConnection;
import com.student.model.Student;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based entry point for the Student Management System.
 *
 * Compile:
 *   javac -cp lib/mysql-connector-java-8.x.x.jar \
 *         -d out \
 *         src/com/student/db/DatabaseConnection.java \
 *         src/com/student/model/Student.java \
 *         src/com/student/dao/StudentDAO.java \
 *         src/com/student/dao/StudentDAOImpl.java \
 *         src/com/student/main/MainApp.java
 *
 * Run:
 *   java -cp out:lib/mysql-connector-java-8.x.x.jar com.student.main.MainApp
 *   (Windows: use ; instead of : in -cp)
 */
public class MainApp {

    private static final StudentDAO dao     = new StudentDAOImpl();
    private static final Scanner    scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("     Student Management System v1.0        ");
        System.out.println("===========================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> viewStudentById();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> { running = false; System.out.println("Goodbye!"); }
                default -> System.out.println("[!] Invalid option. Try again.");
            }
        }

        DatabaseConnection.closeConnection();
    }

    // ── Menu operations ───────────────────────────────────────────────────

    private static void printMenu() {
        System.out.println("\n-------------------------------------------");
        System.out.println("  1. Add Student");
        System.out.println("  2. View All Students");
        System.out.println("  3. View Student by ID");
        System.out.println("  4. Update Student");
        System.out.println("  5. Delete Student");
        System.out.println("  6. Exit");
        System.out.println("-------------------------------------------");
    }

    private static void addStudent() {
        System.out.println("\n-- Add Student --");
        String name   = readString("Name   : ");
        String email  = readString("Email  : ");
        int    age    = readInt   ("Age    : ");
        String course = readString("Course : ");

        Student s = new Student(name, email, age, course);
        int id = dao.addStudent(s);
        if (id > 0) System.out.println("[✓] Student added with ID: " + id);
        else        System.out.println("[✗] Failed to add student.");
    }

    private static void viewAllStudents() {
        System.out.println("\n-- All Students --");
        List<Student> list = dao.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        printHeader();
        list.forEach(MainApp::printRow);
        printDivider();
        System.out.printf("  Total: %d student(s)%n", list.size());
    }

    private static void viewStudentById() {
        int id = readInt("\nEnter Student ID: ");
        Student s = dao.getStudentById(id);
        if (s != null) {
            printHeader();
            printRow(s);
            printDivider();
        } else {
            System.out.println("[!] Student not found.");
        }
    }

    private static void updateStudent() {
        int id = readInt("\nEnter Student ID to update: ");
        Student existing = dao.getStudentById(id);
        if (existing == null) { System.out.println("[!] Student not found."); return; }

        System.out.println("Current: " + existing);
        System.out.println("(Press ENTER to keep current value)");

        String name   = readOptional("New Name   [" + existing.getName()   + "]: ", existing.getName());
        String email  = readOptional("New Email  [" + existing.getEmail()  + "]: ", existing.getEmail());
        String ageStr = readOptional("New Age    [" + existing.getAge()    + "]: ", String.valueOf(existing.getAge()));
        String course = readOptional("New Course [" + existing.getCourse() + "]: ", existing.getCourse());

        existing.setName(name);
        existing.setEmail(email);
        existing.setAge(Integer.parseInt(ageStr));
        existing.setCourse(course);

        if (dao.updateStudent(existing)) System.out.println("[✓] Student updated.");
        else                             System.out.println("[✗] Update failed.");
    }

    private static void deleteStudent() {
        int id = readInt("\nEnter Student ID to delete: ");
        System.out.print("Are you sure? (y/n): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("y")) { System.out.println("Cancelled."); return; }

        if (dao.deleteStudent(id)) System.out.println("[✓] Student deleted.");
        else                       System.out.println("[✗] Delete failed (ID not found?).");
    }

    // ── Display helpers ──────────────────────────────────────────────────

    private static final String ROW_FMT  = "| %-4d | %-20s | %-25s | %-4d | %-20s |%n";
    private static final String HDR_FMT  = "| %-4s | %-20s | %-25s | %-4s | %-20s |%n";
    private static final String DIVIDER  =
        "+------+----------------------+---------------------------+------+----------------------+";

    private static void printHeader() {
        System.out.println(DIVIDER);
        System.out.printf(HDR_FMT, "ID", "Name", "Email", "Age", "Course");
        System.out.println(DIVIDER);
    }

    private static void printRow(Student s) {
        System.out.printf(ROW_FMT, s.getId(), s.getName(), s.getEmail(), s.getAge(), s.getCourse());
    }

    private static void printDivider() { System.out.println(DIVIDER); }

    // ── Input helpers ────────────────────────────────────────────────────

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("[!] Please enter a valid number."); }
        }
    }

    private static String readOptional(String prompt, String defaultVal) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultVal : input;
    }
}
