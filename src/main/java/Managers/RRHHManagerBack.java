package Managers;

import Entidades.ServicioTecnico;
import Entidades.Software;
import Entidades.Tecnico;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

public class RRHHManagerBack {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");
    public static java.util.Scanner leer = new java.util.Scanner(System.in);

    public static Tecnico cargarTecnico() {
        Tecnico tecnico = crearTecnicoNuevo();
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

    public static void armarServicioTecnico(Tecnico tecnico) {
        do {
            ServicioTecnico servicioTecnico;
            List<Software> softwaresSinExperiencia = InternoBack.softwareDisponiblesAgregarTecnicos(tecnico);
            if (!(softwaresSinExperiencia.isEmpty())) {
                servicioTecnico = nuevosServicioTenicos(tecnico, softwaresSinExperiencia);
                RRHHManagerBack.cargarNuevoServiciosTecnicos(servicioTecnico);
            } else {
                System.out.println("No hay softwares para agregar a la expertise del técnico");
                RRHHManagerFront.recursosHumanos();
            }
        } while (MetodosControl.otro("¿Desea agregar otra expertise?"));
    }

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

    public static List<Tecnico> tecnicosPorConocimiento(String conocimiento) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tecnico> tecnicos;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT t FROM Tecnico t WHERE t.conocimiento = :conocimiento";
            tecnicos = entityManager.createQuery(jpql, Tecnico.class).setParameter("conocimiento", conocimiento).getResultList();
        } finally {
            entityManager.close();
        }
        return tecnicos;
    }

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

    public static void actualizarDatosPersonalesTecnico(Tecnico tecnico) {
        Tecnico tecnicoEditado = modificarDatosTecnicos(tecnico);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tecnicoEditado);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

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

    public static Tecnico crearTecnicoNuevo() {
        Tecnico tecnico = new Tecnico();
        System.out.println("Ingrese nombre");
        tecnico.setNombre(leer.next());
        System.out.println("Ingrese apellido");
        tecnico.setApellido(leer.next());
        System.out.println("Ingrese DNI");
        tecnico.setDni(leer.nextInt());
        tecnico.setEstado(true);
        return tecnico;
    }

    public static ServicioTecnico nuevosServicioTenicos(Tecnico tecnico, List<Software> softwaresPosibles) {
        int indice = 1;
        ServicioTecnico servicioTecnico = new ServicioTecnico();
        System.out.println("Servicios: ");
        for (Software software : softwaresPosibles) {
            if (software.isEstado()) {
                System.out.println(indice + ". " + software.getNombre());
                indice++;
            }
        }
        int opcion = 1;
        System.out.print("Indice software a agregar: ");
        try {
            opcion = leer.nextInt();
        } catch (NumberFormatException error) {
            nuevosServicioTenicos(tecnico, softwaresPosibles);
        }
        servicioTecnico.setTecnico(tecnico);
        servicioTecnico.setSoftware(softwaresPosibles.get(opcion - 1));
        servicioTecnico.setEstado(true);
        return servicioTecnico;
    }

    public static Tecnico buscarTecnicoParametros() {
        int opcion = 0;
        Tecnico tecnico;
        do {
            System.out.println();
            System.out.println("-------RRHH--------");
            System.out.println("Buscar técnico por");
            System.out.println("1 - Nombre");
            System.out.println("2 - Apellido");
            System.out.println("3 - DNI");
            System.out.println("4 - Volver al menu");
            try {
                System.out.print("Opcion: ");
                opcion = leer.nextInt();
            } catch (NumberFormatException error) {
                buscarTecnicoParametros();
            }
            String consulta = null;
            String parametro = null;
            int valorInt = 0;
            String valorString = null;

            switch (opcion) {
                case 1:
                    consulta = "SELECT t FROM Tecnico t WHERE nombre = :nombre";
                    parametro = "nombre";
                    System.out.print("Nombre: ");
                    valorString = leer.next();
                    break;
                case 2:
                    consulta = "SELECT t FROM Apellido t WHERE apellido = :apellido";
                    parametro = "apellido";
                    System.out.print("apellido: ");
                    valorString = leer.next();
                    break;
                case 3:
                    consulta = "SELECT t FROM Tecnico t WHERE dni = :dni";
                    parametro = "dni";
                    System.out.print("DNI: ");
                    valorInt = leer.nextInt();
                    break;
                case 4:
                    RRHHManagerFront.recursosHumanos();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
            tecnico = RRHHManagerBack.buscarTecnicoParametros(consulta, parametro, valorInt, valorString);
        } while (opcion == 6);
        return tecnico;
    }

    public static Tecnico modificarDatosTecnicos(Tecnico tecnico) {
        int opcion = 0;
        do {
            System.out.println();
            System.out.println("------- RRHH --------");
            System.out.println("Modificar: ");
            System.out.println("1 - Nombre");
            System.out.println("2 - Apellido");
            System.out.println("3 - dni");
            System.out.println("4 - estado");
            System.out.println("5 - Agregar Servicios");
            System.out.println("6 - Quitar Servicios");
            System.out.println("7 - Volver al menu");
            try {
                System.out.print("Opcion: ");
                opcion = leer.nextInt();
            } catch (NumberFormatException error) {
                leer.next();
                continue;
            }
            switch (opcion) {
                case 1:
                    System.out.print("Nuevo nombre: ");
                    tecnico.setNombre(leer.next());
                    break;
                case 2:
                    System.out.print("Nuevo apellido: ");
                    tecnico.setApellido(leer.next());
                    break;
                case 3:
                    System.out.print("Nuevo dni: ");
                    try {
                        tecnico.setDni(leer.nextInt());
                    } catch (NumberFormatException error) {
                        System.out.println("Formato celular incorrecto");
                        leer.next();
                        continue;
                    }
                    break;
                case 4:
                    System.out.println("Estado: " + ((tecnico.isEstado()) ? "activo" : "inactivo"));
                    int opcion1 = 0;
                    do {
                        System.out.println("Nuevo Estado: ");
                        System.out.println("1- Activo");
                        System.out.println("2- Inactivo");
                        try {
                            opcion1 = leer.nextInt();
                        } catch (NumberFormatException error) {
                            System.out.println("Formato incorrecto. Por favor ingrese un número válido");
                            leer.next();
                            continue;
                        }
                    } while (opcion1 < 1 || opcion1 > 2);
                    tecnico.setEstado((opcion1 == 1));
                    break;
                case 5:
                    RRHHManagerBack.armarServicioTecnico(tecnico);
                    break;
                //case 6: bajaServicios(cliente); break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
                    modificarDatosTecnicos(tecnico);
            }
        } while (MetodosControl.otro("Modificar otro parámetro del técnico"));
        return tecnico;
    }
}


