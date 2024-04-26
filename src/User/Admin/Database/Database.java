package User.Admin.Database;

import User.Person.Course;
import java.util.ArrayList;
import java.util.Optional;

import User.User;

public class Database {

    public static int GLOBAL_ID_USER;
    public static int GLOBAL_ID_COURSE;
    private static Database INSTANCE;
    private ArrayList<User> users;
    private ArrayList<Course> courses;

    private Database(){
        users = new ArrayList<>();
        courses = new ArrayList<>();
        GLOBAL_ID_USER = 1;
        GLOBAL_ID_COURSE = 1;
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

    public Course getCourseById(String courseId){
        Course course_ = null;
        Optional<Course> courseOptional = INSTANCE.getCourses().stream().filter(course -> course.getCourseId().equals(courseId))
                .findFirst();
        if(courseOptional.isPresent()){
            course_ = courseOptional.get();
        }

        return course_;
    }
}
