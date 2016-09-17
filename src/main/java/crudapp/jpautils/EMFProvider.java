package crudapp.jpautils;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFProvider
{
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = initializeFactory();

    private EMFProvider()
    {

    }

    private static EntityManagerFactory initializeFactory()
    {
        return Persistence.createEntityManagerFactory("CrudPU");
    }


    public static EntityManager createEntityManager()
    {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }



}
