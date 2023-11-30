package Managers;

import Entidades.Incidente;
import Entidades.Software;
import Entidades.Tecnico;

import java.util.List;

public class IncidenteManagerBack {


        // ------------- ASIGNAR UN TECNICO A UN INCIDENTE --------------------------------

        public static void asignarTecnicoIncidente(Incidente incidente){
            List<Tecnico> tecnicos =  buscarTecnicoPorConocimiento(incidente);

        }

        public static List<Tecnico> buscarTecnicoPorConocimiento(Incidente incidente){
            return RRHHManagerBack.tecnicosPorConocimiento(incidente.getServicioCliente());
        }

    /*asignarTecnicoDisponible()
    marcarComoResuelto(consideraciones);
    notificarClienteResuelto();
    calcularFechaResolucionEstimadaConColchon();*/



}
