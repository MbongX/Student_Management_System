package User.Person.Student;


import User.AccessLevel;
import User.Person.Course;
import User.Person.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student extends Person {
    private HashMap<Course, ArrayList<Assignment>> marks = new HashMap<>();
    private HashMap<Course, Double> overallGrades = new HashMap<>();

    public Student(){

    }

    public Student(String id, String username, String password, AccessLevel typeAccess) {
        super(id, username, password, typeAccess);
    }

    public HashMap<Course, ArrayList<Assignment>> getMarks() {
        return marks;
    }

    public void setMarks(HashMap<Course, ArrayList<Assignment>> marks) {
        this.marks = marks;
    }

    public HashMap<Course, Double> getOverallGrades() {
        return overallGrades;
    }

    public void setOverallGrades(HashMap<Course, Double> overallGrades) {
        this.overallGrades = overallGrades;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        for(Map.Entry<Course, ArrayList<Assignment>> entry: marks.entrySet()){
            builder.append("\n\nCourse: ").append(entry.getKey().getSubject()).append("\nMarks: ");
            for(Assignment assignment: entry.getValue()){
                builder.append(assignment.getGrade()).append(" ");
            }
        }

        return builder.toString();
    }

    public void joinCourse(Course course){
        this.getAvailableCourses().add(course.getCourseId());
        course.getStudentAttendances().put(getId(), 0);
    }

}
