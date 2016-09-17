package crudapp.dao;


import crudapp.jpautils.EMFProvider;
import crudapp.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class PersonRepository implements PersonDAO
{

    @Override
    public void addPerson(Person person)
    {
        executeInTransaction(entityManager -> entityManager.persist(person));
    }

    @Override
    public Person getPerson(Long id)
    {
        return executeInTransactionNonVoid(entityManager -> entityManager.find(Person.class,id)).get();
    }

    @Override
    public void updatePerson(Person person)
    {
        executeInTransaction(entityManager -> entityManager.merge(person));
    }

    @Override
    public void removePerson(Person person)
    {
        executeInTransaction(entityManager -> entityManager.remove(entityManager.contains(person) ? person : entityManager.merge(person)));
    }

    @Override
    public boolean contains(Person person)
    {
        Optional<Person> opt = executeInTransactionNonVoid(entityManager -> entityManager.find(Person.class,person.getId()));
        return opt.isPresent();

    }


    private void executeInTransaction(Consumer<EntityManager> func)
    {
        executeInTransactionNonVoid(entityManager ->
        {
            func.accept(entityManager);
            return 1;
        });


    }

    private <R> Optional<R> executeInTransactionNonVoid(Function<EntityManager,R> function)
    {
        EntityManager entityManager = EMFProvider.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<R> result = Optional.empty();


        try
        {
            transaction.begin();
            result = Optional.of(function.apply(entityManager));
            transaction.commit();
        }

        catch (Throwable t)
        {
            t.printStackTrace();
            transaction.rollback();
        }
        finally
        {
            entityManager.close();
        }

       return result;
    }



}
