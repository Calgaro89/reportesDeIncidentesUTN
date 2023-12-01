package Managers;


public class RRHHManagerFront {

    public static int mostrarTablaRRHH(){
        System.out.println();
        System.out.println("RECURSOS HUMANOS: ");
        System.out.println("1 - Nuevo Técnico");
        System.out.println("2 - Técnico asociado");
        System.out.println("3 - Volver");
        System.out.println("4 - Salir");
        return 4;
    }
    public static int mostrarTablaBuscarTecnicoParametros(){
        System.out.println("RECURSOS HUMANOS: Tecnico asociado. Buscar tecnico por parámetros.");
        System.out.println("Buscar técnico por");
        System.out.println("1 - Nombre");
        System.out.println("2 - Apellido");
        System.out.println("3 - DNI");
        System.out.println("4 - Volver");
        return 4;
    }
    public static int mostrarTablaTecnicoAsociado(){
        System.out.println("RECURSOS HUMANOS: Tecnico asociado.");
        System.out.println("1 - Actualizar Datos Personales Tecnico");
        System.out.println("2 - Baja Tecnico");
        System.out.println("3 - Alta Expertise Tecnico");
        System.out.println("4 - Baja Expertise Tecnico");
        System.out.println("5 - Eliminar Tecnico");
        System.out.println("6 - Volver");
        return 6;
    }



    public static int mostrarTablaModificarDatosTecnico(){
        System.out.println("RECURSOS HUMANOS: Tecnico asociado. Modificar datos de tecnicos.");
        System.out.println("1 - Nombre");
        System.out.println("2 - Apellido");
        System.out.println("3 - dni");
        System.out.println("4 - estado");
        System.out.println("5 - Volver");
        return 5;
    }
}