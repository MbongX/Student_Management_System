package User.Admin;

import User.User;
import User.AccessLevel;

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

    private void addUser(){
        String userId = String.valueOf(Database.GLOBAL_ID++);
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
            }
        }while(!returnsBack);

        viewUsersCommands();
    }

    private void removeUser(String userId){
        database.getUsers().removeIf(user -> user.getId().equals(userId));
        System.out.println("User " + userId + " has been removed");
        viewUsersCommands();
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
            } else System.out.println("\nInvalid id! Please select one that exists");
        }

        return userId;
    }

    private boolean isUnique(String username){
        return database.getUsers().stream().noneMatch(user -> user.getUsername().equals(username));
    }

    private void editUsername(String userId, Scanner in){
        System.out.print("\nEnter the new username: ");
        String username = getUsername(in);

        database.getUsers().stream().filter(user -> user.getId().equals(userId))
                                    .forEach(user -> user.setUsername(username));
        System.out.println("User " + userId + " updated successfully!");
        viewEditCommands();
    }

    private void editPassword(String userId, Scanner in){
        System.out.print("\nEnter the new password: ");
        String password = getPassword(in);

        database.getUsers().stream().filter(user -> user.getId().equals(userId))
                                    .forEach(user -> user.setPassword(password));
        System.out.println("User " + userId + " updated successfully!");
        viewEditCommands();
    }

    private void editAccessLevel(String userId, Scanner in){
        System.out.print("\nSet the new access level: ");
        AccessLevel accessLevel = AccessLevel.fromString(getAccessLevel(in));

        database.getUsers().stream().filter(user -> user.getId().equals(userId))
                                    .forEach(user -> user.setTypeAccess(accessLevel));
        System.out.println("User " + userId + " updated successfully!");
        viewEditCommands();
    }

    private void viewAdminCommands(){
        System.out.println("\n1. Users\n2. Courses\n3. System\n4. Log out");
    }

    private void viewUsersCommands(){
        System.out.println("\n1. View all users\n2. Add user\n3. Edit user\n4. Remove user\n5. Return to previous menu");
    }

    private void viewEditCommands(){
        System.out.println("\n1. Edit username\n2. Edit password\n3. Edit access level\n4. Return to previous menu");
    }
}
