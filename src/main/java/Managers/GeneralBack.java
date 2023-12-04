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


    // ---------------- DAR BAJA A SOFTWARE ---------------------------------------
    public static void ingresoBajaSoftware(){
        List<Software> softwares = listarSoftwareActivos();
        if (!softwares.isEmpty()){
            int maximo, opcion;
            do {
                maximo = mostrarListaSoftwares(softwares);
                opcion = controlOpcionIndices(maximo);
                darDebajaSoftware(listarSoftwareActivos().get(opcion-1));
            } while (!MetodosControl.otro("Dar de baja a otro software?"));
        } else {
            System.out.println("No posee softwares activos");
            MenuPrincipal.inicioPrograma();
        }
    }

    public static List<Software> listarSoftwareActivos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Software> softwares;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT s FROM Software s WHERE s.estado = :estado";
            softwares = entityManager.createQuery(jpql, Software.class).setParameter("estado", true).getResultList();
        } finally {
            entityManager.close();
        }
        return softwares;
    }

    public static int mostrarListaSoftwares(List<Software> softwares){
        int indice = 0;
        for (Software software : softwares) {
            indice ++;
            System.out.println(indice + " - " + software.getNombre());
        }
        return indice;
    }

    public static void darDebajaSoftware(Software software){
        software.setEstado(false);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try {
                entityManager.getTransaction().begin();
                entityManager.merge(software);
                entityManager.getTransaction().commit();
            } finally {
                entityManager.close();
                System.out.println("Baja software correcta con exito.");
            }
            MenuPrincipal.inicioPrograma();
        }


    // ---------------- DAR ALTA A SOFTWARE ---------------------------------------


    public static void ingresoAltaSoftware(){
        List<Software> softwares = listarSoftwareInactivos();
        if (!softwares.isEmpty()){
            int maximo, opcion;
            do {
                maximo = mostrarListaSoftwares(softwares);
                opcion = controlOpcionIndices(maximo);
                darAltaSoftware(listarSoftwareInactivos().get(opcion-1));
            } while (!MetodosControl.otro("Dar de baja a otro software?"));
        } else {
            System.out.println("No posee softwares inactivos");
            MenuPrincipal.inicioPrograma();
        }
    }

    public static List<Software> listarSoftwareInactivos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Software> softwares;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT s FROM Software s WHERE s.estado = :estado";
            softwares = entityManager.createQuery(jpql, Software.class).setParameter("estado", false).getResultList();
        } finally {
            entityManager.close();
        }
        return softwares;
    }

    public static void darAltaSoftware(Software software){
        software.setEstado(true);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(software);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            System.out.println("Alta software correcta con exito.");
        }
        MenuPrincipal.inicioPrograma();
    }
}
