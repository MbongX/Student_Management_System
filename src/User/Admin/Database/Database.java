package User.Admin.Database;

import User.Person.Course;
import java.util.ArrayList;
import User.User;

public class Database {

    public static int GLOBAL_ID;
    private static Database INSTANCE;
    private ArrayList<User> users;
    private ArrayList<Course> courses;

    private Database(){
        users = new ArrayList<>();
        courses = new ArrayList<>();
        GLOBAL_ID = 1;
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
