package User.Person.Teacher;

import User.User;

import java.util.Scanner;

public class Teacher extends User {

    @Override
    public void start() {
        System.out.println("You are a Teacher. You can select the following options:");
        System.out.println("1. Courses\n2. Attendance tracking\n3. Assign grades\n4. Student details\n5. Log out");
        boolean isLoggedOut;
        Scanner in = new Scanner(System.in);

        do {
            System.out.print("\nUse a command (1,2,3,4,5) to perform an action -> ");
            String command = in.nextLine();

            isLoggedOut = false;
            switch (command) {
                case "1" -> accessCourses();
                case "2" -> accessAttendance();
                case "3" -> accessGrades();
                case "4" -> accessStudents();
                case "5" -> isLoggedOut = true;
                default -> System.out.println("\nInvalid command! Please try again.");
            }
        } while (!isLoggedOut);

        logOut();
    }

    private void accessStudents() {
    }

    private void accessCourses() {
    }

    private void accessAttendance() {
    }

    private void accessGrades() {
    }

    private void logOut() {
        //
    }


}
