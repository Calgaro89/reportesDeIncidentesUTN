package Managers;

import Entidades.*;
import org.example.MenuPrincipal;
import javax.persistence.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GeneralBack {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");


    // ------------- INGRESO SOFTWARES NUEVO  -----------------------------------------------

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
        MenuPrincipal.inicioPrograma();
    }

    public static Software armarNuevoSoftware() {
        System.out.println("-----Nuevo Software----");
        Software software = new Software();
        software.setNombre(controlFormatoNombreOApellido("Software"));
        software.setEstado(true);
        return software;
    }

    // ------------- LISTAR SOFTWARES DISPONIBLES  -----------------------------------------------

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
    // ------------- LISTAR SOFTWARES POR CLIENTE  -----------------------------------------------

    public static List<Software> softwareDisponiblesClientesParaContratar(Cliente cliente) {
        List<Software> todosSoftwares = GeneralBack.listarSoftware();
        List<Software> softwareClientes = AreaComercialBack.obtenerServiciosClientes(cliente).stream()
                .map(ServicioCliente::getSoftware)
                .toList();
        return todosSoftwares.stream()
                .filter(software -> !softwareClientes.contains(software))
                .distinct()
                .collect(Collectors.toList());
    }

    // ------------- LISTAR SOFTWARES QUE UN TECNICO NO TIENE EN SU CARTERA -----------------

    public static List<Software> softwareDisponiblesAgregarTecnicos(Tecnico tecnico) {
        List<Software> todosSoftwares = GeneralBack.listarSoftware();
        List<Software> listaDeSoftwaresTecnico = RRHHManagerBack.obtenerServiciosTecnicos(tecnico)
                .stream()
                .map(ServicioTecnico::getSoftware)
                .toList();
        return todosSoftwares.stream()
                .filter(software -> !listaDeSoftwaresTecnico.contains(software))
                .distinct()
                .collect(Collectors.toList());
    }

    // --------------------------------------------------------------------------------------
    // ---------------- METODOS CONTROL INGRESO DATOS ---------------------------------------

    public static String controlFormatoNombreOApellido(String tituloLineaIngreso) {
        String nombre;
        do {
            nombre = Scanners.obtenerStringSinFormato(tituloLineaIngreso);
            if (!validarFormatoNombreApellido(nombre)) {System.out.println("Formato incorrecto");}
        } while (!validarFormatoNombreApellido(nombre));
        return nombre;
    }

    public static boolean validarFormatoNombreApellido(String nombre) {
        return (nombre.matches("^[a-zA-Z]+$"));
    }

    public static String controlFormatoCUIT(String titutloLineaIngreso) {
        String cuit;
        do {
            cuit = Scanners.obtenerStringSinFormato(titutloLineaIngreso);
            if (!validarFormatoCUIT(cuit)) {System.out.println("Cuit incorrecto, vuelva a ingresarlo en formato (xx-xxxxxxxx-x):");}
        } while (!validarFormatoCUIT(cuit));
        return cuit;
    }

    public static boolean validarFormatoCUIT(String cuit) {
        return (cuit.matches("\\d{2}-\\d{8}-\\d"));
    }

    public static String controlFormatoEmail(String tituloLineaIngreso) {
        String mail;
        do {
            mail = Scanners.obtenerStringSinFormato(tituloLineaIngreso);
            if (!validarEmail(mail)) {System.out.println("Formato mail incorrecto");}
        } while (!validarEmail(mail));
        return mail;
    }

    public static boolean validarEmail(String email) {
        return email.contains("@") && email.contains(".com");
    }

    public static long obtenerCelular(String tituloLineaIngreso) {
        long celular;
        do {
             celular = Scanners.obtenerNumeroLong("Celular");
        } while (!validarCelular(celular));
        return celular;
    }

    public static boolean validarCelular(Long numeroCelular) {
        return (numeroCelular.toString().length() == 10);
    }

    public static int controlLargoDNI(String titulo) {
        String numeroString;
        int numeroInt;
        do {
            numeroInt = Scanners.obtenerNumeroInt(titulo);
            ((Consumer<String>) s -> {if (s.length() != 8) {System.out.println("Formato DNI Incorrecto.");}}).accept(String.valueOf(numeroInt));
        } while (!validarDNI(numeroInt));
        return numeroInt;
    }
    public static boolean validarDNI(int DNI) {
        return (String.valueOf(DNI).length() == 8);
    }

    public static String controlDescripcionIncidente(String tituloLineaIngreso){
        String descripcion;
        do{
            descripcion = Scanners.obtenerCadenaString(tituloLineaIngreso);
        }while (descripcion.length()>100);
        return descripcion;
    }


    public static int controlOpcionIndices(int indiceMaximo) {
        int indice = 0;
        while (true) {
            indice = Scanners.obtenerNumeroInt("Opción");
            if (indice >= 1 && indice <= indiceMaximo) {
                break;
            } else {
                System.out.println("Indice fuera de rango.");
            }
        }
        return indice;
    }


    // --------------------------------------------------------------------------------------
    // ---------------- METODOS INGRESO DATOS SCANNER ---------------------------------------




}
