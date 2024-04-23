package User.Person.Teacher;

import User.Person.Person;
import User.Person.Student.Student;
import java.security.NoSuchAlgorithmException;

import static java.lang.StringTemplate.STR;

public class Teacher extends Person  {


    // Methods
    public void viewStudent(Student student) {
        System.out.println(STR."// Student detail of \{student.getUsername()} //");

    }
}

