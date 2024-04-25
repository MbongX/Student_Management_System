package User.Person;

import User.User;
import User.AccessLevel;
import java.util.ArrayList;
import java.util.Date;

public class Person extends User {
    private String name;
    private boolean gender;
    private Date dateOfBirth;
    private String address;
    private String telephone;
    private ArrayList<String> availableCourses = new ArrayList<>();

    public Person() {

    }

    public Person(String id, String username, String password, AccessLevel typeAccess) {
        super(id, username, password, typeAccess);
    }

    public Person(String name, boolean gender, Date dateOfBirth, String address, String telephone) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public ArrayList<String> getAvailableCourses() {
        return availableCourses;
    }

    public void setAvailableCourses(ArrayList<String> availableCourses) {
        this.availableCourses = availableCourses;
    }
}
