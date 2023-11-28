package org.example;
import Entidades.Software;
import Managers.*;

import java.util.*;
import java.util.Scanner;

public class MenuPrincipal {
    private static Scanner leer = new Scanner(System.in);
    public static void main( String[] args ) {
     menuPrincipal();
    }
    public static void menuPrincipal(){
        try {
            int opcion = 0;
            do {
                System.out.println();
                System.out.println("------Menu Principal-------");
                System.out.println("1. Mesa de Ayuda");
                System.out.println("2. Area Comercial");
                System.out.println("3. Recursos Humanos");
                System.out.println("4. Agregar Software");
                System.out.println("5. Salir");
                System.out.print("Opción: ");
                opcion = leer.nextInt();

                switch (opcion) {
                    case 1:
                        MesaDeAyuda.ingresoMesaDeAyuda();
                        break;
                    case 2:
                        AreaComercialBack.areaComercial();
                        break;
                    case 3:
                        RRHHManagerFront.recursosHumanos();
                        break;
                    case 4:
                        InternoBack.armarNuevoSoftware();
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida. Por favor, elija una opción válida.");
                }
            } while (opcion < 1 || opcion > 5);
        } catch (InputMismatchException errorFormato){
            System.out.println("Opción no válida. Por favor, elija una opción válida.");
            leer.nextLine();
        }
        leer.nextLine();
    }


}
