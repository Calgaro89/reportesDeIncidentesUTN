package Entidades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ClienteManager {
    private static final String PERSISTENCE_UNIT_NAME = "JPA_PU";

public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            String jpql = "SELECT c FROM Cliente c WHERE c.nombre = :nombre";
            List<Cliente> clientes = entityManager.createQuery(jpql, Cliente.class)
                    .setParameter("nombre", "Pablo")
                    .getResultList();
            entityManager.getTransaction().commit();

            clientes.forEach(System.out::println);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }


}


