package Managers;

import Entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IncidenteManagerBack {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_PU");

    // ------------- ASIGNAR UN TECNICO A UN INCIDENTE ---------------------------------------
    public static void asignarTecnicoIncidente(Incidente incidente) {
        List<Tecnico> tecnicos = RRHHManagerBack.tecnicosPorConocimiento(incidente.getServicioCliente());
        Software softwareIncidente = incidente.getServicioCliente().getSoftware();
        Map<Tecnico, Double> tiempoPromedioPorTecnico = obtenerListaPromediosResolucionTecnicos(tecnicos, softwareIncidente);

        Map.Entry<Tecnico, Double> mejorTecnicoEntry = encontrarMejorTecnicoPosibleDesocupado(tiempoPromedioPorTecnico, softwareIncidente);
        Tecnico tecnico;
        Double tiempoPromedio;
        if (mejorTecnicoEntry != null) {
            tecnico = mejorTecnicoEntry.getKey();
            tiempoPromedio = mejorTecnicoEntry.getValue();

            incidente.setTecnico(tecnico);
            incidente.setFechaEstimadaFin(calculoFechaEstimadaFin(tiempoPromedio));
        } else {
            Map.Entry<Tecnico, Double> mejorTecnicoEntryOcupado = encontrarMejorTecnicoOcupado(tiempoPromedioPorTecnico);
            tecnico = mejorTecnicoEntryOcupado.getKey();
            tiempoPromedio = mejorTecnicoEntryOcupado.getValue();

            incidente.setTecnico(tecnico);
            incidente.setFechaEstimadaFin(calculoFechaEstimadaFin(tiempoPromedio));
        }
        actualizarIncidente(incidente);
    }

    public static List<Incidente> obtenerTodosIncidentesPorTenico(Software softwareIncidente, Tecnico tecnico) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Incidente> incidentes;
        try {
            entityManager.getTransaction().begin();
            String jpql = "SELECT i FROM Incidente i WHERE i.tecnico = :tecnico AND i.servicioCliente.servicioTecnico.software = :software";
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

    public static Map<Tecnico, Double> obtenerListaPromediosResolucionTecnicos(List<Tecnico> tecnicos, Software softwareIncidente) {
        Map<Tecnico, Double> tiempoPromedioPorTecnico = new HashMap<>();
        Map<Tecnico, Double> finalTiempoPromedioPorTecnico = tiempoPromedioPorTecnico;

        tecnicos.forEach(tecnico ->
                finalTiempoPromedioPorTecnico.put(tecnico, calcularPromedioTiempoResolucionTecnico(obtenerTodosIncidentesPorTenico(softwareIncidente, tecnico))));

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
        for (Map.Entry<Tecnico, Double> entry : tiempoPromedioPorTecnico.entrySet()) {
            List<Incidente> incidentesDelTecnico = obtenerTodosIncidentesNoResueltos(softwareIncidente, entry.getKey());
            if (incidentesDelTecnico.isEmpty()) {
                return entry;
            }
        }
        return null;
    }

    public static List<Incidente> obtenerTodosIncidentesNoResueltos(Software softwareIncidente, Tecnico tecnico) {
        return obtenerTodosIncidentesPorTenico(softwareIncidente, tecnico)
                .stream()
                .filter(incidente -> !incidente.isEstado())
                .collect(Collectors.toList());
    }


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
}





