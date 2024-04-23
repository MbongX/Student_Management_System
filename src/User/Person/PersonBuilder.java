package User.Person;

import java.util.Date;

public class PersonBuilder {
    private Person person;

    public PersonBuilder(){
        person = new Person(null, false, null, null, null);
    }

    public PersonBuilder setName(String name){
        person.setName(name);
        return this;
    }

    public PersonBuilder setGender(boolean gender){
        person.setGender(gender);
        return this;
    }

    public PersonBuilder setDate(Date date){
        person.setDateOfBirth(date);
        return this;
    }

    public PersonBuilder setAddress(String address){
        person.setAddress(address);
        return this;
    }

    public PersonBuilder setTelephone(String telephone){
        person.setTelephone(telephone);
        return this;
    }

    public Person build(){
        return person;
    }
}
