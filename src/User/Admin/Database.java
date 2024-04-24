package User.Admin;

import User.Person.Course;
import java.util.ArrayList;
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
}
