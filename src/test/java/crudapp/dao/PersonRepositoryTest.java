package crudapp.dao;

import crudapp.model.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;


public class PersonRepositoryTest {

    private static PersonRepository personRepository;

    @BeforeClass
    public static void init() {
        personRepository = new PersonRepository();
    }

    @Test
    public void insertionTest()
    {
        Person p1 = new Person("A","B",10);
        personRepository.addPerson(p1);
        assertThat(personRepository.contains(p1),equalTo(true));

    }

    @Test
    public void removeTest()
    {
        Person p1 = new Person("B","C",11);
        personRepository.addPerson(p1);
        assertThat(personRepository.contains(p1),equalTo(true));
        personRepository.removePerson(p1);
        assertThat(personRepository.contains(p1),equalTo(false));
    }

    @Test
    public void getTest()
    {
        Person p1 = new Person("C","D",10);
        personRepository.addPerson(p1);
        Person p2 = personRepository.getPerson(p1.getId());
        assertThat(p1,equalTo(p2));
    }

    @Test
    public void updateTest()
    {
        Person p1 = new Person("E","F",20);
        personRepository.addPerson(p1);
        Person p2 = personRepository.getPerson(p1.getId());
        p2.setFirstName("AAAAAA");
        personRepository.updatePerson(p2);

        Person p3 = personRepository.getPerson(p2.getId());

        assertThat(p3,equalTo(p2));
        assertThat(p3,not(equalTo(p1)));


    }




}