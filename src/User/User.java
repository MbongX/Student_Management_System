package User;

import java.util.ArrayList;
import java.util.Scanner;

public class User{
    //fields/attributes
    String id,username,password,errorMessage;
    AccessLevel typeAccess;
    ArrayList<Message> messagess;
    Scanner in = new Scanner(System.in);
    
    
    //getters and setters

    public String getId() {
         return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public AccessLevel getTypeAccess() {
        return typeAccess;
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
    public User(){
        
        System.out.println("//////////////////////////////////////////////////////////////");
        System.out.println("//               Student Management System                  //");
        System.out.println("//////////////////////////////////////////////////////////////");
        //System.out.println("Login:");
        System.out.println("Please enter your username and password:\nNote: Password should be at least 6 characters, including lowercase, uppercase, digits and at least one special character");
        
        do{
            System.out.println("Username:");
            username = in.nextLine();
            //validate username
            if(validateUsername(username) == false){
                System.out.println(getErrorMessage());
            }else {
                System.out.println("Password:");
                password = in.nextLine();
                //validate password 

            }          

        }
        while(username.equals("") || password.equals(""));
        

    }

    //methods
    boolean isUsernameValid(String username){
        
    }
    
    boolean validateUsername(String vNameInput)
    {
        //validating based on input parameter where 1-> Strings, 2 ->doubles

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