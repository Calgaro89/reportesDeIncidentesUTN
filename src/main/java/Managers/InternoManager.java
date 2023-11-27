package Managers;

import Entidades.Software;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class InternoManager {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");

    public static void cargarSoftware(Software software){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(software);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
