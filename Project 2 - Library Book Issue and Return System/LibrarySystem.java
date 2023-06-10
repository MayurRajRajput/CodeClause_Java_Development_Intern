import java.sql.*;
import java.util.Scanner;

public class LibrarySystem {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "1234";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean isStudent = false;
        boolean isAdmin = false;
        
        // Connect to the database
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                System.out.println("Connected to the database");
                Statement statement = connection.createStatement();
                
                // Create the Books table if it does not exist
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Books (id INT PRIMARY KEY, title VARCHAR(50), author VARCHAR(50), is_available BOOLEAN)");
                
                // Create the Students table if it does not exist
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Students (id INT PRIMARY KEY, name VARCHAR(50), books_issued INT)");
                
                while (true) {
                    if (!isStudent && !isAdmin) {
                        System.out.println("1. Admin login");
                        System.out.println("2. Student login");
                        System.out.println("3. Exit");
                        choice = scanner.nextInt();
                        scanner.nextLine();                       
                        switch (choice) {
                            case 1:
                                System.out.print("Enter admin password: ");
                                String adminPassword = scanner.nextLine();
                                if (adminPassword.equals("1234")) {
                                    isAdmin = true;
                                    System.out.println("Admin login successful");
                                } else {
                                    System.out.println("Incorrect password");
                                }
                                break;
                            case 2:
                                System.out.print("Enter student ID: ");
                                int studentId = scanner.nextInt();
                                scanner.nextLine();
                                ResultSet studentResultSet = statement.executeQuery("SELECT * FROM Students WHERE id = " + studentId);
                                if (studentResultSet.next()) {
                                    isStudent = true;
                                    System.out.println("Student login successful");
                                } else {
                                    System.out.println("Student not found");
                                }
                                break;
                            case 3:
                                System.out.println("Exiting program");
                                return;
                            default:
                                System.out.println("Invalid choice");
                        }
                    } else if (isStudent) {
                        System.out.println("1. Borrow a book");
                        System.out.println("2. Return a book");
                        System.out.println("3. Logout");
                        choice = scanner.nextInt();
                        scanner.nextLine();                        
                        System.out.print("Enter student ID: ");
                        int studentId = scanner.nextInt();
                        scanner.nextLine();
                        switch (choice) {
                            case 1: 
                                System.out.print("Enter book ID: ");
                                int bookId = scanner.nextInt();
                                scanner.nextLine();                     
                                ResultSet bookResultSet = statement.executeQuery("SELECT * FROM Books WHERE id = " + bookId);
                                if (bookResultSet.next() && bookResultSet.getBoolean("is_available")) {
                                    statement.executeUpdate("UPDATE Books SET is_available = false WHERE id = " + bookId);
                                    statement.executeUpdate("UPDATE Students SET books_issued = books_issued + 1 WHERE id = " + studentId);
                                    System.out.println("Book borrowed successfully");
                                } else {
                                    System.out.println("Book not available");
                                }
                                break;
                            case 2:
                                ResultSet issuedBooksResultSet = statement.executeQuery("SELECT * FROM Students WHERE id = " + studentId);
                                if (issuedBooksResultSet.next() && issuedBooksResultSet.getInt("books_issued") > 0) {
                                    System.out.print("Enter book ID: ");
                                    int returnBookId = scanner.nextInt();
                                    scanner.nextLine();
                                    ResultSet returnBookResultSet = statement.executeQuery("SELECT * FROM Books WHERE id = " + returnBookId);
                                if (returnBookResultSet.next() && !returnBookResultSet.getBoolean("is_available")) {
                                    statement.executeUpdate("UPDATE Books SET is_available = true WHERE id = " + returnBookId);
                                    statement.executeUpdate("UPDATE Students SET books_issued = books_issued - 1 WHERE id = " + studentId);
                                    System.out.println("Book returned successfully");
                                } else {
                                    System.out.println("Invalid book ID");
                                }
                            } else {
                                System.out.println("No books issued");
                            }
                            break;
                        case 3:
                            isStudent = false;
                            System.out.println("Logged out successfully");
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                } else if (isAdmin) {
                    System.out.println("1. Add a book");
                    System.out.println("2. Remove a book");
                    System.out.println("3. Logout");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (choice) {
                        case 1:
                            System.out.print("Enter book ID: ");
                            int addBookId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter book title: ");
                            String addBookTitle = scanner.nextLine();
                            System.out.print("Enter book author: ");
                            String addBookAuthor = scanner.nextLine();
                            statement.executeUpdate("INSERT INTO Books (id, title, author, is_available) VALUES (" + addBookId + ", '" + addBookTitle + "', '" + addBookAuthor + "', true)");
                            System.out.println("Book added successfully");
                            break;
                        case 2:
                            System.out.print("Enter book ID: ");
                            int removeBookId = scanner.nextInt();
                            scanner.nextLine();
                            statement.executeUpdate("DELETE FROM Books WHERE id = " + removeBookId);
                            System.out.println("Book removed successfully");
                            break;
                        case 3:
                            isAdmin = false;
                            System.out.println("Logged out successfully");
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
        scanner.close();
    }
}