package Managers;

import Entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IncidenteManagerBack {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");

    // ------------- ACTUALIZAR EL ESTADO DE UN INCIDENTE ---------------------------------------

    public static void actualizarIncidente(Incidente incidente) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(incidente);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    // ------------- ASIGNAR UN TECNICO A UN INCIDENTE ---------------------------------------
    public static void asignarTecnicoIncidente(Incidente incidente) {
        List<Tecnico> tecnicos = RRHHManagerBack.tecnicosPorConocimiento(incidente.getServicioCliente().getSoftware());
        Map<Tecnico, Double> tiempoPromedioPorTecnico = obtenerListaOrdenadaPromediosResolucionTecnicos(tecnicos, incidente.getServicioCliente().getSoftware());

        Map.Entry<Tecnico, Double> mejorTecnicoEntry = encontrarMejorTecnicoPosibleDesocupado(tiempoPromedioPorTecnico, incidente.getServicioCliente().getSoftware());
        Tecnico tecnico;
        Double tiempoPromedio;
        if (mejorTecnicoEntry != null) {
            tecnico = mejorTecnicoEntry.getKey();
            tiempoPromedio = mejorTecnicoEntry.getValue();
            incidente.setTecnico(tecnico);
            incidente.setFechaEstimadaFin(calculoFechaEstimadaFin(tiempoPromedio));
        } else {
            Map.Entry<Tecnico, Double> mejorTecnicoEntryOcupado = encontrarMejorTecnicoOcupado(tiempoPromedioPorTecnico);
            if (mejorTecnicoEntryOcupado != null){
            tecnico = mejorTecnicoEntryOcupado.getKey();
            tiempoPromedio = mejorTecnicoEntryOcupado.getValue();
            incidente.setTecnico(tecnico);
            incidente.setFechaEstimadaFin(calculoFechaEstimadaFin(tiempoPromedio));
            } else {
                tecnico = RRHHManagerBack.listarTodosLosTecnicos().get(0);
                incidente.setTecnico(tecnico);
                incidente.setFechaEstimadaFin(calculoFechaEstimadaFin((10080.0)));
            }
        }
        actualizarIncidente(incidente);
    }

    // ------------- BUSCA PARA CADA TECNICO LOS INCIDENTES RESUELTOS Y NO RESUELTOS --------------------------------

    public static List<Incidente> obtenerTodosIncidentesPorTecnico(Software softwareIncidente, Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Incidente> incidentes;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT i FROM Incidente i WHERE i.tecnico = :tecnico AND i.servicioCliente.software = :software";
            incidentes = entityManager.createQuery(jpql, Incidente.class)
                    .setParameter("tecnico", tecnico)
                    .setParameter("software", softwareIncidente)
                    .getResultList();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return incidentes;
    }

    public static double calcularPromedioTiempoResolucionTecnico(List<Incidente> incidentes) {
        return incidentes.stream()
                .filter(Incidente::isEstado)
                .mapToDouble(IncidenteManagerBack::calcularTiempoTranscurrido)
                .average()
                .orElse(0.0);
    }

    public static double calcularTiempoTranscurrido(Incidente incidente) {
        return Duration.between(incidente.getFechaIngreso(), incidente.getFechaRealFin()).toHours();
    }

    public static Map<Tecnico, Double> obtenerListaOrdenadaPromediosResolucionTecnicos(List<Tecnico> tecnicos, Software softwareIncidente) {
        Map<Tecnico, Double> tiempoPromedioPorTecnico = new HashMap<>();
        Map<Tecnico, Double> finalTiempoPromedioPorTecnico = tiempoPromedioPorTecnico;

        tecnicos.forEach(tecnico ->
                finalTiempoPromedioPorTecnico.put(tecnico, calcularPromedioTiempoResolucionTecnico(obtenerTodosIncidentesPorTecnico(softwareIncidente, tecnico))));

        tiempoPromedioPorTecnico = tiempoPromedioPorTecnico.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        return tiempoPromedioPorTecnico;
    }

    public static Map.Entry<Tecnico, Double> encontrarMejorTecnicoPosibleDesocupado(Map<Tecnico, Double> tiempoPromedioPorTecnico, Software softwareIncidente) {
        return tiempoPromedioPorTecnico.entrySet().stream()
                .filter(entry -> obtenerTodosIncidentesNoResueltos(softwareIncidente, entry.getKey()).isEmpty())
                .findFirst()
                .orElse(null);
    }

    public static List<Incidente> obtenerTodosIncidentesNoResueltos(Software softwareIncidente, Tecnico tecnico) {
        return obtenerTodosIncidentesPorTecnico(softwareIncidente, tecnico)
                .stream()
                .filter(incidente -> !incidente.isEstado())
                .collect(Collectors.toList());
    }

    public static LocalDateTime calculoFechaEstimadaFin(Double tiempoPromedio) {
        return LocalDateTime.now().plusMinutes(Math.round(tiempoPromedio * 1.2));
    }

    private static Map.Entry<Tecnico, Double> encontrarMejorTecnicoOcupado(Map<Tecnico, Double> tiempoPromedioPorTecnico) {
        return tiempoPromedioPorTecnico.entrySet()
                .stream()
                .findFirst()
                .orElse(null);
    }

    // ------------- MARCAR INCIDENTE COMO RESUELTO ---------------------------------------

    public static void marcarIncidenteComoResuelto(Incidente incidente) {
        incidente.setEstado(true);
        actualizarIncidente(incidente);
        if (incidente.getTipoComunicacion().equals("email")) {
            SendMail.enviarCorreo(incidente.getServicioCliente().getCliente(), incidente.getTecnico().getNombre());
        } else {
            System.out.println("enviar whatsapp al cliente");
        }
    }

    public static void listarTecnicosPorCantidadIncidentesResueltos(LocalDate fechaIncioBusqueda, LocalDate fechaFinBusqueda) {
        Map<Tecnico, Long> incidentesResueltosPorTecnico = new HashMap<>();
        List<Incidente> incidentesResueltos = obtenerTodosLosIncidentesResueltos(fechaIncioBusqueda, fechaFinBusqueda);
        List<Tecnico> tecnicos = listarTodosLosTecnicosEmpresa();

        incidentesResueltosPorTecnico = incidentesResueltos.stream()
                .filter(incidente -> tecnicos.contains(incidente.getTecnico()))
                .collect(Collectors.groupingBy(Incidente::getTecnico, Collectors.counting()));

        incidentesResueltosPorTecnico.forEach((tecnico, cantidadIncidentesResueltos) ->
                System.out.println(tecnico.getNombre() + ": " + cantidadIncidentesResueltos + " incidentes resueltos"));
    }

    public static List<Incidente> obtenerTodosLosIncidentesResueltos(LocalDate fechaIncioBusqueda, LocalDate fechaFinBusqueda) {
        LocalDateTime fechaInicio = fechaIncioBusqueda.atStartOfDay();
        LocalDateTime fechaFin = fechaFinBusqueda.atStartOfDay().plusDays(1);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Incidente> incidentes;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT i FROM Incidente i WHERE estado = :estado AND i.fechaRealFin BETWEEN :fechaInicio AND :fechaFin";
            incidentes = entityManager.createQuery(jpql, Incidente.class)
                    .setParameter("estado", true)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return incidentes;
    }

    public static List<Tecnico> listarTodosLosTecnicosEmpresa() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tecnico> tecnicos;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT i FROM Tecnico i";
            tecnicos = entityManager.createQuery(jpql, Tecnico.class)
                    .getResultList();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return tecnicos;
    }

    public static void mejorTecnicoDeUnaEspecialidad(Software software) {
            List<Tecnico> tecnicos = RRHHManagerBack.tecnicosPorConocimiento(software);
            Map<Tecnico, Double> listaOrdenadaTecnicosPorEspecialidad = obtenerListaOrdenadaPromediosResolucionTecnicos(tecnicos, software);
            Map.Entry<Tecnico, Double> primerEntry = listaOrdenadaTecnicosPorEspecialidad.entrySet().iterator().next();
            Tecnico mejorTecnico = primerEntry.getKey();
            Double promedioResolucion = primerEntry.getValue();

            System.out.println("Mejor técnico de la especialidad " + software.getNombre() + ":");
            System.out.println("Técnico: " + mejorTecnico.getNombre());
            System.out.println("Promedio de resolución: " + promedioResolucion);
        }



    public static void seleccionarSoftware(){
        int maximo, opcion;
        do{
            maximo = RRHHManagerFront.mostrarSoftwaresDisponibles(GeneralBack.listarSoftware());
            opcion = GeneralBack.controlOpcionIndices(maximo);
            mejorTecnicoDeUnaEspecialidad(GeneralBack.listarSoftware().get(opcion - 1));
        } while (opcion == maximo + 1);
    }
}





