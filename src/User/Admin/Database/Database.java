package User.Admin.Database;

import User.Person.Course;
import java.util.ArrayList;
import java.util.Optional;

import User.Person.Student.Assignment;
import User.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class Database {

    public static int GLOBAL_ID_USER;
    public static int GLOBAL_ID_COURSE;
    public static int GLOBAL_ID_ASSIGNMENT;
    private static Database INSTANCE;
    private ArrayList<User> users;
    private ArrayList<Course> courses;
    private ArrayList<Assignment> assignments;

    private Database(){
        users = new ArrayList<>();
        courses = new ArrayList<>();

        assignments = new ArrayList<>();

        GLOBAL_ID_USER = 1;
        GLOBAL_ID_COURSE = 1;
        GLOBAL_ID_ASSIGNMENT = 1;
    }

    public static Database getInstance(){

        if(INSTANCE == null){
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public Course getCourseById(String courseId){
        Course course_ = null;
        Optional<Course> courseOptional = INSTANCE.getCourses().stream().filter(course -> course.getCourseId().equals(courseId))
                .findFirst();
        if(courseOptional.isPresent()){
            course_ = courseOptional.get();
        }

        return course_;
    }

    public Assignment getAssignmentById(String assignmentId){
        Assignment assignment_ = null;
        Optional<Assignment> assignmentOptional = INSTANCE.getAssignments().stream()
                .filter(assignment -> assignment.getAssignmentId().equals(assignmentId))
                .findFirst();
        if(assignmentOptional.isPresent()){
            assignment_ = assignmentOptional.get();
        }

        return assignment_;
    }
}
