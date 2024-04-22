package User.Person;


import User.Person.Student.Student;
import java.util.ArrayList;
import java.util.List;

//course class to represent course details
public class Course extends Student{
    private String name;
    private String code;
    private List<Student> students;

    public Course(String name, String code){
        this.name = name;
        this.code = code;
        //this.students = new ArrayList<Student>();
    }
}
//I have imported the User class which has the 