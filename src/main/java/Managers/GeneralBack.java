package Managers;

import Entidades.*;
import org.example.MenuPrincipal;
import javax.persistence.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralBack {
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
            if (!nombre.matches("^[a-zA-Z]+( [a-zA-Z]+)*$")) {
                System.out.println("Nombre incorrecto, vuelva a ingresarlo: ");
            }
        } while (!nombre.matches("^[a-zA-Z]+( [a-zA-Z]+)*$"));
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

    public static List<Software> softwareDisponiblesClientesParaContratar(Cliente cliente) {
        List<Software> todosSoftwares = GeneralBack.listarSoftware();
        List<Software> softwareClientes = AreaComercialBack.obtenerServiciosClientes(cliente).stream()
                .map(ServicioCliente::getSoftware)
                .collect(Collectors.toList());
        List<Software> softwaresSinContratar = todosSoftwares.stream()
                .filter(software -> !softwareClientes.contains(software))
                .distinct()
                .collect(Collectors.toList());
        return softwaresSinContratar;
    }


    public static List<Software> softwareDisponiblesAgregarTecnicos(Tecnico tecnico) {
        List<Software> todosSoftwares = GeneralBack.listarSoftware();
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

    public static String obtenerNombreOApellido(String titulo) {
        System.out.println(titulo + ": ");
        String nombre = leer.next();
        if (!nombre.matches("^[a-zA-Z]+$")) {
            do {
                System.out.println(titulo + " incorrecto, vuelva a intentarlo: ");
                nombre = leer.next();
                leer.nextLine();
            } while (!nombre.matches("^[a-zA-Z]+$"));
        }
        return nombre;
    }

    public static String obtenerCuit() {
        String cuit;
        do {
            System.out.print("Cuit: ");
            cuit = leer.next();
            leer.nextLine();
        } while (!MetodosControl.validarFormatoCUIT(cuit));
        return cuit;
    }

    public static long obtenerCelular() {
        boolean celularCorrecto = false;
        long celular = 0;
        do {
            try {
                System.out.print("Celular: ");
                celular = leer.nextLong();
                leer.nextLine();
                celularCorrecto = true;
            } catch (InputMismatchException error) {
                System.out.println("Celular Incorrecto,vuelva a ingresarlo: ");
                leer.nextLine();
            }
        } while (!celularCorrecto);
        return celular;
    }

    public static String obtenerEmail() {
        String mail;
        do {
            System.out.print("E-Mail: ");
            mail = leer.next();
            leer.nextLine();
        } while (!MetodosControl.validarEmail(mail));
        return mail;
    }

    public static int obtenerNumeroInt(String titulo) {
        boolean numeroCorrecto = false;
        int numero = 0;
        do {
            try {
                System.out.print("DNI: ");
                numero = leer.nextInt();
                leer.nextLine();
                numeroCorrecto = true;
            } catch (InputMismatchException error) {
                System.out.println(titulo + " incorrecto,vuelva a ingresarlo: ");
                leer.nextLine();
            }
        } while (!numeroCorrecto);
        return numero;
    }

    public static int controlLargoDNI(String titutlo) {
        String numeroString;
        int numeroInt;
        do {
            numeroInt = obtenerNumeroInt(titutlo);
            numeroString = String.valueOf(obtenerNumeroInt(titutlo));
        } while (numeroString.length() == 8);
        return numeroInt;
    }

    public static boolean cambiarEstadoTecnico() {
        System.out.println("Nuevo Estado: ");
        System.out.print("1- Activo");
        System.out.print("-- 2- Inactivo");
        System.out.print("-- 3- Salir");
        int opcion;
        do{
            opcion = leerOpcionIndices(3);
        } while(opcion!= 3);
        return (opcion == 1);
    }


    public static int leerOpcionIndices(int indiceMaximo) {
        int indice = 0;
        while (true) {
            try {
                System.out.print("Ingrese Opcion (1-" + indiceMaximo + "): ");
                indice = MetodosControl.leer.nextInt();
                MetodosControl.leer.nextLine();
                if (indice >= 1 && indice <= indiceMaximo) {
                    break;
                } else {
                    System.out.println("Indice fuera de rango. Inténtelo de nuevo.");
                }
            } catch (InputMismatchException errorFormato) {
                System.out.println("Opción no válida. Por favor, elija una opción válida.");
                MetodosControl.leer.nextLine();
            }
        }
        return indice;
    }

    public static String obtenerDescripcionIncidente(){
        String descripcion;
        do{
            System.out.println("Descripcion incidente (Maximo 100 caracteres):");
            descripcion = leer.next();
            leer.nextLine();
        }while (descripcion.length()>100);
        return descripcion;
    }

    public static String tipoComunicacionReporteIncidente(){
        System.out.println("Vía comunicacion preferida");
        System.out.println("1- celular");
        System.out.println("2- email");
        int opcion = leerOpcionIndices(2);
        return ((opcion == 1)? "celular":"email");
    }


}
