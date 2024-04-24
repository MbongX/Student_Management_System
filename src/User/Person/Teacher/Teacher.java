package User.Person.Teacher;

import User.Admin.Database;
import User.Person.Course;
import User.Person.Student.Student;
import User.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.StringTemplate.STR;

public class Teacher extends User {
    Scanner in = new Scanner(System.in);
    Database database = Database.getInstance();

    @Override
    public void start() {
        System.out.println("You are a Teacher. You can select the following options:");
        System.out.println("1. Courses\n2. Attendance tracking\n3. Assign grades\n4. Student details\n5. Log out");
        boolean isLoggedOut;

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

    // Choice 1
    private void accessCourses() {
        // Display all courses
        if (database.getCourses().isEmpty()) {
            System.out.println("There are no courses in the database");
        }
        for (Course course : database.getCourses()) {
            System.out.println(STR."\{course.toString()}\n");
        }
        viewTeachersCommands();
    }

    // Choice 2
    private void accessAttendance() {
        // Mark/update attendance OR Generate attendance report
        System.out.println("\nYou have accessed the attendance options:");
        System.out.println("1. Mark attendance\n2. Update attendance\n3. Edit attendance report\n4. Return to previous menu");
        boolean returnsBack;

        do {
            System.out.print("\nUse a command (1,2,3,4) to perform an action -> ");
            String command = in.nextLine();

            returnsBack = false;
            switch (command) {
                case "1", "2" -> {
                    if (database.getCourses().isEmpty() || database.getUsers().isEmpty()) {
                        System.out.println("\nThere are no courses or students available to edit");
                    } else {
                        System.out.print("\nChoose a student id: ");
                        // @TODO ADDING LOOP TO DISPLAY students ?
                        String studentId = getStudentId(in);
                        System.out.print("\nChoose a valid course id: ");
                        // @TODO ADDING LOOP TO DISPLAY COURSES ?
                        String courseId = getCourseId(in);
                        markAttendance(studentId, courseId);
                    }
                }
                case "3" -> {
                    if (database.getCourses().isEmpty()) {
                        System.out.println("\nThere are no courses available to edit");
                    } else {
                        System.out.print("\nChoose a valid course id: ");
                        // @TODO ADDING LOOP TO DISPLAY COURSES ?
                        String courseId = getCourseId(in);
                        generateAttendanceReport(courseId);
                    }
                }
                case "4" -> returnsBack = true;
            }
        } while (!returnsBack);
    }


    private void markAttendance(String courseId, String studentId) {
        // @todo UPDATING this method according to our DB and the setStudentAttendances(studentId) method in Course class
        // Course course = database.getCourses().get(Integer.parseInt(courseId));
        // course.setStudentAttendances(studentId);
    }

    private void generateAttendanceReport(String courseId) {
        Course course = database.getCourses().get(Integer.parseInt(courseId));
        HashMap<Student, Integer> map = course.getStudentAttendances();
        for (Map.Entry<Student, Integer> entry : map.entrySet()) {
            System.out.println(STR."\{entry.getKey().getName()} : \{entry.getValue()}\n");
        }

    }

    // Choice 3
    private void accessGrades() {
        if (database.getCourses().isEmpty() || database.getUsers().isEmpty()) {
            System.out.println("\nThere are nothing to edit");
        } else {
            System.out.print("\nChoose a student id: ");
            // @TODO ADDING LOOP TO DISPLAY students ?
            String studentId = getStudentId(in);
            System.out.print("\nChoose a  course id: ");
            // @TODO ADDING LOOP TO DISPLAY COURSES ?
            String courseId = getCourseId(in);
            System.out.print("\nChoose an assignment id: ");
            // @TODO ADDING LOOP TO DISPLAY COURSES ?
            String assignmentId = getAssignmentId(in);
            addMark(courseId, studentId, assignmentId);
        }

    }

    private void addMark(String courseId, String studentId, String assignmentId) {
        // @TODO Check the line below how we'll get our data
        Course course = database.getCourses().get(Integer.parseInt(courseId));
        Student student = (Student) database.getUsers().get(Integer.parseInt(studentId));
        // Assignment assignment = database.getAssignments().get(Integer.parseInt(assignmentId));
        // student.setMarks(course, assignment);
    }

    // Choice 4
    private void accessStudents() {
        if (database.getUsers().isEmpty()) {
            System.out.println("\nThere are no students available");
        } else {
            System.out.print("\nChoose a valid ID: ");
            // @TODO ADDING LOOP TO DISPLAY STUDENTS ?
            String studentId = getStudentId(in);
            // @TODO Check the line below how we'll get our Student
            Student student = (Student) database.getUsers().get(Integer.parseInt(studentId));
            System.out.println(student.toString());
        }
    }

    // Choice 5
    private void logOut() {
    }

    // Other methods
    private void viewTeachersCommands() {
        System.out.println("1. Courses\n2. Attendance tracking\n3. Assign grades\n4. Student details\n5. Log out");
    }

    private String getCourseId(Scanner in) {
        boolean isValid = false;
        String courseId = "";
        while (!isValid) {
            courseId = in.nextLine();
            String finalCourseId = courseId;
            if (database.getCourses().stream().anyMatch(course -> course.getCourseId().equals(finalCourseId))) {
                isValid = true;
            } else {
                System.out.println("Invalid course, please select a valid ID course");
            }
        }
        return courseId;
    }

    private String getStudentId(Scanner in) {
        boolean isValid = false;
        String studentId = "";
        while (!isValid) {
            studentId = in.nextLine();
            String finalStudentId = studentId;
            if (database.getUsers().stream().anyMatch(student -> student.getId().equals(finalStudentId))) {
                isValid = true;
            } else {
                System.out.println("Invalid Student ID, please select a valid one");
            }
        }
        return studentId;
    }

    private String getAssignmentId(Scanner in) {
        boolean isValid = false;
        String assignmentId = "";
        while (!isValid) {
            assignmentId = in.nextLine();
            String finalAssignmentId = assignmentId;
           /* if (database.getAssignments().stream().anyMatch(ass -> ass.getId().equals(finalAssignmentId))) {
                isValid = true;
            } else {
                System.out.println("Invalid ID");
            }*/
        }
        return assignmentId;
    }

}
