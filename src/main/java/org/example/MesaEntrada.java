package org.example;

import java.util.Scanner;

public class MesaEntrada {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        int opcion;
        do{
        System.out.println("1 - Clientes");
        System.out.println("2 - Atención Cliente");
        System.out.println("3 - RRHH");
        System.out.println("4 - Salir");

        opcion = leer.nextInt();

        switch (opcion) {
            case 1: clientes(); break;
            case 2: atencionCliente();break;
            case 3: recursosHumanos(); break;
            case 4: clearScreen(); break;
            default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
        }
    } while (opcion != 4);

}

        public static void clientes(){
            System.out.println("cliente");
        }

        public static void atencionCliente(){

        }

        public static void recursosHumanos(){

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

