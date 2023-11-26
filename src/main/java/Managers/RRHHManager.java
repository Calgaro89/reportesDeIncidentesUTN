package Managers;

import Entidades.Tecnico;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class RRHHManager {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");

    public static void cargarTecnico(Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static Tecnico buscarTecnico(int idTecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            return entityManager.find(Tecnico.class, idTecnico);
        } finally {
            entityManager.close();
        }
    }

    public static List<Tecnico> obtenerTodosLosTecnicos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            return entityManager.createQuery("SELECT t FROM Tecnico t", Tecnico.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public static List<Tecnico> tecnicosPorConocimiento(String conocimiento) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT t FROM Tecnico t WHERE t.conocimiento = :conocimiento";
            Query query = entityManager.createQuery(jpql, Tecnico.class);
            query.setParameter("conocimiento", conocimiento);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public static void bajaTecnico(Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            tecnico.setEstado(false);
            entityManager.merge(tecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static void actualizarDatosTecnico(Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static void eliminarTecnico(int idTecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Tecnico tecnico = entityManager.find(Tecnico.class, idTecnico);
            entityManager.remove(tecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}


