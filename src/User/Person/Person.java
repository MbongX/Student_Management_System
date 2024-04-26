package User.Person;

import User.Person.Student.Student;
import User.User;
import User.AccessLevel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Person extends User {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private String name;
    private boolean gender;
    private Date dateOfBirth;
    private String address;
    private String telephone;
    private ArrayList<String> availableCourses = new ArrayList<>();
    protected boolean isProfileCreated = false;

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

    protected void createProfile(){
        System.out.println("\n---Creating your profile---");
        System.out.println("Name must contain only letters, minimum 2");
        System.out.println("Gender must be either male or female");
        System.out.println("Date of birth must have the following format - DD/MM/YYYY");
        System.out.println("The address must contain a minimum of 4 letters");
        System.out.println("Telephone number must have exactly 10 digits");

        System.out.print("\nEnter your name: ");
        String name = readName();
        System.out.print("\nEnter your gender: ");
        boolean gender = readGender();
        System.out.print("\nEnter your date of birth: ");
        Date date = readDate();
        System.out.print("\nEnter your address: ");
        String address = readAddress();
        System.out.print("\nEnter your telephone number: ");
        String telephone = readTelephone();

        database.getUsers().stream().filter(user -> user.getId().equals(getId()))
                .map(user -> (Person) user)
                .forEach(person -> {
                    person.name = name;
                    person.gender = gender;
                    person.dateOfBirth = date;
                    person.address = address;
                    person.telephone = telephone;
                    person.isProfileCreated = true;
                });
        System.out.println("\nYour profile has been created!");
    }

    protected void viewProfile(){
        System.out.println("\n------Your profile------");
        database.getUsers().stream().filter(user -> user.getId().equals(getId()))
                .map(user -> (Person) user)
                .forEach(person -> System.out.println(STR."\{person}"));
        viewStudentCommands();
    }

    protected void updateProfile(){
        System.out.println("\n---Editing your profile---");
        System.out.println("1. Edit name\n2. Edit gender\n3. Edit date of birth\n4. Edit address\n5. Edit telephone number\n6. Update profile");
        boolean returnsBack;

        do{
            System.out.print("\nUse a command (1,2,3,4,5,6) to perform an action -> ");
            String command = in.nextLine();
            returnsBack = false;

            switch (command){
                case "1" -> editName();
                case "2" -> editGender();
                case "3" -> editDateOfBirth();
                case "4" -> editAddress();
                case "5" -> editTelephone();
                case "6" -> returnsBack = true;
                default -> System.out.println("Invalid command! Please try again");
            }
        }while(!returnsBack);
        viewStudentCommands();
    }

    private String readName(){

        boolean isValidName = false;
        String pattern = "^[a-zA-Z]{2,}$";
        String name = "";

        while(!isValidName){
            name = in.nextLine();
            if(name.matches(pattern)){
                isValidName = true;
            } else System.out.println("\nInvalid name! Please try again");
        }

        return name;
    }

    private boolean readGender(){

        boolean isValidGender = false;
        String gender = "";

        while(!isValidGender){
            gender = in.nextLine();
            if(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")){
                isValidGender = true;
            } else System.out.println("\nInvalid gender! Please try again");
        }

        return gender.equalsIgnoreCase("Male");
    }

    private Date readDate(){

        boolean isValidDate = false;
        Date date = null;
        String dateOfBirth = "";

        while(!isValidDate){
            dateOfBirth = in.nextLine();
            try {
                date = DATE_FORMAT.parse(dateOfBirth);
                if(dateOfBirth.equals(DATE_FORMAT.format(date))){
                    isValidDate = true;
                } else System.out.println("\nInvalid date format! Please try again");
            } catch (ParseException e) {
                System.out.println("\nInvalid date format! Please try again");
            }
        }

        return date;
    }

    private String readAddress(){

        boolean isValidAddress = false;
        String pattern = "(.*[a-zA-Z]){4}.*";
        String address = "";

        while(!isValidAddress){
            address = in.nextLine();
            if(address.matches(pattern)){
                isValidAddress = true;
            } else System.out.println("\nInvalid address! Please try again");
        }

        return address;
    }

    private String readTelephone(){

        boolean isValidNumber = false;
        String telephone = "";
        String pattern = "\\d{10}";

        while(!isValidNumber){
            telephone = in.nextLine();
            if(telephone.matches(pattern)){
                isValidNumber = true;
            } else System.out.println("\nInvalid phone number! Please try again");
        }

        return telephone;
    }

    private void editName(){
        System.out.print("\nEnter the new name: ");
        String name = readName();

        database.getUsers().stream().filter(user -> user.getId().equals(getId()))
                .map(user -> (Person) user)
                .forEach(person -> person.setName(name));
        if(this instanceof Student){
            System.out.println(STR."Student \{getId()} updated successfully!");
        } else System.out.println(STR."Teacher \{getId()} updated successfully!");
        viewUpdateProfileCommands();
    }

    private void editGender(){
        System.out.print("\nEnter the new gender: ");
        boolean gender = readGender();

        database.getUsers().stream().filter(user -> user.getId().equals(getId()))
                .map(user -> (Person) user)
                .forEach(person -> person.setGender(gender));
        if(this instanceof Student){
            System.out.println(STR."Student \{getId()} updated successfully!");
        } else System.out.println(STR."Teacher \{getId()} updated successfully!");
        viewUpdateProfileCommands();
    }

    private void editDateOfBirth(){
        System.out.print("\nEnter the new date of birth: ");
        Date date = readDate();

        database.getUsers().stream().filter(user -> user.getId().equals(getId()))
                .map(user -> (Person) user)
                .forEach(person -> person.setDateOfBirth(date));
        if(this instanceof Student){
            System.out.println(STR."Student \{getId()} updated successfully!");
        } else System.out.println(STR."Teacher \{getId()} updated successfully!");
        viewUpdateProfileCommands();
    }

    private void editAddress(){
        System.out.print("\nEnter the new address: ");
        String address = readAddress();

        database.getUsers().stream().filter(user -> user.getId().equals(getId()))
                .map(user -> (Person) user)
                .forEach(person -> person.setAddress(address));
        if(this instanceof Student){
            System.out.println(STR."Student \{getId()} updated successfully!");
        } else System.out.println(STR."Teacher \{getId()} updated successfully!");
        viewUpdateProfileCommands();
    }

    private void editTelephone(){
        System.out.print("\nEnter the new telephone number: ");
        String telephone = readTelephone();

        database.getUsers().stream().filter(user -> user.getId().equals(getId()))
                .map(user -> (Person) user)
                .forEach(person -> person.setTelephone(telephone));
        if(this instanceof Student){
            System.out.println(STR."Student \{getId()} updated successfully!");
        } else System.out.println(STR."Teacher \{getId()} updated successfully!");
        viewUpdateProfileCommands();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(this.name).append("\nGender: ").append(toStringGender())
                .append("\nDate of birth: ").append(toStringDateOfBirth()).append("\nAddress: ").append(this.address)
                .append("\nTelephone: ").append(this.telephone);

        return builder.toString();
    }

    public String toStringGender(){
        return gender ? "Male" : "Female";
    }

    public String toStringDateOfBirth(){
        return DATE_FORMAT.format(dateOfBirth);
    }

    protected void viewStudentCommands(){
        System.out.println("\n1. View profile\n2. Update profile\n3. View grades\n4. Calculate overall grades\n5. Join course\n6. Log out");
    }

    private void viewUpdateProfileCommands(){
        System.out.println("\n1. Edit name\n2. Edit gender\n3. Edit date of birth\n4. Edit address\n5. Edit telephone number\n6. Update profile");
    }
}
