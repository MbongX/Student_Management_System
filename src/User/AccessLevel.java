package User;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class AccessLevel extends User {
    public AccessLevel() throws NoSuchAlgorithmException {
    }

    public enum AccessLevels {
        ADMINISTRATOR,STUDENT,TEACHER

    }
    
}
