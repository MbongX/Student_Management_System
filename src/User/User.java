package User;


import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;

public class User{
    //fields/attributes
    String id = "",username = "",password = "",errorMessage = "";
    AccessLevel typeAccess;
    ArrayList<Message> messagess;
    boolean valid;
    Scanner in = new Scanner(System.in);
    Console console = System.console();
    
    
    //getters and setters

    public String getId() {
         return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) { 
        this.username = username; 
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password; 
    }
    public AccessLevel getTypeAccess() {
        return typeAccess;
    }
    public void setTypeAccess(AccessLevel typeAccess) {
        this.typeAccess = typeAccess; 
    }
    public ArrayList<Message> getMessagess() {
        return messagess;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String newErrorMessage) {
        this.errorMessage = newErrorMessage;
    }

    // Setter


    //Constructors
    public User(){

    }

    public User(String id, String username, String password, AccessLevel typeAccess) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.typeAccess = typeAccess;
    }

    //methods

    public void start(){

        System.out.println("//////////////////////////////////////////////////////////////");
        System.out.println("//               Student Management System                  //");
        System.out.println("//////////////////////////////////////////////////////////////");
        //System.out.println("Login:");

        System.out.println("Please enter your username and password:\nNote: Password should be at least 6 characters, including lowercase, uppercase, digits and at least one special character");

        do{
            System.out.println("Username:");
            username = in.nextLine();
            //validate username
            if(username.equals("")){

            }
            // password masking implementation
            Console console = System.console();
            char[] passwordChars = console.readPassword("Enter your password: ");
            password = new String(passwordChars);
            Arrays.fill(passwordChars, ' ');
        }
        while(username.equals("") || password.equals(""));
    }


    boolean isNameValid(String name) {
        // Implement your validation logic here
        // For example, check if the name contains only letters and spaces
        return name.matches("[a-zA-Z ]+");

    }
    
    boolean validateUsername(String vNameInput)
    {

        //get the string length
        int strLen = vNameInput.trim().length();

        //Validate string from User Input
        if(strLen == 0)
        {
            errorMessage = "Name cannot be empty!, Please try again";
            valid = false;
        }
        if(strLen>0)
        {
            if(!isNameValid(vNameInput))
            {
                errorMessage = "Invalid name format please try again!";
                valid = false;
            }
            else
            {
                valid = true;
            }
        }
        return valid;
    }
    
    public boolean login(String username, String password)
    {
        
        return false;
    }
    public void hashPasscode(String password){
        
    }
    boolean validateCredentials( String username, String password)
    {
        // Note : This method will perform validation of the credentials against a db like system
        boolean valid = false;  //default value
        
        if(username.equals("") || password.equals(""))
        {
            if(username.equals("") && password.equals(""))
            {
                errorMessage ="Username and password are required";
                valid = false;
            }
            else {
                switch (username) {
                    case "":
                        setErrorMessage("Username cannot be empty");
                        valid = false;
                        break;
                }
                switch (password) {
                    case "":
                        setErrorMessage("Password cannot be empty");
                        valid = false;
                        break;
                }
            }
        }
        else{
            if(username.trim().length() > 0 && password.trim().length() > 0)
            {
                
            }
        }
        
        return valid;
    }
    public void sendMessage(Message message)
    {
        
    }
    public void viewMessage(Message message)
    {
        
    }

    @Override
    public String toString() {
        return "Id: " + id + "\nUsername: " + username + "\nPassword: " + password + "\nTypeAccess: " + typeAccess;
    }
}
