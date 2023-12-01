package Managers;

import org.example.MenuPrincipal;

import java.util.InputMismatchException;
import java.util.function.Consumer;

public class MetodosControl {

    public static boolean otro(String text) {
        int opcion = 0;
        do {
            System.out.println(text);
            System.out.println("1. Si");
            System.out.println("2. No");
            System.out.print("Opción: ");
            opcion = GeneralBack.controlOpcionIndices(2);
            if (opcion==2){ MenuPrincipal.inicioPrograma();}
        } while (opcion != 1);
        return (true);
    }


}
