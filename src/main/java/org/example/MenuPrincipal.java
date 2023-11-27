package org.example;
import Entidades.Software;
import Managers.*;

import java.util.Scanner;

public class MenuPrincipal {
    private static Scanner leer = new Scanner(System.in);
    public static void main( String[] args ) {
     menuPrincipal();
    }
    public static void menuPrincipal(){
        int opcion;
        do{
            System.out.println();
            System.out.println("------Menu Ingreso-------");
            System.out.println("1. Mesa de Ayuda");
            System.out.println("2. Area Comercial");
            System.out.println("3. Recursos Humanos");
            System.out.println("4. Agregar Software");
            System.out.println("5. Salir");

            System.out.print("Opción: ");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1: MesaDeAyuda.ingresoMesaDeAyuda(); break;
                case 2: AreaComercialFront.areaComercial();break;
                case 3: RRHHManagerFront.recursosHumanos(); break;
                case 4: agregarNuevoSoftware(); break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion < 1 || opcion > 5);
    }

    public static void agregarNuevoSoftware(){
        do {
            Software software = Scanners.armarNuevoSoftware();
            InternoBack.cargarSoftware(software);
        } while (Scanners.otro("¿Agregar otro software?"));
    }
}
