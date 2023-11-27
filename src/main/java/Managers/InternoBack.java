package Managers;

import Entidades.Software;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class InternoBack {
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

    public static List<Software> listarSoftware() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Software> softwares;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT s FROM Software s";
            softwares = entityManager.createQuery(jpql, Software.class).getResultList();
        } finally {
            entityManager.close();
        }
        return softwares;
    }
}
