package User.Admin.Database;

public class Constants {
    private static final String URL = "jdbc:mysql://localhost:3306/core_system"; 
    private static final String SYSTEMUSER = "sysadmin";
    private static final String SYSTEMPASSCODE = "KreativStorm@123";
    
    public Constants(){
        
    }

    public static String getSYSTEMPASSCODE() {
        return SYSTEMPASSCODE;
    }

    public static String getSYSTEMUSER() {
        return SYSTEMUSER;
    }

    public static String getURL() {
        return URL;
    }
}
