package Managers;

import Entidades.Cliente;
import Entidades.ServicioTecnico;
import Entidades.Software;
import Entidades.Tecnico;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RRHHManagerBack {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");

    public static Tecnico cargarTecnico() {
        Tecnico tecnico = Scanners.crearTecnicoNuevo();
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

    public static void cargarExperticeTecnico(Tecnico nuevoTecnico) {
        InternoBack.listarSoftware();
        obtenerServiciosTecnicos(nuevoTecnico);

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
        }
        return tecnicos;
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
        Tecnico tecnicoEditado = Scanners.modificarDatosTecnicos(tecnico);
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

    public static void agregarServiciosTecnicos(ServicioTecnico servicioTecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(servicioTecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static void agregarExpertiseTecnico(Tecnico tecnico) {
        List<Software> softwareList = InternoBack.listarSoftware();
        do {
            ServicioTecnico servicioTecnico;
            List<Software> listaDeSoftwaresTecnico = RRHHManagerBack.obtenerServiciosTecnicos(tecnico)
                    .stream()
                    .map(ServicioTecnico::getSoftware)
                    .collect(Collectors.toList());

            List<Software> softwaresSinExperiencia = softwareList.stream()
                    .filter(software -> !listaDeSoftwaresTecnico.contains(software))
                    .distinct()
                    .collect(Collectors.toList());
            if (!(softwaresSinExperiencia.isEmpty())) {
                servicioTecnico = Scanners.nuevosServicioTenicos(tecnico, softwaresSinExperiencia);
                RRHHManagerBack.agregarServiciosTecnicos(servicioTecnico);
            } else {
                System.out.println("No hay softwares para agregar a la expertise del técnico");
                RRHHManagerFront.recursosHumanos();
            }
        } while (Scanners.otro("¿Desea agregar otra expertise?"));
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
}


