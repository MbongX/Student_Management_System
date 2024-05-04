//imports
import User.Admin.Administrator;
import User.Admin.Database.Database;
import User.Person.Course;
import User.Person.Person;
import User.Person.Student.Student;
import User.Person.Teacher.Teacher;
import User.AccessLevel;
import User.User;

import java.awt.*;
import java.awt.image.LookupOp;
import java.time.chrono.IsoEra;


// user class declaration
public class Main {
    public static void main(String[] args) {

        //Administrator admin = new Administrator();
        //admin.start();
        Database database = Database.getInstance(); //db logic on login testing
        User u = new User();
        u.start();
        if(database.isLoggedIn() && database.getAccessLevel() != null) {
            switch (database.getAccessLevel()) {
                case ADMINISTRATOR -> {
                    //Use if LookupOp to Checkbox if Administrator is logged, if yes get id
                    Administrator admin = new Administrator();
                    admin.start();
                    database.getUsers().stream().filter(user -> user.getTypeAccess() == AccessLevel.TEACHER)
                            .forEach(user -> user.start());
                    database.getUsers().stream().filter(user -> user.getTypeAccess() == AccessLevel.STUDENT)
                            .forEach(user -> user.start());
                    admin.start();
                }
                case STUDENT -> {
                    Student student = new Student();
                    database.getUsers().stream().filter(user -> user.getTypeAccess() == AccessLevel.STUDENT)
                            .forEach(user -> user.start());
                    student.start();
                }
                case TEACHER -> {
                    Teacher teacher = new Teacher();
                    database.getUsers().stream().filter(user -> user.getTypeAccess() == AccessLevel.TEACHER)
                            .forEach(user -> user.start());
                    teacher.start();
                }
                default -> throw new IllegalStateException("Unexpected value: " + database.getAccessLevel());
            }
        }
    }
}
