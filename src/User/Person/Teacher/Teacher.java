package User.Person.Teacher;

import User.Admin.Database.Database;
import User.Person.Course;
import User.Person.Person;
import User.Person.Student.Assignment;
import User.Person.Student.Student;
import User.AccessLevel;

import java.util.*;

import static java.lang.StringTemplate.STR;

public class Teacher extends Person {
    Scanner in = new Scanner(System.in);

    public Teacher(){

    }

    public Teacher(String id, String username, String password, AccessLevel typeAccess) {
        super(id, username, password, typeAccess);
    }

    public List<Course> getCoursesById(){
        return database.getCourses().stream().filter(course -> course.getLecturerId().equals(getId()))
                .toList();
    }

    @Override
    public void start() {
        System.out.println("\nYou are a Teacher. You can select the following options:");
        System.out.println("1. View your courses\n2. Attendance tracking\n3. Add grade\n4. Edit grade\n5. View student profile\n6. Log out");
        boolean isLoggedOut;

        do {
            System.out.print("\nUse a command (1,2,3,4,5,6) to perform an action -> ");
            String command = in.nextLine();

            isLoggedOut = false;
            switch (command) {
                case "1" -> accessCourses();
                case "2" -> accessAttendance();
                case "3", "4" -> {
                    List<Course> courses = getCoursesById();
                    if (courses.isEmpty()) {
                        System.out.println("\nThere are no courses available to access");
                    } else {
                        for (Course course : courses) {
                            System.out.println(STR."\{course.toString()}\n");
                        }
                        System.out.print("Choose a valid course id: ");
                        String courseId = getCourseId(in);
                        Course course = database.getCourseById(courseId);
                        if (course.getStudentAttendances().isEmpty()) {
                            System.out.println("\nThere are no students participating in this course");
                        } else {
                            List<Student> students = course.getStudentsByIds();
                            for (Student student : students) {
                                System.out.print(STR."\nId: \{student.getId()}; Username: \{student.getUsername()}");
                            }
                            System.out.print("\nChoose a valid student id: ");
                            String studentId = getStudentIdFromCourse(in, students);
                            Student student = (Student) database.getUserById(studentId);
                            if(command.equals("3")){
                                addGrade(course, student);
                            } else {
                                List<Assignment> assignmentList = student.getAssignmentsFromCourseByStudentId(courseId);
                                if(assignmentList.isEmpty()){
                                    System.out.println("\nThe selected student does not have any assignments");
                                } else {
                                    for(Assignment assignment: assignmentList){
                                        System.out.println(STR."\n\{assignment}");
                                    }
                                    System.out.print("\nChoose a valid assignment id: ");
                                    String assignmentId = getAssignmentId(in, assignmentList);
                                    editGrade(assignmentId);
                                }
                            }
                        }
                    }
                }
                case "5" -> accessStudents();
                case "6" -> isLoggedOut = true;
                default -> System.out.println("\nInvalid command! Please try again.");
            }
        } while (!isLoggedOut);

        logOut();
    }

    private void accessCourses() {
        List<Course> courses = getCoursesById();
        if (courses.isEmpty()) {
            System.out.println("\nYou have not been assigned to any courses yet");
        }
        for (Course course : courses) {
            System.out.println(STR."\{course.toString()}\n");
        }
        viewTeachersCommands();
    }

    // Choice 2
    private void accessAttendance() {
        System.out.println("\nYou have accessed the attendance options:");
        System.out.println("1. Update attendance\n2. Generate attendance report\n3. Return to previous menu");
        boolean returnsBack;

        do {
            System.out.print("\nUse a command (1,2,3) to perform an action -> ");
            String command = in.nextLine();

            returnsBack = false;
            switch (command) {
                case "1" -> {
                    if (getCoursesById().isEmpty()) {
                        System.out.println("\nThere are no courses available to update attendance");
                    } else {
                        for (Course course : getCoursesById()) {
                            System.out.println(STR."\{course.toString()}\n");
                        }
                        System.out.print("Choose a valid course id: ");
                        String courseId = getCourseId(in);
                        Course course = database.getCourseById(courseId);
                        if(course.getStudentAttendances().isEmpty()){
                            System.out.println("\nThere are no students participating in this course");
                        } else {
                            for(Student student: course.getStudentsByIds()){
                                System.out.print(STR."\nId: \{student.getId()}; Username: \{student.getUsername()}");
                            }
                            System.out.print("\nChoose a valid student id: ");
                            String studentId = getStudentIdFromCourse(in, course.getStudentsByIds());
                            Student student = (Student) database.getUserById(studentId);
                            markAttendance(course, student);
                        }
                    }
                }
                case "2" -> {
                    if (getCoursesById().isEmpty()) {
                        System.out.println("\nThere are no courses available to edit");
                    } else {
                        for (Course course : getCoursesById()) {
                            System.out.println(STR."\{course.toString()}\n");
                        }
                        System.out.print("\nChoose a valid course id: ");
                        String courseId = getCourseId(in);
                        generateAttendanceReport(courseId);
                    }
                }
                case "3" -> returnsBack = true;
            }
        } while (!returnsBack);
    }

    private void addGrade(Course course, Student student){
        System.out.println("\n---Creating a new assignment---");
        System.out.println("Assignment name can contain letters and digits, at least 3 letters required");
        System.out.println("Assignment grade must be a number between 1 and 10");
        String assignmentId = String.valueOf(Database.GLOBAL_ID_ASSIGNMENT++);
        System.out.print("\nEnter the name of the assignment: ");
        String name = getAssignmentName();
        System.out.print("\nEnter the grade: ");
        double grade = Double.parseDouble(getGrade());

        Assignment assignment = new Assignment(assignmentId, name, grade);
        database.getAssignments().add(assignment);
        database.getUsers().stream().filter(user -> user.getId().equals(student.getId()))
                .map(user -> (Student) user)
                .forEach(student1 -> student1.getMarks().get(course.getCourseId()).add(assignmentId));
        System.out.println("\nGrade added successfully!");
    }

