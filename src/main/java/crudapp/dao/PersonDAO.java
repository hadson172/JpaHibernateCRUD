package crudapp.dao;

import crudapp.model.Person;



public interface PersonDAO
{
    void addPerson(Person person);      //C
    Person getPerson(Long id);          //R
    void updatePerson(Person person);   //U
    void removePerson(Person person);   //D
    boolean contains(Person person);
}
