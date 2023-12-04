package Managers;

import Entidades.*;
import org.example.MenuPrincipal;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

public class RRHHManagerBack {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");

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
            RRHHManagerBack.ingresoIndiceTecnicoAsociado(tecnico);
            return null;
        }
        return nuevosServicioTecnicos(tecnico, softwaresSinExperiencia);
    }

    private static ServicioTecnico nuevosServicioTecnicos(Tecnico tecnico, List<Software> softwaresPosibles) {
        int indice = 1;
        ServicioTecnico servicioTecnico = new ServicioTecnico();
        System.out.println("Servicios: ");
        for (Software software : softwaresPosibles) {
            if (software.isEstado()) {
                System.out.println(indice + ". " + software.getNombre());
                indice++;
            }
        }
        int opcion = GeneralBack.controlOpcionIndices(softwaresPosibles.size());

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

    public static List<Tecnico> tecnicosPorConocimiento(Software software) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tecnico> tecnicos;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT st.tecnico FROM ServicioTecnico st WHERE st.software = :software";
            tecnicos = entityManager.createQuery(jpql, Tecnico.class).setParameter("software", software).getResultList();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return tecnicos;
    }

    // ------------- LISTAR TODOS LOS TECNICOS -------------------------------------------------

    public static List<Tecnico> listarTodosLosTecnicos() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tecnico> tecnicos;
        String consulta = "SELECT t FROM Tecnico t";
        try {
            entityManager.getTransaction().begin();
            tecnicos = entityManager.createQuery(consulta, Tecnico.class).getResultList();
            entityManager.getTransaction().commit();
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
        tecnico.setNombre(GeneralBack.controlFormatoNombreOApellido("Nombre"));
        tecnico.setApellido(GeneralBack.controlFormatoNombreOApellido("Apellido"));
        tecnico.setDni(GeneralBack.controlLargoDNI("DNI"));
        tecnico.setEstado(true);
        return tecnico;
    }
    // ------------- INGRESO A RRHH ----------------------------------------------------------
    public static void ingresoIndiceRRHH(){
        int opcion, volverOmaximo;
        do{
            volverOmaximo = RRHHManagerFront.mostrarTablaRRHH();
            opcion = GeneralBack.controlOpcionIndices(volverOmaximo);
            accionesIndiceRRHH(opcion);
        }while(opcion != volverOmaximo);
    }
    public static void accionesIndiceRRHH(int opcion){
        switch (opcion) {
            case 1:  ingresoArmarYCargarServicioTecnico(RRHHManagerBack.cargarTecnico(crearTecnicoNuevo()));
                break;
            case 2: ingresoIndiceTecnicoAsociado(ingresoBusquedaTecnicoPorParametro());
                break;
            case 3:
                    IncidenteManagerBack.listarTecnicosPorCantidadIncidentesResueltos(Scanners.obtenerLocalDate("Fecha inicio busqueda"),Scanners.obtenerLocalDate("Fecha fin de busqueda"));
                    break;
            case 4: IncidenteManagerBack.seleccionarSoftware();
                    break;
            case 5:
                MenuPrincipal.inicioPrograma();
                break;
            case 6:
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
            opcion = GeneralBack.controlOpcionIndices(volverOmaximo);
            tecnico =  opcionesIndicesBuscarTecnicoParametros(opcion);
        }while (tecnico == null);
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
                    valorString = GeneralBack.controlFormatoNombreOApellido("Nombre");
                    break;
                case 2:
                    consulta = "SELECT t FROM Tecnico t WHERE apellido = :apellido";
                    parametro = "apellido";
                    valorString = GeneralBack.controlFormatoNombreOApellido("Apellido");
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
        if (tecnico == null){
            System.out.println("No se encontro el Tecnico asociado, vuelva a intentarlo");
            ingresoBusquedaTecnicoPorParametro();
        }
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
    public static void ingresoIndiceTecnicoAsociado(Tecnico tecnico){
        int opcion, volverOmaximo;
        do{
            volverOmaximo = RRHHManagerFront.mostrarTablaTecnicoAsociado();
            opcion = GeneralBack.controlOpcionIndices(volverOmaximo);
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
        opcion = GeneralBack.controlOpcionIndices(volverOmaximo);
        opcionesModificarDatosTecnicos(opcion, tecnico);
        } while (opcion != volverOmaximo);
    }

    public static void opcionesModificarDatosTecnicos(int opcion, Tecnico tecnico) {
            switch (opcion) {
                case 1:
                    tecnico.setNombre(GeneralBack.controlFormatoNombreOApellido("Nombre"));
                    break;
                case 2:  tecnico.setNombre(GeneralBack.controlFormatoNombreOApellido("Apellido"));
                    break;
                case 3:
                    tecnico.setDni(GeneralBack.controlLargoDNI("DNI"));
                    break;
                case 4:
                    tecnico.setEstado((cambiarEstadoTecnico()));
                    break;
                case 5:
                    ingresoIndiceTecnicoAsociado(tecnico);
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
        int opcion = GeneralBack.controlOpcionIndices(softwaresDeUnTecnico.size());
        return buscarServicioParaDarBaja(softwaresDeUnTecnico.get(opcion), tecnico);
    }
    public static List<Software> softwareDeUnTecnico(Tecnico tecnico){
        return RRHHManagerBack.obtenerServiciosTecnicos(tecnico)
                .stream()
                .map(ServicioTecnico::getSoftware)
                .collect(Collectors.toList());
    }

    public static ServicioTecnico buscarServicioParaDarBaja(Software software, Tecnico tecnico){
        return obtenerServiciosTecnicos(tecnico).stream()
                .filter(servicio -> servicio.getSoftware().getNombre().equals(software.getNombre()))
                .findFirst()
                .orElse(null);
    }

    // ------------- CAMBIAR ESTADO TECNICO  -----------------------------------------------

    public static boolean cambiarEstadoTecnico() {
        System.out.println("Nuevo Estado: ");
        System.out.print("1- Activo");
        System.out.print("-- 2- Inactivo");
        System.out.print("-- 3- Salir");
        int opcion;
        do{
            opcion = GeneralBack.controlOpcionIndices(3);
        } while(opcion!= 3);
        return (opcion == 1);
    }
}


