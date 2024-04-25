package User.Admin;

import User.Person.Course;
import User.Person.Student.Student;
import User.Person.Teacher.Teacher;
import User.User;
import User.AccessLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Administrator extends User {

    Database database = Database.getInstance();

    @Override
    public void start(){

        System.out.println("\nYou are an administrator. You can select the following options:");
        System.out.println("1. Users\n2. Courses\n3. System\n4. Log out");
        boolean isLoggedOut;
        Scanner in = new Scanner(System.in);

        do {
            System.out.print("\nUse a command (1,2,3,4) to perform an action -> ");
            String command = in.nextLine();

            isLoggedOut = false;
            switch (command) {
                case "1" -> accessUsers();
                case "2" -> accessCourses();
                case "3" -> accessSystem();
                case "4" -> isLoggedOut = true;
                default -> System.out.println("\nInvalid command! Please try again.");
            }
        }while(!isLoggedOut);

        logOut();
    }

    private void accessUsers(){
        System.out.println("\nYou have accessed the Users options:");
        System.out.println("1. View all users\n2. Add user\n3. Edit user\n4. Remove user\n5. Return to previous menu");
        boolean returnsBack;
        Scanner in = new Scanner(System.in);

        do{
            System.out.print("\nUse a command (1,2,3,4,5) to perform an action -> ");
            String command = in.nextLine();

            returnsBack = false;
            switch (command){
                case "1" -> viewAllUsers();
                case "2" -> addUser();
                case "3" -> {
                    if(database.getUsers().isEmpty()){
                        System.out.println("\nThere are no users available to edit");
                    } else {
                        System.out.print("\nChoose a valid user id to edit: ");
                        String userId = getUserId(in);
                        editUser(userId);
                    }
                }
                case "4" -> {
                    if(database.getUsers().isEmpty()){
                        System.out.println("\nThere are no users available to remove");
                    } else {
                        System.out.print("\nChoose a valid user id to remove: ");
                        String userId = getUserId(in);
                        removeUser(userId);
                    }
                }
                case "5" -> returnsBack = true;
                default -> System.out.println("\nInvalid command! Please try again");
            }
        }while(!returnsBack);

        viewAdminCommands();
    }

    private void accessCourses(){
        System.out.println("\nYou have accessed the Courses options:");
        System.out.println("1. View all courses\n2. Add course\n3. Edit course\n4. Remove course\n5. Generate attendance reports" +
                "\n6. Return to previous menu");
        boolean returnsBack;
        Scanner in = new Scanner(System.in);

        do{
            System.out.print("\nUse a command (1,2,3,4,5,6) to perform an action -> ");
            String command = in.nextLine();
            returnsBack = false;

            switch (command){
                case "1" -> viewAllCourses();
                case "2" -> addCourse();
                case "3" -> {
                    if(database.getCourses().isEmpty()){
                        System.out.println("\nThere are no courses available to edit");
                } else {
                        System.out.print("\nChoose a valid course id to edit: ");
                        String courseId = getCourseId(in);
                        editCourse(courseId);
                    }
                }
                case "4" -> {
                    if(database.getCourses().isEmpty()){
                        System.out.println("\nThere are no courses available to remove");
                    } else {
                        System.out.print("\nChoose a valid course id to remove: ");
                        String courseId = getCourseId(in);
                        removeCourse(courseId);
                    }
                }
                case "5" -> generateAttendanceReports();
                case "6" -> returnsBack = true;
                default -> System.out.println("Invalid command! Please try again");
            }
        }while(!returnsBack);

        viewAdminCommands();
    }

    private void accessSystem(){

    }

    private void logOut(){
//        User user = new User();
//        user.start();
    }

    private void viewAllUsers(){
        if(database.getUsers().isEmpty()){
            System.out.println("There are no users in the database");
        }
        for(User user: database.getUsers()){
            System.out.println(user + "\n");
        }
        viewUsersCommands();
    }

    private void viewAllCourses(){
        if(database.getCourses().isEmpty()){
            System.out.println("There are no courses in the database");
        }
        for(Course course: database.getCourses()){
            System.out.println(course + "\n");
        }
        viewCoursesCommands();
    }

    private void generateAttendanceReports(){
        if(database.getCourses().isEmpty()){
            System.out.println("There are no courses in the database");
        }
        for(Course course: database.getCourses()){
            HashMap<String, Integer> map = course.getStudentAttendances();
            List<Student> students = course.getStudentsByIds();
            System.out.println(STR."\n\nCourse - \{course.getSubject()}\n");
            for(Student student: students){
                System.out.println(STR."\{student.getName()} : \{map.get(student.getId())}");
            }
        }
        viewCoursesCommands();
    }

    private void addUser(){
        String userId = String.valueOf(Database.GLOBAL_ID_USER++);
        Scanner in = new Scanner(System.in);
        System.out.println("\n---Create a new user---");
        System.out.println("Username must be minimum 4 characters, only letters and digits");
        System.out.println("Password must be minimum 6 characters, at least one lowercase, one uppercase, one digit and one special character");
        System.out.println("Access level must be Student, Teacher or Admin");

        System.out.print("\nUsername: ");
        String username = getUsername(in);
        System.out.print("\nPassword: ");
        String password = getPassword(in);
        System.out.print("\nAccess level: ");
        AccessLevel accessLevel = AccessLevel.fromString(getAccessLevel(in));

        database.getUsers().add(new User(userId, username, password, accessLevel));
        System.out.println("User added successfully!");
        viewUsersCommands();
    }

    private void addCourse(){
        String lecturerId;
        String courseId = String.valueOf(Database.GLOBAL_ID_COURSE++);
        Scanner in = new Scanner(System.in);
        System.out.println("\n---Create a new course---");
        System.out.println("Subject must be minimum 4 letters and unique");
        System.out.println("Lecturer must have a valid id");
        System.out.println("The total number of courses must be maximum 20");

        System.out.print("\nSubject: ");
        String subject = getSubject(in);

        if(database.getUsers().stream().noneMatch(user -> user.getTypeAccess() == AccessLevel.TEACHER)){
            lecturerId = "";
            System.out.print("\nThere are no teachers available to assign");
        } else {
            database.getUsers().stream().filter(user -> user.getTypeAccess() == AccessLevel.TEACHER)
                    .forEach(System.out::println);
            System.out.print("\nSelect a lecturer id: ");
            lecturerId = getLecturerId(in);
        }
        System.out.print("\nNumber of courses: ");
        int totalNr = Integer.parseInt(getTotalNumber(in));

        database.getCourses().add(new Course(courseId, subject, lecturerId, totalNr));
        database.getUsers().stream().filter(user -> user.getId().equals(lecturerId))
                                    .map(user -> (Teacher) user)
                                    .forEach(teacher -> teacher.getAvailableCourses().add(courseId));
        System.out.println("\nCourse added successfully!");
        viewCoursesCommands();
    }

    private void editUser(String userId){
        System.out.println("\n---Editing a user---");
        System.out.println("1. Edit username\n2. Edit password\n3. Edit access level\n4. Return to previous menu");
        boolean returnsBack;
        Scanner in = new Scanner(System.in);

        do{
            System.out.print("\nUse a command (1,2,3,4) to perform an action -> ");
            String command = in.nextLine();
            returnsBack = false;

            switch (command){
                case "1" -> editUsername(userId, in);
                case "2" -> editPassword(userId, in);
                case "3" -> editAccessLevel(userId, in);
                case "4" -> returnsBack = true;
                default -> System.out.println("Invalid command! Please try again");
            }
        }while(!returnsBack);

        viewUsersCommands();
    }

    private void editCourse(String courseId){
        System.out.println("\n---Editing a course---");
        System.out.println("1. Edit subject\n2. Edit lecturer\n3. Edit total number\n4. Add student to course\n5. Return to previous menu");
        boolean returnsBack;
        Scanner in = new Scanner(System.in);

        do{
            System.out.print("\nUse a command (1,2,3,4,5) to perform an action -> ");
            String command = in.nextLine();
            returnsBack = false;

            switch (command){
                case "1" -> editSubject(courseId, in);
                case "2" -> editLecturer(courseId, in);
                case "3" -> editTotalNumber(courseId, in);
                case "4" -> addStudentToCourse(courseId, in);
                case "5" -> returnsBack = true;
                default -> System.out.println("Invalid command! Please try again");
            }
        }while(!returnsBack);

        viewCoursesCommands();
    }

    private void removeUser(String userId){
        database.getUsers().removeIf(user -> user.getId().equals(userId));
        System.out.println("User " + userId + " has been removed");
        viewUsersCommands();
    }

    private void removeCourse(String courseId){
        database.getCourses().removeIf(course -> course.getCourseId().equals(courseId));
        System.out.println("Course " + courseId + " has been removed");
        viewCoursesCommands();
    }

    private String getUsername(Scanner in){

        boolean isValidUsername = false;
        String pattern = "^[a-zA-Z0-9]{4,}$";
        String username = "";

        while(!isValidUsername){
            username = in.nextLine();
            if(username.matches(pattern) && isUniqueUsername(username)){
                isValidUsername = true;
            } else System.out.println("\nInvalid username! Please try again");
        }

        return username;
    }

    private String getPassword(Scanner in){

        boolean isValidPassword = false;
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_-])[A-Za-z\\d@$!%*?&_-]{6,}$";
        String password = "";

        while(!isValidPassword){
            password = in.nextLine();
            if(password.matches(pattern)){
                isValidPassword = true;
            } else System.out.println("\nInvalid password! Please try again");
        }

        return password;
    }

    private String getAccessLevel(Scanner in){

        boolean isValid = false;
        String accessLevel = "";

        while(!isValid){
            accessLevel = in.nextLine();
            if(accessLevel.equalsIgnoreCase("Student") || accessLevel.equalsIgnoreCase("Teacher")
            || accessLevel.equalsIgnoreCase("Admin")){
                isValid = true;
            } else System.out.println("\nInvalid access level! Please try again");
        }

        return accessLevel;
    }

    private String getUserId(Scanner in){

        boolean isValidId = false;
        String userId = "";

        while(!isValidId){
            userId = in.nextLine();
            String finalUserId = userId;
            if(database.getUsers().stream().anyMatch(user -> user.getId().equals(finalUserId))){
                isValidId = true;
            } else System.out.println("\nInvalid user id! Please select a valid one");
        }

        return userId;
    }

    private String getSubject(Scanner in){

        boolean isValidSubject = false;
        String subject = "";
        String pattern = "(.*[a-zA-Z]){4}.*";

        while(!isValidSubject){
            subject = in.nextLine();
            if(subject.matches(pattern) && isUniqueSubject(subject)){
                isValidSubject = true;
            } else System.out.println("\nInvalid subject format! Please try again");
        }

        return subject;
    }

    private String getLecturerId(Scanner in){

        boolean isValidId = false;
        String lecturerId = "";

        while(!isValidId){
            lecturerId = in.nextLine();
            String finalUserId = lecturerId;
            if(database.getUsers().stream().filter(user -> user.getTypeAccess() == AccessLevel.TEACHER)
                                            .anyMatch(user -> user.getId().equals(finalUserId))){
                isValidId = true;
            } else System.out.println("\nInvalid lecturer id! Please select a valid one");
        }

        return lecturerId;
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
                System.out.println("\nInvalid course id! Please select a valid one");
            }
        }
        return courseId;
    }

    private String getStudentId(List<Student> students, Scanner in) {
        boolean isValid = false;
        String studentId = "";
        while (!isValid) {
            studentId = in.nextLine();
            String finalStudentId = studentId;
            if (students.stream().anyMatch(student -> student.getId().equals(finalStudentId))) {
                isValid = true;
            } else {
                System.out.println("Invalid student id! Please select a valid one");
            }
        }
        return studentId;
    }

    private String getTotalNumber(Scanner in){

        boolean isValidNumber = false;
        String total = "";
        String pattern = "\\d{1,2}";

        while(!isValidNumber){
            total = in.nextLine();
            int totalNr = Integer.parseInt(total);
            if(total.matches(pattern) && (totalNr > 0 && totalNr <= 20)){
                isValidNumber = true;
            } else System.out.println("Invalid number of courses! Please try again");
        }

        return total;
    }

    private boolean isUniqueUsername(String username){
        return database.getUsers().stream().noneMatch(user -> user.getUsername().equals(username));
    }

    private boolean isUniqueSubject(String subject){
        return database.getCourses().stream().noneMatch(course -> course.getSubject().equals(subject));
    }

    private void editUsername(String userId, Scanner in){
        System.out.print("\nEnter the new username: ");
        String username = getUsername(in);

        database.getUsers().stream().filter(user -> user.getId().equals(userId))
                                    .forEach(user -> user.setUsername(username));
        System.out.println("User " + userId + " updated successfully!");
        viewEditUserCommands();
    }

    private void editPassword(String userId, Scanner in){
        System.out.print("\nEnter the new password: ");
        String password = getPassword(in);

        database.getUsers().stream().filter(user -> user.getId().equals(userId))
                                    .forEach(user -> user.setPassword(password));
        System.out.println("User " + userId + " updated successfully!");
        viewEditUserCommands();
    }

    private void editAccessLevel(String userId, Scanner in){
        System.out.print("\nSet the new access level: ");
        AccessLevel accessLevel = AccessLevel.fromString(getAccessLevel(in));

        database.getUsers().stream().filter(user -> user.getId().equals(userId))
                                    .forEach(user -> user.setTypeAccess(accessLevel));
        System.out.println("User " + userId + " updated successfully!");
        viewEditUserCommands();
    }

    private void editSubject(String courseId, Scanner in){
        System.out.print("\nEnter the new subject: ");
        String subject = getSubject(in);

        database.getCourses().stream().filter(course -> course.getCourseId().equals(courseId))
                                    .forEach(course -> course.setSubject(subject));
        System.out.println("Course " + courseId + " updated successfully!");
        viewEditCourseCommands();
    }

    private void editLecturer(String courseId, Scanner in){
        System.out.print("\nEnter the new lecturer id: ");
        String lecturerId = getLecturerId(in);

        Optional<Course> courseOptional = database.getCourses().stream()
                .filter(course -> course.getCourseId().equals(courseId))
                .findFirst();
        if(courseOptional.isPresent()){
            Course course = courseOptional.get();
            String previousLecturerId = course.getLecturerId();

            database.getUsers().stream().filter(user -> user.getId().equals(previousLecturerId))
                                        .map(user -> (Teacher) user)
                                        .forEach(teacher -> teacher.getAvailableCourses().remove(courseId));
        }


        database.getCourses().stream().filter(course -> course.getCourseId().equals(courseId))
                                    .forEach(course -> course.setLecturerId(lecturerId));
        database.getUsers().stream().filter(user -> user.getId().equals(lecturerId))
                                    .map(user -> (Teacher) user)
                                    .forEach(teacher -> teacher.getAvailableCourses().add(courseId));

        System.out.println("Course " + courseId + " updated successfully!");
        viewEditCourseCommands();
    }

    private void editTotalNumber(String courseId, Scanner in){
        System.out.print("\nChange total number of courses: ");
        int totalNr = Integer.parseInt(getTotalNumber(in));

        database.getCourses().stream().filter(course -> course.getCourseId().equals(courseId))
                                    .forEach(course -> course.setTotalNumber(totalNr));
        System.out.println("Course " + courseId + " updated successfully!");
        viewEditCourseCommands();
    }

    private void addStudentToCourse(String courseId, Scanner in){
        List<Student> studentsNotInCourse = database.getUsers().stream()
                .filter(user -> user.getTypeAccess() == AccessLevel.STUDENT)
                .map(user -> (Student) user)
                .filter(student -> !student.getAvailableCourses().contains(courseId))
                .toList();

        if(studentsNotInCourse.isEmpty()){
            System.out.println("\nThere are no students available to join");
        } else {
            for(User student: studentsNotInCourse){
                System.out.println(student.toString() + "\n");
            }
            System.out.print("\nSelect a student id: ");
            String studentId = getStudentId(studentsNotInCourse, in);

            database.getCourses().stream().filter(course -> course.getCourseId().equals(courseId))
                                .forEach(course -> course.getStudentAttendances().put(studentId, 0));
            database.getUsers().stream().filter(user -> user.getId().equals(studentId))
                                        .map(user -> (Student) user)
                                        .forEach(student -> student.getAvailableCourses().add(courseId));
            System.out.println("Student " + studentId + " has been successfully added to course " + courseId);
            viewEditCourseCommands();
        }
    }

    private void viewAdminCommands(){
        System.out.println("\n1. Users\n2. Courses\n3. System\n4. Log out");
    }

    private void viewUsersCommands(){
        System.out.println("\n1. View all users\n2. Add user\n3. Edit user\n4. Remove user\n5. Return to previous menu");
    }

    private void viewEditUserCommands(){
        System.out.println("\n1. Edit username\n2. Edit password\n3. Edit access level\n4. Return to previous menu");
    }

    private void viewCoursesCommands(){
        System.out.println("\n1. View all courses\n2. Add course\n3. Edit course\n4. Remove course\n5. Generate attendance reports" +
                "\n6. Return to previous menu");
    }

    private void viewEditCourseCommands(){
        System.out.println("\n1. Edit subject\n2. Edit lecturer\n3. Edit total number\n4. Add student to course\n5. Return to previous menu");
    }
}
