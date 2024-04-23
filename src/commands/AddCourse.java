package commands;

import User.Person.Course;

import java.util.Scanner;

public class AddCourse implements Command{
    private static final Scanner scanner = new Scanner(System.in);
    public final String commandName = "Add a new course";

    @Override
    public void execute() {
        // @todo Asking elt to create a new course
        Course newCourse = new Course();


    }

}