    private void editGrade(String assignmentId){
        System.out.print("\nEnter the new grade: ");
        double grade = Double.parseDouble(getGrade());
        database.getAssignments().stream().filter(assignment1 -> assignment1.getAssignmentId().equals(assignmentId))
                .forEach(assignment1 -> assignment1.setGrade(grade));
        System.out.println("\nGrade updated successfully!");
    }

    private void markAttendance(Course course, Student student) {
        System.out.print(STR."\nUpdate the number of attendances for \{student.getUsername()}: ");
        int attendances = Integer.parseInt(getAttendance(in, course.getTotalNumber()));

        database.getCourses().stream().filter(course1 -> course1.getCourseId().equals(course.getCourseId()))
                    .forEach(course1 -> course1.getStudentAttendances().put(student.getId(), attendances));
        System.out.println(STR."\nAttendance report for \{course.getSubject()} updated successfully!");
    }

    private void generateAttendanceReport(String courseId) {
        Course course = database.getCourseById(courseId);
        HashMap<String, Integer> map = course.getStudentAttendances();
        List<Student> students = course.getStudentsByIds();

        if(!students.isEmpty()){
            System.out.println(STR."\nCourse - \{course.getSubject()}\n");
        } else System.out.println("\nThere are no students participating in this course");
        for(Student student: students){
            System.out.println(STR."\{student.getName()} : \{map.get(student.getId())}");
        }
    }

    private void accessStudents() {
        if (database.getUsers().stream().noneMatch(user -> user.getTypeAccess() == AccessLevel.STUDENT)) {
            System.out.println("\nThere are no students available");
        } else {
            database.getUsers().stream().filter(user -> user.getTypeAccess() == AccessLevel.STUDENT)
                    .forEach(user -> System.out.print(STR."\{user}"));
            System.out.print("\nChoose a valid ID: ");
            String studentId = getStudentId(in);
            Student student = (Student) database.getUserById(studentId);
            if(student.isProfileCreated()){
                System.out.println(student);
            } else System.out.println(STR."Student \{student.getUsername()} has not created his profile");
        }
    }

    private void logOut() {
    }

    private void viewTeachersCommands() {
        System.out.println("\n1. Courses\n2. Attendance tracking\n3. Assign grades\n4. Student details\n5. Log out");
    }

    private String getAttendance(Scanner in, int totalCourses){
        boolean isValidAttendance = false;
        String attendance = "";
        String pattern = "\\d{1,2}";

        while(!isValidAttendance){
            attendance = in.nextLine();
            if(attendance.matches(pattern) && (Integer.parseInt(attendance) >= 0 && Integer.parseInt(attendance) <= totalCourses)){
                isValidAttendance = true;
            } else System.out.println("Invalid number of courses! Please try again");
        }

        return attendance;
    }

    private String getAssignmentName(){
        boolean isValidName = false;
        String pattern = "^(?=.*[a-zA-Z]{3,})[a-zA-Z0-9]+$";
        String name = "";

        while(!isValidName){
            name = in.nextLine();
            if(name.matches(pattern)){
                isValidName = true;
            } else System.out.println("Invalid assignment name! Please try again");
        }

        return name;
    }

    private String getGrade(){
        boolean isValidGrade = false;
        String pattern = "^\\d+(\\.\\d+)?$";
        String grade = "";

        while(!isValidGrade){
            grade = in.nextLine();
            if(grade.matches(pattern) && (Double.parseDouble(grade) >= 1 && Double.parseDouble(grade) <= 10)){
                isValidGrade = true;
            } else System.out.println("Invalid grade! Please try again");
        }

        return grade;
    }

    private String getCourseId(Scanner in) {
        boolean isValid = false;
        String courseId = "";
        while (!isValid) {
            courseId = in.nextLine();
            String finalCourseId = courseId;
            if (getCoursesById().stream().anyMatch(course -> course.getCourseId().equals(finalCourseId))) {
                isValid = true;
            } else {
                System.out.println("Invalid course id! Please select a valid one");
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
            if (database.getUsers().stream().filter(user -> user.getTypeAccess() == AccessLevel.STUDENT)
                                            .anyMatch(user -> user.getId().equals(finalStudentId))) {
                isValid = true;
            } else {
                System.out.println("Invalid Student ID! Please select a valid one");
            }
        }
        return studentId;
    }

    private String getStudentIdFromCourse(Scanner in, List<Student> students){
        boolean isValid = false;
        String studentId = "";
        while (!isValid) {
            studentId = in.nextLine();
            String finalStudentId = studentId;
            if (students.stream().anyMatch(student -> student.getId().equals(finalStudentId))) {
                isValid = true;
            } else {
                System.out.println("Invalid Student ID! Please select a valid one");
            }
        }
        return studentId;
    }

    private String getAssignmentId(Scanner in, List<Assignment> assignmentList) {
        boolean isValid = false;
        String assignmentId = "";
        while (!isValid) {
            assignmentId = in.nextLine();
            String finalAssignmentId = assignmentId;
            if (assignmentList.stream().anyMatch(assignment -> assignment.getAssignmentId().equals(finalAssignmentId))) {
                isValid = true;
            } else {
                System.out.println("Invalid Assignment ID! Please select a valid one");
            }
        }
        return assignmentId;
    }

}
