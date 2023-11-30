package Managers;

import Entidades.ServicioCliente;
import Entidades.ServicioTecnico;
import Entidades.Software;
import Entidades.Tecnico;
import org.example.MenuPrincipal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;

public class RRHHManagerBack {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");
    public static java.util.Scanner leer = new java.util.Scanner(System.in);

    // ------------- CARGAR NUEVO TECNICO ----------------------------------------------------

    public static Tecnico cargarTecnico(Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return tecnico;
    }
    // ------------- ARMAR UN NUEVO SERVICIO TECNICO -------------------------------------------------

    public static void ingresoArmarYCargarServicioTecnico(Tecnico tecnico) {
        do {
            ServicioTecnico servicioTecnico = armarListaServiciosTecnicosPosibles(tecnico);
            RRHHManagerBack.cargarNuevoServiciosTecnicos(servicioTecnico);
        } while (MetodosControl.otro("¿Desea agregar otra expertise?"));
    }

    private static ServicioTecnico armarListaServiciosTecnicosPosibles(Tecnico tecnico) {
        List<Software> softwaresSinExperiencia = GeneralBack.softwareDisponiblesAgregarTecnicos(tecnico);
        if (softwaresSinExperiencia.isEmpty()) {
            System.out.println("No hay softwares para agregar a la expertise del técnico");
            RRHHManagerBack.ingresoIndiceTecnicoAsocaido(tecnico);
            return null;
        }
        ServicioTecnico servicioTecnico = nuevosServicioTenicos(tecnico, softwaresSinExperiencia);
        return servicioTecnico;
    }

    private static ServicioTecnico nuevosServicioTenicos(Tecnico tecnico, List<Software> softwaresPosibles) {
        int indice = 1;
        ServicioTecnico servicioTecnico = new ServicioTecnico();
        System.out.println("Servicios: ");
        for (Software software : softwaresPosibles) {
            if (software.isEstado()) {
                System.out.println(indice + ". " + software.getNombre());
                indice++;
            }
        }
        int opcion = GeneralBack.leerOpcionIndices(softwaresPosibles.size());

        servicioTecnico.setTecnico(tecnico);
        servicioTecnico.setSoftware(softwaresPosibles.get(opcion - 1));
        servicioTecnico.setEstado(true);
        return servicioTecnico;
    }


