package Managers;

import Entidades.*;
import org.example.MenuPrincipal;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

public class InternoBack {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");
    public static java.util.Scanner leer = new java.util.Scanner(System.in);

    public static void cargarSoftware() {
        Software software = armarNuevoSoftware();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(software);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        System.out.println("Software agregado con exito.");
        MenuPrincipal.menuPrincipal();
    }

    public static Software armarNuevoSoftware() {
        System.out.println("-----Nuevo Software----");
        String nombre;
        do {
            System.out.print("Nombre: ");
            nombre = leer.nextLine();
            if (!nombre.matches("^[a-zA-Z]+( [a-zA-Z]+)*$")){
                System.out.println("Nombre incorrecto, vuelva a ingresarlo: ");
            }
        }while(!nombre.matches("^[a-zA-Z]+( [a-zA-Z]+)*$"));
        Software software = new Software();
        software.setNombre(nombre);
        software.setEstado(true);
        return software;
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

    public static List<Software> softwareDisponiblesClientesParaContratar(Cliente cliente){
        List<Software> todosSoftwares = InternoBack.listarSoftware();
        List<Software> softwareClientes = AreaComercialBack.obtenerServiciosClientes(cliente).stream()
                .map(ServicioCliente::getSoftware)
                .collect(Collectors.toList());
        List<Software> softwaresSinContratar = todosSoftwares.stream()
                .filter(software -> !softwareClientes.contains(software))
                .distinct()
                .collect(Collectors.toList());
        return softwaresSinContratar;
    }

    public static List<Software> softwareDisponiblesAgregarTecnicos(Tecnico tecnico){
        List<Software> todosSoftwares = InternoBack.listarSoftware();
        List<Software> listaDeSoftwaresTecnico = RRHHManagerBack.obtenerServiciosTecnicos(tecnico)
                .stream()
                .map(ServicioTecnico::getSoftware)
                .collect(Collectors.toList());
        List<Software> softwaresSinExperiencia = todosSoftwares.stream()
                .filter(software -> !listaDeSoftwaresTecnico.contains(software))
                .distinct()
                .collect(Collectors.toList());
        return softwaresSinExperiencia;
    }

}
