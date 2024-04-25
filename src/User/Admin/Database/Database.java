package User.Admin.Database;

import User.Person.Course;
import java.util.ArrayList;
import User.User;

public class Database {

    private static Database INSTANCE;
    private ArrayList<User> users;
    private ArrayList<Course> courses;

    private Database(){
        users = new ArrayList<User>();
        courses = new ArrayList<>();
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
