//imports
import User.Admin.Administrator;
import User.Admin.Database.Database;
import User.Person.Course;
import User.Person.Person;
import User.Person.Teacher.Teacher;
import User.AccessLevel;
import User.User;
import java.time.chrono.IsoEra;


// user class declaration
public class Main {
    public static void main(String[] args) {

        //Administrator admin = new Administrator();
        //admin.start();
        Database.getInstance(); //db logic on login testing
        User user = new User();
        user.start();
        
        Administrator admin = new Administrator();
        admin.start();
        Database database = Database.getInstance();
        database.getUsers().stream().filter(user -> user.getTypeAccess() == AccessLevel.TEACHER)
                .forEach(user -> user.start());
        database.getUsers().stream().filter(user -> user.getTypeAccess() == AccessLevel.STUDENT)
                .forEach(user -> user.start());
        admin.start();



    }
}
