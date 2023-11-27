package Managers;

import Entidades.Cliente;
import Entidades.ServicioCliente;
import javax.persistence.*;
import java.util.List;

public class AreaComercialBack {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");

    public static void cargarCliente() {
        Cliente cliente = Scanners.crearCliente();
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
        Cliente clienteUpdate = Scanners.modificarDatosClientes(cliente);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(clienteUpdate);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static void eliminarCliente(Cliente cliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Cliente cliente_eliminar = entityManager.find(Cliente.class, cliente.getIdCliente());
            entityManager.remove(cliente_eliminar);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static List<ServicioCliente> obtenerServiciosClientes(Cliente cliente){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ServicioCliente> serviciosCliente;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT s FROM ServicioCliente s WHERE s.cliente.idCliente = :idCliente";
            serviciosCliente = entityManager.createQuery(jpql, ServicioCliente.class)
                    .setParameter("idCliente",cliente.getIdCliente())
                    .getResultList();
        } finally {
            entityManager.close();
        }
        return serviciosCliente;
    }

    public static Cliente buscarClienteParametros(String consulta, String parametro, int valorInt, long valorLong, String valorString){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Cliente cliente;
        try {
            entityManager.getTransaction().begin();
            if (valorString == null && valorInt != 0) {
                cliente = entityManager.createQuery(consulta, Cliente.class).setParameter(parametro, valorInt).getSingleResult();
            } else if (valorString == null && valorInt == 0){
                cliente = entityManager.createQuery(consulta, Cliente.class).setParameter(parametro, valorLong).getSingleResult();
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

    public static void agregarServiciosClientes(ServicioCliente servicioCliente){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(servicioCliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static void consultaCliente(Cliente cliente){
        System.out.println("Datos Cliente:");
        System.out.println(cliente);
        AreaComercialBack.obtenerServiciosClientes(cliente).forEach(s -> System.out.println(s.getSoftware().getNombre()));
    }
}

