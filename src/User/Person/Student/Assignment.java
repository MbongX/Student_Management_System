package User.Person.Student;

public class Assignment {
    private String assignmentId;
    private String name;
    private double grade;

    public Assignment(){

    }

    public Assignment(String assignmentId, String name, double grade) {
        this.assignmentId = assignmentId;
        this.name = name;
        this.grade = grade;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
