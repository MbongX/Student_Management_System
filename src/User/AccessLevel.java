package User;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public enum AccessLevel{
    ADMINISTRATOR,STUDENT,TEACHER;

    public static AccessLevel fromString(String accessLevel){

        if(accessLevel.equalsIgnoreCase("Student")){
            return STUDENT;
        } else if(accessLevel.equalsIgnoreCase("Teacher")){
            return TEACHER;
        } else return ADMINISTRATOR;
    }
    
}
