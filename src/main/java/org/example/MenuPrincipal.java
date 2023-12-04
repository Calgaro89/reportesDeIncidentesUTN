package org.example;
import Managers.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;
import java.util.Scanner;

public class MenuPrincipal {

    public static void main(String[] args) {
        cargarBaseDatos();
        inicioPrograma();
    }

    public static void cargarBaseDatos(){
        GeneralBack.listarSoftware();
    }
    public static void inicioPrograma() {
        int opcion;
        do {
            opcion = GeneralBack.controlOpcionIndices(mostrarMenuPrincipal());
            opcionesMenuPrincipal(opcion);
        } while (opcion == 5 + 1);
    }


    public static int mostrarMenuPrincipal() {
        System.out.println("------Menu Principal-------");
        System.out.println("1. Mesa de Ayuda");
        System.out.println("2. Area Comercial");
        System.out.println("3. Recursos Humanos");
        System.out.println("4. Agregar Software");
        System.out.println("5. Baja a Software");
        System.out.println("6. Alta Software");
        System.out.println("7. Salir");
        return (7);
    }

    public static void opcionesMenuPrincipal(int opcion) {
        switch (opcion) {
            case 1:
                MesaAyudaBack.ingresoMesaAyuda();
                break;
            case 2:
                AreaComercialBack.ingresoAreaComercial();
                break;
            case 3:
                RRHHManagerBack.ingresoIndiceRRHH();
                break;
            case 4:
                GeneralBack.cargarSoftware();
                break;
            case 5:
                GeneralBack.ingresoBajaSoftware();
                break;
            case 6:
                GeneralBack.ingresoAltaSoftware();
                break;
            case 7:
                System.exit(0);
        }
    }

}