package Managers;

import Entidades.Cliente;
import org.example.MenuPrincipal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class AreaComercial {
    private static Scanner leer = new Scanner(System.in);
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");

    public static void areaComercial(){
        int opcion;
        do{
            System.out.println("-------AREA COMERCIAL--------");
            System.out.println("1 - Alta de Cliente");
            System.out.println("2 - Actualizar Cliente");
            System.out.println("3 - Eliminar Cliente");
            System.out.println("4 - Menu Principal");
            System.out.println("5 - Salir");

            opcion = leer.nextInt();

            switch (opcion) {
                case 1: ; break;
                case 2: ; break;
                case 3: ; break;
                case 4: MenuPrincipal.menuPrincipal(); break;
                case 5: MenuPrincipal.clearScreen(); break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion < 1 || opcion > 5);

    }

    public static void cargarCliente(Cliente cliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static Cliente buscarCliente(int idCliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            return entityManager.find(Cliente.class, idCliente);
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
}

