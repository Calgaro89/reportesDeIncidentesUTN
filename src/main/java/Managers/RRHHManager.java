package Managers;

import Entidades.Tecnico;
import org.example.MenuPrincipal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;


public class RRHHManager {
    private static Scanner leer = new Scanner(System.in);
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");


    public static void recursosHumanos(){
        int opcion;
        System.out.println("------------------------");
        System.out.println("1 - Alta de Tecnico");
        System.out.println("2 - Actualizar Tenico");
        System.out.println("3 - Lista de Tecnicos");
        System.out.println("4 - Eliminar Tecnico");
        System.out.println("5 - Menu Principal");
        System.out.println("6 - Salir");
        System.out.print("Opción: ");
        opcion = leer.nextInt();
        Tecnico tecnico ;

        switch (opcion) {

            case 1:
                tecnico = new Tecnico();
                System.out.println("Ingrese nombre");
                tecnico.setNombre(leer.next());
                System.out.println("Ingrese apellido");
                tecnico.setApellido(leer.next());
                System.out.println("Ingrese DNI");
                tecnico.setDni(leer.nextInt());
                tecnico.setEstado(true);
                RRHHManager.cargarTecnico(tecnico); break;
            case 2:
                tecnico = new Tecnico();
                System.out.println("Ingrese DNI del Tecnico");
                int dni = leer.nextInt();
                for (Tecnico tec : RRHHManager.obtenerTodosLosTecnicos()){
                    if (tec.getDni() == dni){
                        tecnico = tec;
                        System.out.println(tec);
                    }

                }

                // RRHHManager.actualizarDatosTecnico(tecnico);
                ;break;
            case 3: ;RRHHManager.obtenerTodosLosTecnicos().forEach(System.out::println); break;
            case 4: ; break;
            case 5: ;MenuPrincipal.menuPrincipal(); break;
            case 6: ; break;
            default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
        }while (opcion < 1 || opcion > 6);

    }

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
        List<Tecnico> tecnicos;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT t FROM Tecnico t WHERE t.conocimiento = :conocimiento";
            tecnicos = entityManager.createQuery(jpql, Tecnico.class).setParameter("conocimiento", conocimiento).getResultList();
        } finally {
            entityManager.close();
        } return tecnicos;
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


