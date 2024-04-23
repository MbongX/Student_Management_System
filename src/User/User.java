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



    //Constructurs
    public User() {
        
        String inUsername = "",inPassword = "";
        char[] cPassword;
        boolean validUsername = false;
        boolean validPassword = false;
        
        
        System.out.println("//////////////////////////////////////////////////////////////");
        System.out.println("//               Student Management System                  //");
        System.out.println("//////////////////////////////////////////////////////////////");
        //System.out.println("Login:");
        System.out.println("Please enter your Username and Password:\nNote: Password should be at least 6 characters, including lowercase, uppercase, digits and at least one special character");
        
        do{

            
            //validate inUsername
            if(validateUsername(inUsername) == false){
                do {
                    System.out.println("Username:");
                    inUsername = in.nextLine();
                    
                    if(validateUsername(inUsername) == false) {
                        //print out error message
                        System.out.println(getErrorMessage());
                        //flag as invalid
                        validUsername = false;
                    }else{
                        //set the Username
                        setUsername(inUsername);
                        //flag as valid
                        validUsername = true;
                    }
                }
                while(validateUsername(inUsername) == false);
            }
            do {
                System.out.println("Password:");

                cPassword = console.readPassword();
                if (console == null) {
                    System.out.println("Exiting , console not available");
                    System.exit(1);
                }

                //Create a StringBuffer object to use when building the cPassword string from the cPassword array
                StringBuffer sb = new StringBuffer();

                //iterate through the char array using a for-each loop
                for (char x : cPassword) {
                    //append each char into the stringbuffer variable
                    sb.append(x);
                }
                //convert the StringBuffer to a string
                inPassword = sb.toString();


                //validate Password
                if (validatePassword(inPassword) != true) {
                    System.out.println(getErrorMessage());
                    validPassword = false;
                } 
                if (validatePassword(inPassword)){
                    //encrypt password before setting it on the password variable
                    
                    //try-catch block to cater for hashing/encryption exception
                    try {
                        // Create a new MessageDigest object to enable encryption, returned hash value is a byte array| Can look into surrounding the velow statement with a try-catch block
                        MessageDigest digestPassword = MessageDigest.getInstance("SHA-256");

                        //Storing the hash value within a byte array
                        byte[] encodedHash = digestPassword.digest(password.getBytes(StandardCharsets.UTF_8));

                        //converting the byte array into a string via StringBuffer
                        StringBuffer hashString = new StringBuffer();

                        for (byte b : encodedHash) {
                            String hash = Integer.toHexString(0xff & b);
                            if (hash.length() == 1) {
                                hashString.append('0');
                            }
                            hashString.append(hash);

                            hashString.append(hash);
                        }
                        password = hashString.toString();
                        System.out.println(password);
                        validPassword = true;
                        
                    } catch (NoSuchAlgorithmException e) {
                        System.err.println("Error: algorithm not available.");
                        e.printStackTrace();
                    }

                }
            }
            while(validatePassword(inPassword) == false);
            
        }
        while(validUsername == false || validPassword == false);
    }

    //methods

    boolean isUsernameValid(String username){
        //extract code from classes.rar
        String pattern = "^[a-zA-Z0-9]+$"; 
        return username.matches(pattern);
    }
    
    boolean validatePassword(String password){
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
}
//template classes
// Vlad you can also join the audio call so we can communicate efficiently
// Thank you