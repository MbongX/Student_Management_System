package User.Admin;

import User.User;
import User.AccessLevel;
import java.util.Scanner;

public class Administrator extends User {

    Database database = Database.getInstance();

    public void start(){

        System.out.println("You are an administrator. You can select the following options:");
        System.out.println("1. Users\n2. Courses\n3. System\n4. Log out");
        boolean isLoggedOut;

        do {
            System.out.print("\nUse a command (1,2,3,4) to perform an action -> ");
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();

            isLoggedOut = false;
            switch (command) {
                case "1" -> accessUsers();
                case "2" -> accessCourses();
                case "3" -> accessSystem();
                case "4" -> isLoggedOut = true;
                default -> {
                    System.out.println("\nInvalid command! Please try again.");
                }
            }
        }while(!isLoggedOut);

        logOut();
    }

    private void accessUsers(){
        System.out.println("You have accessed the Users options:");
        System.out.println("1. View all users\n2. Add user\n3. Edit user\n4. Remove user\n5. Return to previous menu");
        boolean returnsBack;

        do{
            System.out.print("\nUse a command (1,2,3,4,5) to perform an action -> ");
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();

            returnsBack = false;
            switch (command){
                case "1" -> viewAllUsers();
                case "2" -> addUser();
                case "3" -> editUser();
                case "4" -> removeUser();
                case "5" -> returnsBack = true;
            }
        }while(!returnsBack);
    }

    private void accessCourses(){

    }

    private void accessSystem(){

    }

    private void logOut(){
        User user = new User();
    }

    private void viewAllUsers(){
        if(database.getUsers().size() == 0){
            System.out.println("There are no users in the database");
        }
        for(User user: database.getUsers()){
            System.out.println(user);
        }
    }

    private void addUser(){
        String userId = String.valueOf(Database.GLOBAL_ID++);
        Scanner in = new Scanner(System.in);
        System.out.println("\n---Create a new user---");
        System.out.println("Username must be minimum 4 characters, only letters and digits");
        System.out.println("Password must be minimum 6 characters, at least one lowercase, one uppercase, one digit and one special character");
        System.out.println("Access level must be Student, Teacher or Admin");

        System.out.print("Username: ");
        String username = getUsername(in);
        System.out.print("\nPassword: ");
        String password = getPassword(in);
        System.out.print("\nAccess level: ");
        AccessLevel accessLevel = AccessLevel.fromString(getAccessLevel(in));

        database.getUsers().add(new User(userId, username, password, accessLevel));
        System.out.println("A new user has been added");
    }

    private void editUser(){

    }

    private void removeUser(){

    }

    private String getUsername(Scanner in){

        boolean isValidUsername = false;
        String pattern = "^[a-zA-Z0-9]{4,}$";
        String username = "";

        while(!isValidUsername){
            username = in.nextLine();
            if(username.matches(pattern) && isUnique(username)){
                isValidUsername = true;
            } else System.out.println("\nInvalid username! Please try again");
        }

        return username;
    }

    private String getPassword(Scanner in){

        boolean isValidPassword = false;
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
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

    private boolean isUnique(String username){
        return database.getUsers().stream().noneMatch(user -> user.getUsername().equals(username));
    }
}
