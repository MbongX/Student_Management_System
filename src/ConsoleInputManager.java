import User.AccessLevel;
import User.Person.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleInputManager {
    private static final Scanner scanner = new Scanner(System.in);
    public static List<Course> courses = new ArrayList<>();
    protected AccessLevel userRole;

    public static void startProgram() {
        // @TODO 1. Loading date from CSV files or Json file


    }

    public static void displayMenu(AccessLevel userRole) {

    }

    public List<Course> getCourses () {
        return courses;
    }
}
