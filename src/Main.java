//imports
import User.Admin.Administrator;
import User.Admin.Database.Database;
import User.Person.Course;
import User.Person.Teacher.Teacher;
import User.AccessLevel;
import User.User;
import java.time.chrono.IsoEra;


// user class declaration
public class Main {
    public static void main(String[] args) {
        Administrator admin = new Administrator();
        admin.start();
    }
}
