package User.Person;

import User.Admin.Database;
import User.Person.Student.Student;
import User.Person.Teacher.Teacher;
import User.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Course {
    private String courseId;
    private String subject;
    private String lecturerId;
    private HashMap<Student, Integer> studentAttendances = new HashMap<>();
    private int totalNumber;
    private Database database = Database.getInstance();

    public Course(){

    }

    public Course(String courseId, String subject, String lecturerId, int totalNumber) {
        this.courseId = courseId;
        this.subject = subject;
        this.lecturerId = lecturerId;
        this.totalNumber = totalNumber;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
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

    public Teacher getLecturerById(){
       Optional<User> userOptional = database.getUsers().stream().filter(user -> user.getId().equals(lecturerId)).findFirst();
       Teacher lecturer = null;
       if(userOptional.isPresent()){
           lecturer = (Teacher) userOptional.get();
       }

        return lecturer;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("\nSubject: ").append(subject);

        Teacher lecturer = getLecturerById();
        if(lecturer != null){
            builder.append("\nTeacher: ").append(lecturer.getName());
        } else builder.append("\nNo teacher was assigned");

        builder.append("\nTotal number of courses: ")
                .append(totalNumber).append("\n\n---Student list---");

        for(Map.Entry<Student, Integer> entry: studentAttendances.entrySet()){
            builder.append("\n").append(entry.getKey().getName());
        }
        return builder.toString();
    }
}
//I have imported the User class which has the 