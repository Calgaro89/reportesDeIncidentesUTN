package Managers;

import java.util.InputMismatchException;

public class MetodosControl {
    public static java.util.Scanner leer = new java.util.Scanner(System.in);

    public static boolean validarEmail(String email) {
        return email.contains("@") && email.contains(".com");
    }

    public static boolean validarFormatoCUIT(String cuit) {
        return cuit.matches("\\d{2}-\\d{8}-\\d");
    }

    public static boolean otro(String text) {
        int opcion = 0;
        do {
            System.out.println(text);
            System.out.println("1. Si");
            System.out.println("2. No");
            System.out.print("Opción: ");
            try {
                opcion = leer.nextInt();
            } catch (NumberFormatException error) {
                otro(text);
            }
        } while (opcion < 1 || opcion > 2);
        return (opcion == 1);
    }

    public static int leerOpcionIndices(int indiceMaximo) {
        int indice = 0;
        while (true) {
            try {
                System.out.print("Indice (1-" + indiceMaximo + "): ");
                indice = leer.nextInt();leer.nextLine();
                if (indice >= 1 && indice <= indiceMaximo) {
                    break;
                } else {
                    System.out.println("Indice fuera de rango. Inténtelo de nuevo.");
                }
            } catch (InputMismatchException errorFormato) {
                System.out.println("Opción no válida. Por favor, elija una opción válida.");
                leer.nextLine();
            }
        }
        return indice;
    }

}
