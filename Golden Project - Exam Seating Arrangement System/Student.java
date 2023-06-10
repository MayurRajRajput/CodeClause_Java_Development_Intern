class Student {
    private String rollNumber;
    private String name;
    private int semester;
    private String branch;
    private int year;
    private int seatNumber;
    private int rowNumber;
    
    // Constructor
    public Student(String rollNumber, String name, int semester, String branch, int year) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.semester = semester;
        this.branch = branch;
        this.year = year;
        this.seatNumber = -1; // default value for un-assigned seat
        this.rowNumber = -1; // default value for un-assigned seat
    }
    
    // Getters and setters
    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}