    // ------------- CARGAR NUEVOS SERVICIOS TECNICOS (EXPERTISE)  -----------------------------------
    public static void cargarNuevoServiciosTecnicos(ServicioTecnico servicioTecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(servicioTecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    // ------------- BAJA SERVICIO TECNICO (ESTADO == false) -------------------------------------------------

    public static void bajaServicioTecnico(ServicioTecnico servicioTecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        servicioTecnico.setEstado(false);
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(servicioTecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    // ------------- LISTAR TECNICOS POR CONOCMIENTO -------------------------------------------------

    public static List<Tecnico> tecnicosPorConocimiento(ServicioCliente servicioCliente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tecnico> tecnicos;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT t FROM Tecnico t JOIN t.serviciosTecnicos st WHERE st.software = :software";
            tecnicos = entityManager.createQuery(jpql, Tecnico.class).setParameter("software", servicioCliente.getSoftware()).getResultList();
        } finally {
            entityManager.close();
        }
        return tecnicos;
    }
    // ------------- BAJA TECNICO (ESTADO == false) -------------------------------------------------

    public static void bajaTecnico(Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        tecnico.setEstado(false);
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
    // ------------- CARGAR DATOS ACTUALIZADOS DEL TECNICO ------------------------------------------

    public static void actualizarDatosPersonalesTecnico(Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
    // ------------- ELIMINAR TECNICO --------------------------------------------------------------

    public static void eliminarTecnico(Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Tecnico tecnicoEliminar = entityManager.find(Tecnico.class, tecnico.getIdTecnico());
            entityManager.remove(tecnicoEliminar);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
    // ------------- OBTENER SERVICIOS TECNICOS DE UN TECNICO ---------------------------------------

    public static List<ServicioTecnico> obtenerServiciosTecnicos(Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ServicioTecnico> servicioTecnico;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT s FROM ServicioTecnico s WHERE s.tecnico.idTecnico = :idTecnico";
            servicioTecnico = entityManager.createQuery(jpql, ServicioTecnico.class)
                    .setParameter("idTecnico", tecnico.getIdTecnico())
                    .getResultList();
        } finally {
            entityManager.close();
        }
        return servicioTecnico;
    }

    // ------------- CREAR TECNICO NUEVO ----------------------------------------------------------

    public static Tecnico crearTecnicoNuevo() {
        System.out.println("RRHH: Nuevo Tecnico.");
        Tecnico tecnico = new Tecnico();
        tecnico.setNombre(GeneralBack.obtenerNombreOApellido("Nombre"));
        tecnico.setApellido(GeneralBack.obtenerNombreOApellido("Apellido"));
        tecnico.setDni(GeneralBack.controlLargoDNI("DNI"));
        tecnico.setEstado(true);
        return tecnico;
    }
    // ------------- INGRESO A RRHH ----------------------------------------------------------
    public static void ingresoIndiceRRHH(){
        int opcion, volverOmaximo;
        do{
            volverOmaximo = RRHHManagerFront.mostrarTablaRRHH();
            opcion = GeneralBack.leerOpcionIndices(volverOmaximo);
            accionesIndiceRRHH(opcion);
        }while(opcion != volverOmaximo);
    }
    public static void accionesIndiceRRHH(int opcion){
        switch (opcion) {
            case 1:  ingresoArmarYCargarServicioTecnico(RRHHManagerBack.cargarTecnico(crearTecnicoNuevo()));
                break;
            case 2: ingresoIndiceTecnicoAsocaido(ingresoBusquedaTecnicoPorParametro());
                break;
            case 3:
                MenuPrincipal.menuPrincipal();
                break;
            case 4:
                System.exit(0);
                break;
        }
    }

    // ------------- BUSCAR TECNICOS POR PARAMETRO  -----------------------------------------------

    public static Tecnico ingresoBusquedaTecnicoPorParametro(){
        Tecnico tecnico;
        int opcion, volverOmaximo;
        do{
            volverOmaximo = RRHHManagerFront.mostrarTablaBuscarTecnicoParametros();
            opcion = GeneralBack.leerOpcionIndices(volverOmaximo);
            tecnico =  opcionesIndicesBuscarTecnicoParametros(opcion);
        }while (opcion != volverOmaximo);
        return tecnico;
    }

    public static Tecnico opcionesIndicesBuscarTecnicoParametros(int opcion) {
        Tecnico tecnico;
        String consulta = null;
        String parametro = null;
        int valorInt = 0;
        String valorString = null;

        switch (opcion) {
            case 1:
                consulta = "SELECT t FROM Tecnico t WHERE nombre = :nombre";
                parametro = "nombre";
                valorString = GeneralBack.obtenerNombreOApellido("Nombre: ");
                break;
            case 2:
                consulta = "SELECT t FROM Apellido t WHERE apellido = :apellido";
                parametro = "apellido";
                valorString = GeneralBack.obtenerNombreOApellido("Apellido");
                break;
            case 3:
                consulta = "SELECT t FROM Tecnico t WHERE dni = :dni";
                parametro = "dni";
                valorInt = GeneralBack.controlLargoDNI("DNI");
                break;
            case 4:
                RRHHManagerBack.ingresoIndiceRRHH();
                break;
        }
        tecnico = RRHHManagerBack.buscarTecnicoParametros(consulta, parametro, valorInt, valorString);
        return tecnico;
    }

    public static Tecnico buscarTecnicoParametros(String consulta, String parametro, int valorInt, String valorString) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Tecnico tecnico;
        try {
            entityManager.getTransaction().begin();
            if (valorString == null && valorInt != 0) {
                tecnico = entityManager.createQuery(consulta, Tecnico.class).setParameter(parametro, valorInt).getSingleResult();
            } else {
                tecnico = entityManager.createQuery(consulta, Tecnico.class).setParameter(parametro, valorString).getSingleResult();
            }
        } catch (NoResultException cliente_null) {
            return null;
        } finally {
            entityManager.close();
        }
        return tecnico;
    }

    // ------------- INGRESO A TECNICO ASOCIADO RRHH ----------------------------------------
    public static void ingresoIndiceTecnicoAsocaido(Tecnico tecnico){
        int opcion, volverOmaximo;
        do{
            volverOmaximo = RRHHManagerFront.mostrarTablaTecnicoAsociado();
            opcion = GeneralBack.leerOpcionIndices(volverOmaximo);
            accionesTablaTecnicoAsociado(tecnico, opcion);
        }while(opcion != volverOmaximo);
    }
    public static void accionesTablaTecnicoAsociado(Tecnico tecnico, int opcion){
        switch (opcion) {
            case 1:
                ingresoModificarDatosTecnico(tecnico);
                break;
            case 2:
                RRHHManagerBack.bajaTecnico(tecnico);
                break;
            case 3:
                ingresoArmarYCargarServicioTecnico(tecnico);
                break;
            case 4:
                inicioBajaServicioTecnico(tecnico);
                break;
            case 5:
                RRHHManagerBack.eliminarTecnico(tecnico);
                break;
            case 6:
                RRHHManagerBack.ingresoIndiceRRHH();
                break;
        }
    }



    // ------------- BUSCAR TECNICOS POR PARAMETRO  -----------------------------------------------

    public static void ingresoModificarDatosTecnico(Tecnico tecnico){
        int opcion, volverOmaximo;
        do {
        volverOmaximo = RRHHManagerFront.mostrarTablaModificarDatosTecnico();
        opcion = GeneralBack.leerOpcionIndices(volverOmaximo);
        opcionesModificarDatosTecnicos(opcion, tecnico);
        } while (opcion != volverOmaximo);
    }

    public static void opcionesModificarDatosTecnicos(int opcion, Tecnico tecnico) {
            switch (opcion) {
                case 1:
                    tecnico.setNombre(GeneralBack.obtenerNombreOApellido("Nombre"));
                    break;
                case 2:
                    tecnico.setNombre(GeneralBack.obtenerNombreOApellido("Apellido"));
                    break;
                case 3:
                    tecnico.setDni(GeneralBack.controlLargoDNI("DNI"));
                    break;
                case 4:
                    tecnico.setEstado((GeneralBack.cambiarEstadoTecnico()));
                    break;
                case 5:
                    ingresoIndiceTecnicoAsocaido(tecnico);
                    break;
            }
        actualizarDatosPersonalesTecnico(tecnico);
    }

    // ------------- DAR BAJA SERVICIO TECNICOS  -----------------------------------------------

    public static void inicioBajaServicioTecnico(Tecnico tecnico){
        do{
            bajaServicioTecnico(buscarServicioTecnicoParaBaja(tecnico));
        } while (MetodosControl.otro("Dar de baja otro servico?"));
    }

    public static ServicioTecnico buscarServicioTecnicoParaBaja(Tecnico tecnico){
       int indice = 1;
        List<Software> softwaresDeUnTecnico = softwareDeUnTecnico(tecnico);
        System.out.println("Servicios: ");
        for (Software software : softwaresDeUnTecnico) {
            if (software.isEstado()) {
                System.out.println(indice + ". " + software.getNombre());
                indice++;
            }
        }
        int opcion = GeneralBack.leerOpcionIndices(softwaresDeUnTecnico.size());
        return buscarServicioParaDarBaja(softwaresDeUnTecnico.get(opcion), tecnico);
    }
    public static List<Software> softwareDeUnTecnico(Tecnico tecnico){
        List<Software> listaDeSoftwaresTecnico = RRHHManagerBack.obtenerServiciosTecnicos(tecnico)
                .stream()
                .map(ServicioTecnico::getSoftware)
                .collect(Collectors.toList());
        return listaDeSoftwaresTecnico;
    }

    public static ServicioTecnico buscarServicioParaDarBaja(Software software, Tecnico tecnico){
        return obtenerServiciosTecnicos(tecnico).stream()
                .filter(servicio -> servicio.getSoftware().getNombre().equals(software))
                .findFirst()
                .orElse(null);
    }
}


