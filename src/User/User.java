package User;


import User.Admin.Database.Database;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;

public class User {
    //fields/attributes
    String id, username, password, errorMessage;
    String inUsername, inPassword;
    boolean valid, validUsername, validPassword, loggedIn;
    char[] cPassword;
    ArrayList<Message> messagess;
    AccessLevel typeAccess;
    protected Scanner in = new Scanner(System.in);
    Console console = System.console();
    protected Database database = Database.getInstance();

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

    //Constructurs
    public User() {
        inPassword = "";
        inUsername = "";
    }
    public User(String id, String username, String password, AccessLevel typeAccess) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.typeAccess = typeAccess;
    }

    //methods
    public void start() {
        System.out.println("//////////////////////////////////////////////////////////////");
        System.out.println("//               Student Management System                  //");
        System.out.println("//////////////////////////////////////////////////////////////");
        //System.out.println("Login:");
        System.out.println("Please enter your Username and Password:\nNote: Password should meet the below criteria : \n-Should be at least 6 characters \n-Should including at least 1 lowercase \n-Should include at least 1 uppercase \n-Should contain at least 1 digit \n-Should contain at least one special character");

        do {
            //validate Username
            if (!validateUsername(inUsername)) {
                do {
                    System.out.print("Enter username:");
                    inUsername = in.nextLine();

                    if (!validateUsername(inUsername)) {
                        //print out error message
                        System.out.println(getErrorMessage());
                        //flag as invalid
                        validUsername = false;
                    } else {
                        //set the Username
                        setUsername(inUsername);
                        //flag as valid
                        validUsername = true;
                    }
                }
                while (!validateUsername(inUsername));
            }
            do {
                System.out.print("Enter password:");
                cPassword = console.readPassword();
                if (console == null) {
                    System.out.println("Exiting , console not available");
                    System.exit(1);
                }

                //Create a StringBuffer object to use when building the cPassword string from the cPassword array
                StringBuilder sb = new StringBuilder();

                //iterate through the char array using a for-each loop
                for (char x : cPassword) {
                    //append each char into the stringBuilder variable
                    sb.append(x);
                }
                //convert the StringBuffer to a string
                inPassword = sb.toString();

                //validate Password
                if (!validatePassword(inPassword)) {
                    //set flag outcome
                    validPassword = false;
                    //print out error message
                    System.out.println(getErrorMessage());

                } else {
                    if (validatePassword(inPassword)) {
                        //hash and set the password
                        hashPasscode(inPassword);
                    }
                }
            }
            while (!validatePassword(inPassword));
        }
        while (!validUsername || !validPassword);
        System.out.println("Attempting to login ...");
        login(getUsername(), getPassword());
    }

    boolean isUsernameValid(String username) {
        //using a regex pattern to validate the username content
        return username.matches("^[a-zA-Z0-9]+$");
    }

    boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$");
    }

    boolean validatePassword(String password) {
        //get string length
        int strLen = password.length();

        if (password.isEmpty()) {
            setErrorMessage("Password cannot be empty or les than 6 characters!");
            valid = false;
        }
        if (strLen <= 6) {
            setErrorMessage("Password cannot be empty or les than 6 characters!");
            valid = false;
        } else {
            if (isPasswordValid(password)) {
                valid = true;
            }
            if (!isPasswordValid(password)) {
                setErrorMessage("Invalid password format!");
                valid = false;
            }
        }
        return valid;
    }


    boolean validateUsername(String vUsername) {
        //get the string length
        int strLen = vUsername.trim().length();

        //Validate string from User Input
        if (strLen == 0) {
            errorMessage = "Username cannot be empty!, Please try again";
            valid = false;
        }
        if (strLen > 0) {
            if (!isUsernameValid(vUsername)) {
                errorMessage = "Invalid name format please try again!";
                valid = false;
            } else {
                valid = true;
            }
        }
        return valid;
    }

    String enhanceHashString(String hashKey) {

        String newHash, splitHash1, splitHash2;
        //Get string length and divisor value
        int strLen = hashKey.length(), divisor = strLen / 2;

        //divide the hashkey into 2 parts by using the divisor
        splitHash1 = hashKey.substring(0, divisor);
        //System.out.println(splitHash1);
        splitHash2 = hashKey.substring(divisor);
        //System.out.println(splitHash2);

        //inverse the string using the reverse function provided by the StringBuilder Class
        StringBuilder reverseString1 = new StringBuilder(splitHash1).reverse();
        StringBuilder reverseString2 = new StringBuilder(splitHash2).reverse();

        //combine the new hashkeys
        newHash = reverseString1.toString().concat(reverseString2.toString());
        //System.out.println(newHash);
        //return new hash
        return newHash;
    }

    public boolean login(String username, String password) {
        //given both username and password have been set and validated
        //Open connection to Database
        //perform validation of db credentials aginst local credentials to flagging it as succussfully logged in
        validateCredentials(getPassword(), getPassword());
        //retrieve access type

        return false;
    }

    public void hashPasscode(String hashPasscode) {
        //encrypt password before setting it on the password variable
        try { //try-catch block to cater for hashing/encryption exception

            // Create a new MessageDigest object to enable encryption, returned hash value is a byte array| Can look into surrounding the below statement with a try-catch block
            MessageDigest digestPassword = MessageDigest.getInstance("SHA-256");

            //Storing the hash value within a byte array
            byte[] encodedHash = digestPassword.digest(hashPasscode.getBytes(StandardCharsets.UTF_8));

            //converting the byte array into a string via StringBuffer
            StringBuilder hashString = new StringBuilder();

            for (byte b : encodedHash) {
                String hash = Integer.toHexString(0xff & b);
                if (hash.length() == 1) {
                    hashString.append('0');
                }
                hashString.append(hash);
            }
            //assign new string into the hashpassword attribute
            String hashPassword = hashString.toString();

            //encrypt string before setting into the string attribute
            setPassword(enhanceHashString(hashPassword));
            //System.out.println(password);
            validPassword = true;

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: algorithm not available.");
            //on excepection Call ToPrintStackTrace
            e.printStackTrace();
            setErrorMessage(e.getMessage());
        }
    }

    boolean validateCredentials(String username, String password) {
        // Note : This method will perform validation of the credentials against a db like system
        loggedIn = false;  //default value

        return valid;
    }

    public void sendMessage(Message message) {

    }

    public void viewMessage(Message message) {

    }

    public String toStringUser() {
        return "Id: " + id + "\nUsername: " + username + "\nPassword: " + password + "\nTypeAccess: " + typeAccess;
    }

    @Override
    public String toString() {
        return "\nId: " + id + "; Username: " + username;
    }
}