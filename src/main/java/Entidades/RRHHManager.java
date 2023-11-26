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
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tecnico);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public static Tecnico buscarTenico(int idTecnico) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Tecnico tecnico = null;
        try {
            entityManager.getTransaction().begin();
            tecnico = entityManager.find(Tecnico.class, idTecnico);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return tecnico;
    }

    public static List<Tecnico> obtenerTodosLosTecnicos() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tecnico> listaTecnicos = null;
        try {
            entityManager.getTransaction().begin();
            listaTecnicos = entityManager.createQuery("SELECT p FROM Producto p", Tecnico.class).getResultList();
            entityManager.close();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
         return listaTecnicos;
    }







    }


