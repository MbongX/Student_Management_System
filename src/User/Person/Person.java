package User.Person;

import User.User;
import java.util.ArrayList;
import java.util.Date;

public class Person extends User {
    private String name;
    private boolean gender;
    private Date dateOfBirth;
    private String address;
    private String telephone;
    private ArrayList<String> availableCourses = new ArrayList<>();
    private PersonBuilder personBuilder = new PersonBuilder();

    public Person() {

    }

    public Person(String name, boolean gender, Date dateOfBirth, String address, String telephone) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.telephone = telephone;
    }

    public Person createProfile(String name, boolean gender, Date dateOfBirth, String address, String telephone){
        return personBuilder.setName(name).setGender(gender).setDate(dateOfBirth).setAddress(address).setTelephone(telephone)
                .build();
    }

    public Person changeName(String name){
        personBuilder.setName(name);
        return this;
    }

    public Person changeGender(boolean gender){
        personBuilder.setGender(gender);
        return this;
    }

    public Person changeDate(Date date){
        personBuilder.setDate(date);
        return this;
    }

    public Person changeAddress(String address){
        personBuilder.setAddress(address);
        return this;
    }

    public Person changeTelephone(String telephone){
        personBuilder.setTelephone(telephone);
        return this;
    }

    public Person updateProfile(){
        return personBuilder.build();
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
