package crudapp.dao;


import crudapp.jpautils.EMFProvider;
import crudapp.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomerRepository implements CustomerDAO
{


    @Override
    public boolean saveCustomerIfNotExists(Customer customer)
    {
        if(!containsCustomer(customer.getUsername()))
        {
            executeInTransaction(em -> em.persist(customer));
            return true;
        }

        return false;

    }


    @Override
    public Optional<Customer> getCustomerByUsername(String username)
    {
        List<Customer> result = executeInTransactionNonVoid(em ->
                em.createQuery("SELECT c FROM Customer c WHERE c.username = :username",Customer.class)
                   .setParameter("username",username)
                   .getResultList()).orElse(Collections.emptyList());

        return result.size() == 1 ? Optional.of(result.get(0)) : Optional.empty();
    }

    @Override
    public void updateCustomer(Customer customer)
    {
        if(containsCustomer(customer.getUsername()))
        {
            executeInTransaction(em -> em.merge(customer));
        }


    }

    @Override
    public boolean removeCustomer(String username)
    {
        Optional<Customer> result = getCustomerByUsername(username);

        if(result.isPresent())
        {
            executeInTransaction(em ->
                    em.remove(em.contains(result.get()) ? result.get() : em.merge(result.get())));
            return true;
        }

        return false;

    }

    @Override
    public boolean containsCustomer(String username)
    {
        return getCustomerByUsername(username).isPresent();
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
        R result = null;


        try
        {
            transaction.begin();
            result = function.apply(entityManager);
            transaction.commit();
        }

        catch (Throwable t)
        {
            t.printStackTrace();
            transaction.rollback();
        }
        finally
        {
            if ( (entityManager != null) && (entityManager.isOpen()))
                entityManager.close();
        }

        return result == null ? Optional.empty() : Optional.of(result);
    }
}
