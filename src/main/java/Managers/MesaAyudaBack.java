package Managers;

import Entidades.*;
import org.example.MenuPrincipal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;


public class MesaAyudaBack {
    static List<Software> listaSoftwaresClientes;
    static Cliente cliente;
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");


    // ------------- INGRESO MESA AYUDA -----------------------------------------------

    public static void ingresoMesaAyuda(){
        int opcion, maximoOvolver;
        do{
            maximoOvolver = MesaAyudaFront.mostrarOpcionesMesaDeAyuda();
            opcion = GeneralBack.controlOpcionIndices(maximoOvolver);
            opcionesIngresoMesaDeAyuda(opcion);
        } while (opcion != maximoOvolver-1);
    }
    public static void opcionesIngresoMesaDeAyuda(int opcion) {
                switch (opcion) {
                    case 1:
                        ingresoCuentaConCuit();
                        break;
                    case 2:
                        System.out.println("Seleccionaste: Contratar servicios");
                        break;
                    case 3:
                        MenuPrincipal.mostrarMenuPrincipal();
                        break;
                    case 4:
                        System.out.println("¡Hasta luego!");
                        break;
                }
            }

    // ------------- INGRESO CUENTA CON CUIT --------------------------------------------
    public static void ingresoCuentaConCuit() {
        boolean salir = false;
        do {
            cliente = AreaComercialBack.buscarClienteCUIT(GeneralBack.controlFormatoCUIT(Scanners.obtenerStringSinFormato("CUIT")));
                if (cliente == null) {
                    System.out.println("Cliente inexistente");
                } else {
                    salir = true;
                    ingresoMesaAyudaConCUITAprobado();
                }
        } while (!salir);
    }

    // ------------- INGRESO MESA AYUDA CUENTA CON CUIT OK ----------------------------------

    public static void ingresoMesaAyudaConCUITAprobado(){
        int opcion, maximoOvolver;
        do{
            maximoOvolver = MesaAyudaFront.mostrarOpciones(cliente);
            opcion = GeneralBack.controlOpcionIndices(maximoOvolver);
            mesaDeAyudaCuentaAsociado(opcion);
        } while (opcion != maximoOvolver+1);
    }
    public static void mesaDeAyudaCuentaAsociado(int opcion) {
        switch (opcion) {
            case 1:
                consultarSuscripciones(cliente);
                break;
            case 2:
                crearIncidente();
                break;
            case 3:
                mostrarListaDeIncidentesNoResueltos(buscarIncidentesPorCliente(cliente));
                break;
            case 4:
                mostrarHistorialIncidentesPersona(buscarIncidentesPorCliente(cliente));
                break;
            case 5:
                System.exit(0);
                break;
        }
    }
    // ------------- LISTAR Y MOSTRAR SOFTWARES DE UN CLIENTE -------------------------------------
    public static int consultarSuscripciones(Cliente cliente) {
        int indice = 0;
         listaSoftwaresClientes = AreaComercialBack.listarSoftwareDeCliente(cliente);
        if (listaSoftwaresClientes.isEmpty()) {
            System.out.println("No posee servicios contratados.");
        } else {
            indice = mostarSoftwaresContratadosCliente(listaSoftwaresClientes);
        } return indice;
    }

    public static int mostarSoftwaresContratadosCliente(List<Software> listaSoftwaresClientes) {
        System.out.println("Servicios Contratados: ");
        int indice = 0;
        for (Software softwares : listaSoftwaresClientes) {
            indice++;
            System.out.println(indice + ". " + softwares.getNombre());
        }
        return indice;
    }
    // ------------- REPORTAR UN PROBLEMA -------------------------------------
    public static ServicioCliente iniciarReporteProblemas() {
        int indiceMaximo = consultarSuscripciones(cliente);
        int opcion = GeneralBack.controlOpcionIndices(indiceMaximo);
        Software software = listaSoftwaresClientes.get(opcion);
        return armarServicioClienteReporteProblema(software);
    }
    public static ServicioCliente armarServicioClienteReporteProblema(Software software){
        return AreaComercialBack.obtenerServiciosClientes(cliente).stream()
                .filter(servicio -> servicio.getSoftware().getNombre().equals(software.getNombre()))
                .findFirst()
                .orElse(null);
    }

    public static void crearIncidente(){
        Incidente incidente = new Incidente();
        incidente.setServicioCliente(iniciarReporteProblemas());
        incidente.setDescripcion(GeneralBack.controlDescripcionIncidente("Ingrese descripcion. (Maximo 1000 caracteres"));
        incidente.setFechaIngreso(LocalDateTime.now());
        incidente.setTipoComunicacion(tipoComunicacionReporteIncidente());
        incidente.setEstado(false);
        IncidenteManagerBack.asignarTecnicoIncidente(incidente);
        cargarIncidente(incidente);
    }


    // ------------- CARGAR A LA BD UN INCIDENTE -------------------------------------

    public static void cargarIncidente(Incidente incidente){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        incidente.setEstado(false);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(incidente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    // ------------- VER INCIDENTES POR CLIENTE -------------------------------------

    public static List<Incidente> buscarIncidentesPorCliente(Cliente cliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Incidente> incidentes;
        String consulta = "SELECT t FROM Incidente t WHERE idCliente = :idCliente";
        try {
            entityManager.getTransaction().begin();
            incidentes = entityManager.createQuery(consulta, Incidente.class).setParameter("idCliente", cliente.getIdCliente()).getResultList();
        } catch (NoResultException incidentes_null) {
            System.out.println("No posee incidentes");
            return null;
        } finally {
            entityManager.close();
        }
        return incidentes;
    }

    public static void mostrarListaDeIncidentesNoResueltos(List<Incidente> incidentes){
        incidentes.stream().filter(incidente -> !incidente.isEstado()).forEach(System.out::println);
    }

    public static void mostrarHistorialIncidentesPersona(List<Incidente> incidentes){
        incidentes.stream().filter(Incidente::isEstado).forEach(System.out::println);
    }

    // ------------- DAR INCIDENTES POR RESUELTO -------------------------------------
    public static void resolverIncidenteServicioCliente(Incidente incidente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
            incidente.setEstado(true);
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(incidente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static String tipoComunicacionReporteIncidente(){
        System.out.println("Vía comunicacion preferida");
        System.out.println("1- celular");
        System.out.println("2- email");
        int opcion = GeneralBack.controlOpcionIndices(2);
        return ((opcion == 1)? "celular":"email");
    }
}
