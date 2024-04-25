package User.Person;

import User.Admin.Database.Database;
import User.Person.Student.Student;
import User.Person.Teacher.Teacher;
import User.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Course {
    private String courseId;
    private String subject;
    private String lecturerId;
    private HashMap<String, Integer> studentAttendances = new HashMap<>();
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

    public HashMap<String, Integer> getStudentAttendances() {
        return studentAttendances;
    }

    public void setStudentAttendances(HashMap<String, Integer> studentAttendances) {
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

    public List<Student> getStudentsByIds() {
        List<User> allUsers = database.getUsers();

        return allUsers.stream()
                .filter(user -> studentAttendances.containsKey(user.getId()))
                .map(user -> (Student) user)
                .collect(Collectors.toList());
    }


    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("\nId: ").append(courseId).append("\nSubject: ").append(subject);

        Teacher lecturer = getLecturerById();
        if(lecturer != null){
            builder.append("\nTeacher: ").append(lecturer.getName());
        } else builder.append("\nNo teacher was assigned");

        builder.append("\nTotal number of courses: ")
                .append(totalNumber);
        if(!getStudentsByIds().isEmpty()){
            builder.append("\n\n---Student list---");
        }

        for(Student student: getStudentsByIds()){
            builder.append("\n").append(student.getName());
        }
        return builder.toString();
    }
}
//I have imported the User class which has the 