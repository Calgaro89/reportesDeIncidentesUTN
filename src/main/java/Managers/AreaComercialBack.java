package Managers;

import Entidades.Cliente;
import Entidades.ServicioCliente;
import Entidades.Software;
import org.example.MenuPrincipal;

import javax.persistence.*;
import java.awt.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AreaComercialBack {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");

    // ------------- CARGAR CLIENTE A LA BASE DE DATOS -----------------------------------------

    public static void cargarCliente() {
        Cliente cliente = crearCliente();
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
    // ------------- BUSCAR CLIENTE POR PARAMETRO QUE RECIBE EL METODO ---------------------------

    public static Cliente buscarClienteParametros(String consulta, String parametro, int valorInt, long valorLong, String valorString){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Cliente cliente;
        try {
            entityManager.getTransaction().begin();
            if (valorString == null && valorInt != 0) {
                cliente = entityManager.createQuery(consulta, Cliente.class).setParameter(parametro, valorInt).getSingleResult();
            } else
                cliente = entityManager.createQuery(consulta, Cliente.class).setParameter(parametro, Objects.requireNonNullElse(valorString, valorLong)).getSingleResult();
            } catch (NoResultException cliente_null){
                return null;
            }
            finally {
            entityManager.close();
        }
        return cliente;
    }
    // ------------- BUSCAR CLIENTE POR CUIT ------------------------------------------------
    public static Cliente buscarClienteCUIT(String cuit) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            try {
               return entityManager.createQuery("SELECT t FROM Cliente t WHERE cuit = :cuit", Cliente.class).setParameter("cuit", cuit).getSingleResult();
           } catch (NoResultException cliente_null){
               return null;
           }
        } finally {
            entityManager.close();
        }
    }
    // ------------- DAR DE BAJA A UN CLIENTE (Estado == false) -----------------------------
     public static void bajaCliente(Cliente cliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
         cliente.setEstado(false);
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
    // ------------- CARGAR DATOS ACTUALIZADOS DE UN CLIENTE ---------------------------------
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
    // ------------- ELIMINAR UN CLIENTE ----------------------------------------------------------
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
    // ------------- LISTAR LOS SERVICIO CONTRATADOS POR UN CLIENTE -------------------------------
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

    // ------------- DAR DE ALTA A SERVICIO CONTRATADO DE CLIENTE ---------------------------------
    public static void altaServiciosClientes(ServicioCliente servicioCliente){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(servicioCliente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
    // ------------- DAR DE BAJA A SERVICIO CONTRATADO DE CLIENTE ---------------------------------
    public static void bajaServiciosCliente(Cliente cliente) {
        ServicioCliente servicioClienteABaja = armadoServicioClienteParaBaja(cliente);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(servicioClienteABaja);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    // ------------- CONSULTAR DATOS CLIENTE -------------------------------------------------
    public static void consultarDatosCliente(Cliente cliente){
        System.out.println("Datos Cliente:");
        System.out.println(cliente);
        AreaComercialBack.obtenerServiciosClientes(cliente).forEach(s -> System.out.println(s.getSoftware().getNombre()));
    }

    // ------------- ARMAR NUEVO CLIENTE ----------------------------------------------------
    public static Cliente crearCliente() {
        System.out.println("AREA COMERCIAL: Nuevo cliente.");
        Cliente cliente = new Cliente();
        cliente.setNombre(Scanners.obtenerStringSinFormato("Nombre"));
        cliente.setCuit(GeneralBack.controlFormatoCUIT("CUIT"));
        cliente.setCelular(GeneralBack.obtenerCelular("Celular"));
        cliente.setMail(GeneralBack.controlFormatoEmail("Email:"));
        cliente.setEstado(true);
        return cliente;
    }

    // ------------- BUSCAR CLIENTE ------------------------------------------------------
    public static Cliente buscarClientesParametros(int opcion) {
        String consulta = null;
        String parametro = null;
        long valorLong = 0;
        int valorInt = 0;
        String valorString = null;
        switch (opcion) {
            case 1:
                consulta = "SELECT t FROM Cliente t WHERE nombre = :nombre";
                parametro = "nombre";
                valorString = GeneralBack.controlFormatoNombreOApellido("Nombre");
                break;
            case 2:
                consulta = "SELECT t FROM Cliente t WHERE cuit = :cuit";
                parametro = "cuit";
                valorString = GeneralBack.controlFormatoCUIT("CUIT");
                break;
            case 3:
                consulta = "SELECT t FROM Cliente t WHERE idCliente = :idCliente";
                parametro = "idCliente";
                valorInt = Scanners.obtenerNumeroInt("idCliente");
                break;
            case 4:
                consulta = "SELECT t FROM Cliente t WHERE mail = :mail";
                parametro = "mail";
                valorString = GeneralBack.controlFormatoEmail("email:");
                break;
            case 5:
                consulta = "SELECT t FROM Cliente t WHERE celular = :celular";
                parametro = "celular";
                valorLong = GeneralBack.obtenerCelular("Celular");
                break;
            case 6:
                AreaComercialBack.ingresoAreaComercial();
                break;
            default:
                System.out.println("Opción no válida. Por favor, elija una opción válida.");
        }
        return AreaComercialBack.buscarClienteParametros(consulta, parametro, valorInt, valorLong, valorString);
    }

    private static void controlResultadoBusquedaCliente(Cliente cliente) {
        if (cliente == null) {
            System.out.println("Cliente inexistente");
            if (MetodosControl.otro("¿Desea buscar por otro parámetro?")) {
                ingresoBuscarClienteAsociado();
            } else {
                ingresoAreaComercial();
            }
        } else {
            System.out.println(cliente);
            if (MetodosControl.otro("¿Desea ingresar con este cliente?")) {
                ingresoClienteAsociado(cliente);
            }
        }
    }

    // ------------- MODIFICAR DATOS CLIENTE -----------------------------------------------

    public static void ingresarModificarDatosClientes(Cliente cliente) {
        int maximoOvolver, opcion;
        do {
             maximoOvolver = AreaComercialFront.mostrarMenuModificarCliente(cliente);
             opcion = GeneralBack.controlOpcionIndices(maximoOvolver);
            AreaComercialBack.procesarOpcionModificarDatosClientes(opcion, cliente);
        } while (opcion != maximoOvolver);
    }

    public static void procesarOpcionModificarDatosClientes(int opcion, Cliente cliente) {
        switch (opcion) {
            case 1: modificarNombre(cliente);break;
            case 2: modificarCUIT(cliente);break;
            case 3: modificarEmail(cliente);break;
            case 4: modificarCelular(cliente); break;
            case 5: AreaComercialFront.mostrarTablaClientesAsociado(cliente);break;
        }
    }

    private static void modificarNombre(Cliente cliente) {
        cliente.setNombre(GeneralBack.controlFormatoNombreOApellido("Nuevo nombre"));
        actualizarDatosCliente(cliente);
    }

    private static void modificarCUIT(Cliente cliente) {
        cliente.setCuit(GeneralBack.controlFormatoCUIT("Nuevo CUIT"));
        actualizarDatosCliente(cliente);
    }

    private static void modificarEmail(Cliente cliente) {
        cliente.setMail(GeneralBack.controlFormatoEmail("Nuevo email"));
        actualizarDatosCliente(cliente);
    }

    private static void modificarCelular(Cliente cliente) {
        cliente.setCelular(GeneralBack.obtenerCelular("Nuevo Celular"));
        actualizarDatosCliente(cliente);
    }

    // ------------- CARGAR NUEVO SERVICIO CLIENTE -----------------------------------------------

    public static void cargarNuevoServicioCliente(Cliente cliente){
        do{
            ServicioCliente servicioCliente;
            List<Software> softwaresSinContratar = GeneralBack.softwareDisponiblesClientesParaContratar(cliente);
            if (!softwaresSinContratar.isEmpty()){
                servicioCliente = armarServiciosClientes(cliente);
                altaServiciosClientes(servicioCliente);
            } else {
                System.out.println("No hay softwares para agregar a la cartera del cliente");
                AreaComercialFront.mostrarTablaClientesAsociado(cliente);
            }
        } while (MetodosControl.otro("Agregar otro servicio?"));
    }
    // ------------- ARMAR SERVICIO CLIENTE -----------------------------------------------

    public static ServicioCliente armarServiciosClientes(Cliente cliente) {
        ServicioCliente servicioCliente = new ServicioCliente();
        servicioCliente.setEstado(true);
        servicioCliente.setCliente(cliente);
        List<Software> softwareList = GeneralBack.softwareDisponiblesClientesParaContratar(cliente);
        int opcion = GeneralBack.controlOpcionIndices(mostrarServiciosDisponiblesParaEseCliente(softwareList));
        servicioCliente.setSoftware(softwareList.get(opcion - 1));
        return servicioCliente;
    }

    private static int mostrarServiciosDisponiblesParaEseCliente(List<Software> softwareList) {
        System.out.println("Servicios: ");
        int indice = 1, maximo = 0;
        for (Software software : softwareList) {
            if (software.isEstado()) {
                System.out.println(indice + ". " + software.getNombre());
                indice++; maximo++;
            }
        } return maximo;
    }


    // ------------- DAR DE BAJA SERVICIO CLIENTE -------------------------------------------
    public static ServicioCliente armadoServicioClienteParaBaja(Cliente cliente) {
        List<Software> softwaresCliente = listarSoftwareDeCliente(cliente);
        int maximo = mostrarServiciosDisponiblesParaEseCliente(softwaresCliente);
        int indice = GeneralBack.controlOpcionIndices(maximo);
        Software softwareBaja = softwaresCliente.get(indice - 1);
        ServicioCliente servicioClienteEliminar = AreaComercialBack.obtenerServiciosClientes(cliente).stream()
                .filter(servicioCliente -> servicioCliente.getSoftware().equals(softwareBaja))
                .findFirst()
                .orElseThrow();
        servicioClienteEliminar.setEstado(false);
        return servicioClienteEliminar;
    }

    public static List<Software> listarSoftwareDeCliente(Cliente cliente){
        return obtenerServiciosClientes(cliente).stream()
                .map(ServicioCliente::getSoftware)
                .collect(Collectors.toList());
    }

    // ------------- INGRESO ACCIONES CLIENTE ASOCIADO -------------------------------------

    public static void ingresoClienteAsociado(Cliente cliente) {
        int opcion, maximoOvolver;
        do {
            maximoOvolver = AreaComercialFront.mostrarTablaClientesAsociado(cliente);
            opcion = GeneralBack.controlOpcionIndices(maximoOvolver);
            opcionesTablaClienteAsociado(opcion, cliente);
        } while (opcion != maximoOvolver);
    }

    public static void opcionesTablaClienteAsociado(int opcion, Cliente cliente) {
            switch (opcion) {
                case 1:ingresarModificarDatosClientes(cliente);break;
                case 2:bajaCliente(cliente);break;
                case 3:eliminarCliente(cliente);break;
                case 4:consultarDatosCliente(cliente);break;
                case 5:cargarNuevoServicioCliente(cliente);break;
                case 6:bajaServiciosCliente(cliente);break;
                case 7: ingresoAreaComercial();break;
                case 8:System.exit(0);break;
            }
    }
    // ------------- INGRESO ACCIONES AREA COMERCIAL -------------------------------------

    public static void metodosTablaAreaComercial(int opcion){
        switch (opcion) {
            case 1: cargarCliente();break;
            case 2: ingresoBuscarClienteAsociado(); break;
            case 3: MenuPrincipal.inicioPrograma();break;
            case 4: System.exit(0); break;
        }
    }

    public static void ingresoAreaComercial(){
        int opcion, maximoOvolver;
        do{
            maximoOvolver = AreaComercialFront.mostrarTablaAreaComercial();
            opcion = GeneralBack.controlOpcionIndices(maximoOvolver);
            metodosTablaAreaComercial(opcion);
        } while (opcion != maximoOvolver-1);
    }

    // ------------- BUSCAR CIUDADO ASOCIADO INGRESO AREA COMERCIAL---------------------------
    public static void ingresoBuscarClienteAsociado(){
        int opcion, maximoOvolver;
        do{
            maximoOvolver = AreaComercialFront.mostrarTablaBuscarClienteAsociado();
            opcion = GeneralBack.controlOpcionIndices(maximoOvolver);
            controlResultadoBusquedaCliente(buscarClientesParametros(opcion));
        } while (opcion != maximoOvolver);
    }
}

