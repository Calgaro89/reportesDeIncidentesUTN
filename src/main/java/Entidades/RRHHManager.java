package Entidades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class RRHHManager {
    public static void cargarTenico(Tecnico tecnico) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        try {
            entityManager.persist(tecnico);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }







    }


