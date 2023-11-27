package org.example;
import Managers.AreaComercial;
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
            System.out.println("Menu Principal");
            System.out.println("1 - Mesa de Ayuda");
            System.out.println("2 - Area Comercial");
            System.out.println("3 - Recursos Humanos");
            System.out.println("4 - Salir");


            opcion = leer.nextInt();

            switch (opcion) {
                case 1: MesaDeAyuda.mesaDeAyuda(); break;
                case 2: AreaComercial.areaComercial();break;
                case 3: RRHHManager.recursosHumanos(); break;
                case 4: MenuPrincipal.clearScreen(); break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion < 1 || opcion > 4);


    }


    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
