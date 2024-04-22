package User;

import java.io.Console;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;

public class User{
    //fields/attributes
    String id,username,password,errorMessage;
    AccessLevel typeAccess;
    ArrayList<Message> messagess;
    boolean valid;
    Scanner in = new Scanner(System.in);
    
    
    //getters and setters

    public String getId() {
         return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) { this.password = password; }
    public AccessLevel getTypeAccess() {
        return typeAccess;
    }
    public void setTypeAccess(AccessLevel typeAccess) { this.typeAccess = typeAccess; }
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

        Console console = System.console();

        if (console == null){
            System.out.println("Exiting , console not available");
            System.exit(1);
        }
        
        String username = "";
        char[] password;

        boolean validUsername = false;
        boolean validassword = false;
        
        System.out.println("//////////////////////////////////////////////////////////////");
        System.out.println("//               Student Management System                  //");
        System.out.println("//////////////////////////////////////////////////////////////");
        //System.out.println("Login:");
        System.out.println("Please enter your username and password:\nNote: Password should be at least 6 characters, including lowercase, uppercase, digits and at least one special character");
      /*  
        do{
            System.out.println("Username:");
            username = in.nextLine();
            
            //validate username
            if(validateUsername(username) == false){
                System.out.println(getErrorMessage());
                validUsername = false;
            }else {
                System.out.println("Password:");
                password = console.readPassword();
                //validate password
                

            }
                // password masking implementation
            Console console = System.console();
            char[] passwordChars = console.readPassword("Enter your password: ");
            password = new String(passwordChars);
            Arrays.fill(passwordChars, ' ');

                
                if(isValidPassword(password.toString()) != true){
                    System.out.println(getErrorMessage());
                    //validPassword = false;
                }

                

            }          


        }
        while(username.equals("") || password.equals(""));
        */

    }

    //methods

    boolean isUsernameValid(String username){
        //extract code from classes.rar
        String pattern = "^[a-zA-Z0-9]+$"; 
        return username.matches(pattern);
    }
    
    boolean isValidPassword(String password){
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return password.matches(pattern);
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
            if(!isUsernameValid(vNameInput))
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
    
    boolean validateCredentials(String username, String password)
    {
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
}
//template classes
// Vlad you can also join the audio call so we can communicate efficiently
// Thank you