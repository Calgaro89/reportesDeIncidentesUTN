package Managers;

import Entidades.Cliente;

public class MesaAyudaFront {
    public static int mostrarOpcionesMesaDeAyuda() {
        System.out.println("-------MESA DE AYUDA--------");
        System.out.println("1. Cliente Asociado");
        System.out.println("2. Cliente Nuevo");
        System.out.println("3. Volver al menu");
        System.out.println("4. Salir");
        return 4;
    }

    public static int mostrarOpciones(Cliente cliente) {
        System.out.println("MESA DE AYUDA: " + cliente.getNombre());
        System.out.println("1 - Consultar Suscripciones");
        System.out.println("2 - Reportar Problemas");
        System.out.println("3 - Consultar Reportes");
        System.out.println("4 - Menu Principal");
        return 4;
    }
}