import java.util.*;

public class Main {
    private static ArrayList<Student> students = new ArrayList<Student>();
    private static int totalStudents;
    private static int availableClasses;
    private static int seatsPerClass;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Admin login
        System.out.println("Enter admin username:");
        String adminUsername = scanner.nextLine();
        System.out.println("Enter admin password:");
        String adminPassword = scanner.nextLine();
        if (!adminUsername.equals("TheProfessor123") || !adminPassword.equals("GoldenProject")) {
            System.out.println("Error: Invalid admin credentials");
            return;
        }
        
        // Admin input
        System.out.println("Enter total number of students:");
        totalStudents = scanner.nextInt();
        System.out.println("Enter number of available classes:");
        availableClasses = scanner.nextInt();
        System.out.println("Enter number of seats per class:");
        seatsPerClass = scanner.nextInt();
        
        for (int i = 1; i <= totalStudents; i++) {
            scanner.nextLine();
            System.out.println("Enter name of student " + i + ":");
            String name = scanner.nextLine();
            System.out.println("Enter roll number of student " + i + ":");
            String rollNumber = scanner.nextLine();
            System.out.println("Enter branch of student " + i + ":");
            String branch = scanner.nextLine();
            System.out.println("Enter semester of student " + i + ":");
            int semester = scanner.nextInt();
            System.out.println("Enter year of student " + i + ":");
            int year = scanner.nextInt();
            students.add(new Student(rollNumber,name,semester,branch,year));
        }
        
        // Generate seating arrangement
        generateSeatingArrangement();
        scanner.close();
    }  
        // Generate seating arrangement
        public static void generateSeatingArrangement() {
        int totalSeats = availableClasses * seatsPerClass;
        if (totalStudents > totalSeats) {
            System.out.println("Error: Not enough seats available");
            return;
        }
        
        // Sort students by roll number
        Collections.sort(students, new Comparator<Student>() {
            public int compare(Student s1, Student s2) {
                return s1.getRollNumber().compareTo(s2.getRollNumber());
            }
        });
        
        int seatCount = 0;
        int rowNumber = 1;
        for (Student student : students) {
            int seatNumber = seatCount % seatsPerClass + 1;
            student.setSeatNumber(seatNumber);
            student.setRowNumber(rowNumber);
            seatCount++;
            if (seatCount % seatsPerClass == 0) {
                rowNumber++;
            }
        }
        
        System.out.println("Seating arrangement generated:");
        for (int classNumber = 1; classNumber <= availableClasses; classNumber++) {
            System.out.println("Class " + classNumber + ":");
            for (int seatNumber = 1; seatNumber <= seatsPerClass; seatNumber++) {
                System.out.print("Seat " + seatNumber + ": ");
                for (Student student : students) {
                    if (student.getSeatNumber() == seatNumber && student.getRowNumber() == classNumber) {
                        System.out.print(student.getName() + " (" + student.getRollNumber() + ")");
                    }
                }
                System.out.println();
            }
        }
    }
}