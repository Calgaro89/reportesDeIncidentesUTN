package Managers;

import Entidades.Cliente;
import Entidades.Software;
import javax.persistence.*;
import java.util.List;

public class AreaComercialBack {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");

    public static void cargarCliente(Cliente cliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        cliente.setEstado(true);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static Cliente buscarClienteId(int idCliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            return entityManager.find(Cliente.class, idCliente);
        } finally {
            entityManager.close();
        }
    }

    public static Cliente buscarClienteCUIT(String cuit) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Cliente cliente;
        try {
            entityManager.getTransaction().begin();
            try {
               return cliente = entityManager.createQuery("SELECT t FROM Cliente t WHERE cuit = :cuit", Cliente.class).setParameter("cuit", cuit).getSingleResult();
           } catch (NoResultException cliente_null){
               return null;
           }
        } finally {
            entityManager.close();
        }
    }

    public static List<Cliente> obtenerTodosLosClientes() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            return entityManager.createQuery("SELECT t FROM Cliente t", Cliente.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

     public static void bajaCliente(Cliente cliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            cliente.setEstado(false);
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static void actualizarDatosCliente(Cliente cliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static void eliminarCliente(int idCliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Cliente cliente = entityManager.find(Cliente.class, idCliente);
            entityManager.remove(cliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static List<Software> obtenerServiciosClientes(Cliente cliente){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Software> servicios;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT s FROM Software s WHERE s.cliente.idCliente = :clienteId";
            servicios = entityManager.createQuery(jpql,Software.class)
                    .setParameter("idCliente",cliente.getIdCliente())
                    .getResultList();
        } finally {
            entityManager.close();
        }
        return servicios;
    }

    public static Cliente buscarClienteParametros(String consulta, String parametro, long valorInt, String valorString){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Cliente cliente;
        try {
            entityManager.getTransaction().begin();
            if (valorString == null) {
                cliente = entityManager.createQuery(consulta, Cliente.class).setParameter(parametro, valorInt).getSingleResult();
            } else {
                cliente = entityManager.createQuery(consulta, Cliente.class).setParameter(parametro, valorString).getSingleResult();
            }
            try {
            } catch (NoResultException cliente_null){
                return null;
            }
        } finally {
            entityManager.close();
        }
        return cliente;
    }
}
