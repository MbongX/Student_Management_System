package User.Admin.Database;

import User.Person.Student.Assignment;
import User.User;
import User.AccessLevel;
import User.Admin.Database.Constants;
import User.Person.Course;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class Database {

    public static int GLOBAL_ID_USER;
    public static int GLOBAL_ID_COURSE;
    public static int GLOBAL_ID_ASSIGNMENT;
    private static Database INSTANCE;
    private ArrayList<User> users;
    private ArrayList<Course> courses;
    private ArrayList<Assignment> assignments;
    private Connection connection;
    private boolean connected;
    private boolean loggedIn;
    private String url;
    private int userID;
    private ArrayList<String> resultSets;
    private AccessLevel accessLevel = null;


    private Database(){
        users = new ArrayList<>();
        courses = new ArrayList<>();

        assignments = new ArrayList<>();
        resultSets = new ArrayList<>();
        setConnected(false);
        setConnection(null);
        setLoggedIn(false);
        GLOBAL_ID_USER = 1;
        GLOBAL_ID_COURSE = 1;
        GLOBAL_ID_ASSIGNMENT = 1;
        
        String url = Constants.getURL();
        String username = Constants.getSYSTEMUSER();
        String password = Constants.getSYSTEMPASSCODE();

        System.out.println("Connecting to the database...");

        try {
            setConnection(connection = DriverManager.getConnection(url, username, password));
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to the database!", e);
        }
        
    }

    public static Database getInstance(){

        if(INSTANCE == null){
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public Course getCourseById(String courseId){
        Course course_ = null;
        Optional<Course> courseOptional = INSTANCE.getCourses().stream().filter(course -> course.getCourseId().equals(courseId))
                .findFirst();
        if(courseOptional.isPresent()){
            course_ = courseOptional.get();
        }
        return course_;
    }

    public Assignment getAssignmentById(String assignmentId){
        Assignment assignment_ = null;
        Optional<Assignment> assignmentOptional = INSTANCE.getAssignments().stream()
                .filter(assignment -> assignment.getAssignmentId().equals(assignmentId))
                .findFirst();
        if(assignmentOptional.isPresent()){
            assignment_ = assignmentOptional.get();
        }
        
        return assignment_;
    }
    
    public boolean performLogin(String username, String password) {
        //Mysql Logic to check if user exists
        try {
            String user = null,pass = null;
            getInstance();
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from core_system.users where username = '" + username + "' and password = '" + password + "';");

            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                userID = userId;
                user = resultSet.getString("username");
                pass = resultSet.getString("password");
                //testing purposes only
                resultSets.add(String.valueOf(userId));
                resultSets.add(user);
                resultSets.add(pass);
                //System.out.println(getResultSets().toString());
            }

            //validate user and pass
            if ( (user.equals(username) && pass.equals(password)) ) {
                //check access Admin->Student->Teacher ?
                resultSet = statement.executeQuery("select * from core_system.administrator where id = " + String.valueOf(userID) + ";");
                //clear out the array
                resultSets.clear();
                while (resultSet.next()) {

                    int adminId = resultSet.getInt("admin_id");
                    int id = resultSet.getInt("id");
                    //add to arraylist
                    resultSets.add(String.valueOf(adminId));
                    resultSets.add(String.valueOf(id));
                }

                if (!resultSets.isEmpty()) {
                    //user is admin, check and assign access
                    if(resultSets.get(1).equals(String.valueOf(userID))) {
                        System.out.println("User is admin");
                        setAccessLevel(AccessLevel.ADMINISTRATOR);
                        setLoggedIn(true);
                    }
                } else {
                    if (resultSets.isEmpty()) {

                        //user not admin, check teacher
                        resultSet = statement.executeQuery("select * from core_system.teachers where id = " + userID + ";");
                        resultSets.clear();
                        while (resultSet.next()) {
                            int teacherID = resultSet.getInt("teacher_id");
                            //int userId = resultSet.getInt("student_id");
                            resultSets.add(String.valueOf(teacherID));
                            //resultSets.add(String.valueOf(userId));
                        }
                        //check if user is teacher
                        if(!resultSets.isEmpty()) {
                            if(resultSets.get(0).equals(String.valueOf(userID))) {
                                System.out.println("User is teacher");
                                setAccessLevel(AccessLevel.TEACHER);
                                setLoggedIn(true);
                            }else{
                                    System.out.println("User is not Teacher");
                                    setLoggedIn(false);
                            }
                        }else{
                            //check if user is student
                            resultSet = statement.executeQuery("select * from core_system.teachers where id = " + userID + ";");
                            resultSets.clear();
                            while(resultSet.next()){
                                int userId = resultSet.getInt("id");
                                int studentId = resultSet.getInt("student_id");
                                resultSets.add(String.valueOf(userId));
                                resultSets.add(String.valueOf(studentId));
                            }
                            //verify if user is student via id
                            if(resultSets.isEmpty()){
                                //user is not student || -_- weird
                                setLoggedIn(false);
                            }else{
                                if(resultSets.get(0).equals(String.valueOf(userID))) {
                                    System.out.println("User is student");
                                    setAccessLevel(AccessLevel.STUDENT);
                                    setLoggedIn(true);
                                }
                            }
                        }
                    }
                }
            }else {
                System.out.println("Invalid credentials");
                setLoggedIn(false);
            }
            
        }catch (Exception e) {
            setLoggedIn(false);
            throw new RuntimeException(e);
        }
        
        
        //create a counter to check how many times they have tried, if counter is exceeded terminate the application
        return isLoggedIn();
    }
    
    public void generateUsersTable(){
        ArrayList<String> ids = new ArrayList<>();
    }
    public void generateAdminsTable(){
        
    }
    public void genereateTeachersTable() {
        
    }
    public void generateStudentsTable(){
        
    }
    public void generateMarksTable() {
        
    }
    public void generateCoursesTable() {
        
    }
    public void generateBackupTable() {
        
    }
    public void generateEnrollmentTable() {
        
    }
    public void generateStudentCoursesTable() {
        
    }
    
    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public User getUserById(String userId){
        User user_ = null;
        Optional<User> userOptional = INSTANCE.getUsers().stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();
        if(userOptional.isPresent()){
            user_ = userOptional.get();
        }

        return user_;

    }
    
    public ArrayList<String> getResultSets() {
        return resultSets;
    }

    public void setResultSets(ArrayList<String> resultSets) {
        this.resultSets = resultSets;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int id) {
        this.userID = id;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
