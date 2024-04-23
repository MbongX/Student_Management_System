package commands;

import User.Person.Course;

import java.util.Scanner;

public class AddCourse implements Command{
    private static final Scanner scanner = new Scanner(System.in);
    public final int commandNumber = 8;
    public final String commandName = "Add a new course";

    public int getCommandNumber() { return commandNumber;};
    public String getCommandName() { return commandName;}

    @Override
    public void execute() {
        // @todo Asking elt to create a new course and adding it to the List
        System.out.println("Test ADD COURSE");
        Course newCourse = new Course();


    }

}
