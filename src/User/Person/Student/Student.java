package User.Person.Student;


import User.Person.Course;
import User.Person.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student extends Person {
    private HashMap<Course, ArrayList<Assignment>> marks = new HashMap<>();
    private HashMap<Course, Double> overallGrades = new HashMap<>();

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
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(this.getName()).append("\nGender: ").append(this.isGender())
                .append("\nDate of birth: ").append(this.getDateOfBirth()).append("\nAddress: ").append(this.getAddress())
                .append("\nTelephone: ").append(this.getTelephone());

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
