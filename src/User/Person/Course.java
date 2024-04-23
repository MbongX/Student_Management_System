package User.Person;

import User.Person.Student.Student;
import User.Person.Teacher.Teacher;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private String courseId;
    private String subject;
    private Teacher lecturer;
    private HashMap<Student, Integer> studentAttendances = new HashMap<>();
    private int totalNumber;

    public String getCourseId() {
        return courseId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Teacher getLecturer() {
        return lecturer;
    }

    public void setLecturer(Teacher lecturer) {
        this.lecturer = lecturer;
    }

    public HashMap<Student, Integer> getStudentAttendances() {
        return studentAttendances;
    }

    public void setStudentAttendances(HashMap<Student, Integer> studentAttendances) {
        this.studentAttendances = studentAttendances;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("\nSubject: ").append(subject).append("; Teacher: ").append(lecturer).append("\nTotal number of courses: ")
                .append(totalNumber).append("\n\nStudent attendance list");

        for(Map.Entry<Student, Integer> entry: studentAttendances.entrySet()){
            builder.append("\n").append(entry.getKey().getName()).append(" -> ").append(entry.getValue());
        }
        return builder.toString();
    }
}
//I have imported the User class which has the 