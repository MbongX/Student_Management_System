package User.Person.Student;


import User.AccessLevel;
import User.Person.Course;
import User.Person.Person;
import User.User;

import java.util.*;
import java.util.stream.Collectors;

public class Student extends Person {
    private HashMap<String, ArrayList<String>> marks = new HashMap<>();
    private HashMap<Course, Double> overallGrades = new HashMap<>();

    public Student(){

    }

    public Student(String id, String username, String password, AccessLevel typeAccess) {
        super(id, username, password, typeAccess);
    }

    @Override
    public void start(){
        if(!isProfileCreated){
            createProfile();
        }
        System.out.println("\nYou are a student. You can select the following options:");
        System.out.println("1. View profile\n2. Update profile\n3. View grades\n4. Calculate overall grades\n5. Join course\n6. Log out");
        boolean isLoggedOut;

        do {
            System.out.print("\nUse a command (1,2,3,4,5,6) to perform an action -> ");
            String command = in.nextLine();

            isLoggedOut = false;
            switch (command) {
                case "1" -> viewProfile();
                case "2" -> updateProfile();
                case "3" -> viewGrades();
                case "4" -> calculateOverallGrades();
                case "5" -> joinCourse();
                case "6" -> isLoggedOut = true;
                default -> System.out.println("\nInvalid command! Please try again.");
            }
        }while(!isLoggedOut);

        logOut();
    }

    private HashMap<Course, ArrayList<Assignment>> getMarksById() {
        HashMap<Course, ArrayList<Assignment>> validMarks = new HashMap<>();
        for (Map.Entry<String, ArrayList<String>> entry : marks.entrySet()) {
            String courseId = entry.getKey();
            ArrayList<String> assignmentIds = entry.getValue();
            Course course = database.getCourseById(courseId);
            if (course != null) {
                ArrayList<Assignment> validAssignmentIds = new ArrayList<>();
                for (String assignmentId : assignmentIds) {
                    Assignment assignment = database.getAssignmentById(assignmentId);
                    if (assignment != null) {
                        validAssignmentIds.add(assignment);
                    }
                }
                validMarks.put(course, validAssignmentIds);
            }
        }
        return validMarks;
    }

    public List<Assignment> getAssignmentsFromCourseByStudentId(String courseId){
        return database.getUsers().stream()
                .filter(user -> user.getId().equals(getId()))
                .map(user -> (Student) user)
                .findFirst()
                .map(student -> {
                    List<String> assignmentIds = student.getMarks().get(courseId);
                    return assignmentIds.stream()
                            .map(database::getAssignmentById)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                })
                .orElse(Collections.emptyList());
    }


    public HashMap<String, ArrayList<String>> getMarks() {
        return marks;
    }

    public void setMarks(HashMap<String, ArrayList<String>> marks) {
        this.marks = marks;
    }

    public HashMap<Course, Double> getOverallGrades() {
        return overallGrades;
    }

    public void setOverallGrades(HashMap<Course, Double> overallGrades) {
        this.overallGrades = overallGrades;
    }

    private void viewGrades(){
        database.getUsers().stream().filter(user -> user.getId().equals(getId()))
                .map(user -> (Student) user)
                .forEach(Student::printGrades);
        viewStudentCommands();
    }

    private void calculateOverallGrades(){
        StringBuilder builder = new StringBuilder();
        database.getUsers().stream().filter(user -> user.getId().equals(getId()))
                .map(user -> (Student) user)
                .forEach(student -> {
                    for(Map.Entry<Course, ArrayList<Assignment>> entry: student.getMarksById().entrySet()){
                        double sum = 0;
                        if(entry.getValue().size() >= 2){
                            for(Assignment assignment: entry.getValue()){
                                sum += assignment.getGrade();
                            }
                        }
                        if(sum == 0){
                            student.getOverallGrades().put(entry.getKey(), 0.0);
                        } else student.getOverallGrades().put(entry.getKey(), sum / entry.getValue().size());
                    }
                    builder.append(student.getOverallGrades().isEmpty() ? "\nInsufficient data to calculate overall grades"
                            : "\nOverall grades were successfully calculated");
                });
        System.out.println(builder);
        viewStudentCommands();
    }

    private void joinCourse(){
        List<Course> coursesNotJoined = database.getCourses().stream()
                .filter(course -> !course.getStudentAttendances().containsKey(getId()))
                .toList();

        if(coursesNotJoined.isEmpty()){
            System.out.println("\nThere are no courses available to join");
        } else {
            for(Course course: coursesNotJoined){
                System.out.println(STR."\{course}\n");
            }
            System.out.print("\nSelect a valid course id: ");
            String courseId = getCourseId(coursesNotJoined);

            database.getCourses().stream().filter(course -> course.getCourseId().equals(courseId))
                    .forEach(course -> course.getStudentAttendances().put(getId(), 0));
            database.getUsers().stream().filter(user -> user.getId().equals(getId()))
                    .map(user -> (Student) user)
                    .forEach(student -> {
                        student.getAvailableCourses().add(courseId);
                        student.getMarks().put(courseId, new ArrayList<>());
                    });
            System.out.println(STR."You successfully joined course \{courseId}!");
        }
        viewStudentCommands();
    }

    private String getCourseId(List<Course> coursesNotJoined) {
        boolean isValid = false;
        String courseId = "";
        while (!isValid) {
            courseId = in.nextLine();
            String finalCourseId = courseId;
            if (coursesNotJoined.stream().anyMatch(course -> course.getCourseId().equals(finalCourseId))) {
                isValid = true;
            } else {
                System.out.println("Invalid course id! Please select a valid one");
            }
        }
        return courseId;
    }

    private void logOut(){
//        User user = new User();
//        user.start();
    }

    @Override
    public String toString() {
       return super.toString();
    }

    public void printGrades(){
        StringBuilder builder = new StringBuilder();
        if(getMarksById().isEmpty()){
            builder.append("You are not enrolled in any courses");
        }
        for(Map.Entry<Course, ArrayList<Assignment>> entry: getMarksById().entrySet()){
            builder.append("\n\nCourse: ").append(entry.getKey().getSubject());
            if(entry.getValue().isEmpty()){
                builder.append("\nThere are no assignments available");
            } else builder.append("\n---Assignments---");
            for(Assignment assignment: entry.getValue()){
                builder.append("\n").append(assignment.getName()).append(" -> ").append(assignment.getGrade());
            }
            if(!getOverallGrades().isEmpty()){
                double grade = getOverallGrades().get(entry.getKey());
                if(grade != 0){
                    builder.append("\nOverall grade: ").append(String.format("%.2f", grade));
                } else builder.append("\nCourse not finished yet");
            }
        }
        System.out.println(builder);
    }

}
