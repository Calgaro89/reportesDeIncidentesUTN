package Managers;

import Entidades.ServicioTecnico;
import Entidades.Software;
import Entidades.Tecnico;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;


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

    public static void cargarExperticeTecnico(Tecnico nuevoTecnico){
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
        } return tecnicos;
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static void eliminarTecnico(int idTecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Tecnico tecnico = entityManager.find(Tecnico.class, idTecnico);
            entityManager.remove(tecnico);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static List<ServicioTecnico> obtenerServiciosTecnicos(Tecnico tecnico){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ServicioTecnico> servicioTecnico;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT s FROM ServicioTecnico s WHERE s.tecnico.idTecnico = :idTecnico";
            servicioTecnico = entityManager.createQuery(jpql, ServicioTecnico.class)
                    .setParameter("idTecnico",tecnico.getIdTecnico())
                    .getResultList();
        } finally {
            entityManager.close();
        }
        return servicioTecnico;
    }

    public static void agregarServiciosTenicos(ServicioTecnico servicioTecnico){
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
        ServicioTecnico servicioTecnico = new ServicioTecnico();
        do {
            List<ServicioTecnico> servicioTecnicos = RRHHManagerBack.obtenerServiciosTecnicos(tecnico);
            List<Software> softwaresPosibles = new ArrayList<>();
            for (Software softwares : softwareList)
                if (!(servicioTecnicos.contains(softwares))) {
                    softwaresPosibles.add(softwares);
                }
             servicioTecnico = Scanners.nuevosServicioTenicos(tecnico, softwaresPosibles);
            RRHHManagerBack.agregarServiciosTenicos(servicioTecnico);
        } while (Scanners.otro("¿Desea agregar otra expertise?"));
    }
}


