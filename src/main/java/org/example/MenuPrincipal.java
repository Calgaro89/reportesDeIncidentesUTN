package org.example;
import Managers.AreaComercialBack;
import Managers.AreaComercialFront;
import Managers.MesaDeAyuda;
import Managers.RRHHManager;

import java.util.Scanner;

public class MenuPrincipal {
    private static Scanner leer = new Scanner(System.in);
    public static void main( String[] args ) {
     menuPrincipal();
    }
    public static void menuPrincipal(){
        int opcion;
        do{
            System.out.println("Menu de ingreso");
            System.out.println("1. Mesa de Ayuda");
            System.out.println("2. Area Comercial");
            System.out.println("3. Recursos Humanos");
            System.out.println("4. Salir");

            System.out.print("Opción: ");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1: Managers.LimpiarPantalla.limpiarPantalla();MesaDeAyuda.ingresoMesaDeAyuda(); break;
                case 2: Managers.LimpiarPantalla.limpiarPantalla();AreaComercialFront.areaComercial();break;
                case 3: Managers.LimpiarPantalla.limpiarPantalla();RRHHManager.recursosHumanos(); break;
                case 4: Managers.LimpiarPantalla.limpiarPantalla(); break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion < 1 || opcion > 4);
    }
}
