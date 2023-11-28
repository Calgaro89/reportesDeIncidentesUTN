package Managers;

import Entidades.*;

import java.util.InputMismatchException;
import java.util.List;

public class Scanners {
    public static java.util.Scanner leer = new java.util.Scanner(System.in);

    public static Cliente crearCliente() {
        System.out.println("-------AREA COMERCIAL--------");
        System.out.print("Nombre: ");
        String nombre = leer.next();

        System.out.println("Cuit: ");
        String cuit = leer.next();

        while (!MesaDeAyuda.validarFormatoCUIT(cuit)) {
            System.out.println("Formato de CUIT incorrecto. Asegúrate de seguir el formato xx-xxxxxxxx-x");
            System.out.print("Cuit: ");
            cuit = leer.next();
        }

        System.out.print("Celular: ");
        long celular = 0;
        try {
            celular = leer.nextLong();
        } catch (NumberFormatException error) {
            System.out.println("Celular Incorrecto");
            leer.nextLine();
        }
        System.out.print("E-Mail: ");
        String mail = leer.next();

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setCuit(cuit);
        cliente.setCelular(celular);
        cliente.setMail(mail);
        return cliente;
    }

    public static Cliente buscarClientesParametros() {
        int opcion = 0;
        Cliente cliente;
        do {
            System.out.println();
            System.out.println("-------AREA COMERCIAL--------");
            System.out.println("Buscar cliente por");
            System.out.println("1 - Nombre");
            System.out.println("2 - CUIT");
            System.out.println("3 - iD");
            System.out.println("4 - E-Mial");
            System.out.println("5 - Celular");
            System.out.println("6 - Volver al menu");
            try {
                System.out.print("Opcion: ");
                opcion = leer.nextInt();
            } catch (NumberFormatException error) {
                buscarClientesParametros();
            }
            String consulta = null;
            String parametro = null;
            long valorLong = 0;
            int valorInt = 0;
            String valorString = null;

            switch (opcion) {
                case 1:
                    consulta = "SELECT t FROM Cliente t WHERE nombre = :nombre";
                    parametro = "nombre";
                    System.out.print("Nombre: ");
                    valorString = leer.next();
                    break;
                case 2:
                    consulta = "SELECT t FROM Cliente t WHERE cuit = :cuit";
                    parametro = "cuit";
                    System.out.print("CUIT: ");
                    valorString = leer.next();
                    break;
                case 3:
                    consulta = "SELECT t FROM Cliente t WHERE idCliente = :idCliente";
                    parametro = "idCliente";
                    System.out.print("idCliente: ");
                    valorInt = leer.nextInt();
                    break;
                case 4:
                    consulta = "SELECT t FROM Cliente t WHERE mail = :mail";
                    parametro = "mail";
                    System.out.print("email: ");
                    valorString = leer.next();
                    break;
                case 5:
                    consulta = "SELECT t FROM Cliente t WHERE celular = :celular";
                    parametro = "celular";
                    System.out.print("celular: ");
                    valorLong = leer.nextLong();
                    break;
                case 6:
                    AreaComercialFront.areaComercial();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
            cliente = AreaComercialBack.buscarClienteParametros(consulta, parametro, valorInt, valorLong, valorString);
        } while (opcion == 6);
        return cliente;
    }

    public static Cliente modificarDatosClientes(Cliente cliente) {
        int opcion = 0;
        System.out.println();
        System.out.println("-------AREA COMERCIAL--------");
        System.out.println("Modificar: ");
        System.out.println("1 - Nombre");
        System.out.println("2 - CUIT");
        System.out.println("3 - e-mial");
        System.out.println("4 - Celular");
        System.out.println("5 - Agregar Servicios");
        System.out.println("6 - Quitar Servicios");
        System.out.println("7 - Volver al menu");
        try {
            System.out.print("Opcion: ");
            opcion = leer.nextInt();
        } catch (NumberFormatException error) {
            modificarDatosClientes(cliente);
        }
        switch (opcion) {
            case 1:
                System.out.print("Nuevo nombre: ");
                cliente.setNombre(leer.next());
                break;
            case 2:
                System.out.print("Nuevo CUIT: ");
                cliente.setCuit(leer.next());
                break;
            case 3:
                System.out.print("Nuevo e-mail: ");
                cliente.setMail(leer.next());
                break;
            case 4:
                System.out.print("Nuevo celular: ");
                try {
                    cliente.setCelular(leer.nextLong());
                } catch (NumberFormatException error) {
                    System.out.println("Formato celular incorrecto");
                    modificarDatosClientes(cliente);
                }
                break;
            case 5:
                agregarServiciosClientes(cliente);
                break;
            //case 6: bajaServicios(cliente); break;
            case 7:
                buscarClientesParametros();
                break;
            default:
                System.out.println("Opción no válida. Por favor, elija una opción válida.");
                modificarDatosClientes(cliente);
        }
        return cliente;
    }

    public static Software armarNuevoSoftware() {
            System.out.println("-----Nuevo Software----");
            System.out.print("Nombre: ");
            String nombre = leer.next();
            Software software = new Software();
            software.setNombre(nombre);
            software.setEstado(true);
        return software;
    }

    public static void agregarServiciosClientes(Cliente cliente) {
        ServicioCliente servicioCliente = new ServicioCliente();
        servicioCliente.setCliente(cliente);
        int indice = 1;
        System.out.println("Servicios: ");
        List<Software> softwareList = InternoBack.listarSoftware();
        for (Software software : softwareList) {
            if (software.isEstado()) {
                System.out.println(indice + ". " + software.getNombre());
                indice++;
            }
        }
        List<ServicioCliente> softwareCliente = AreaComercialBack.obtenerServiciosClientes(cliente);

        int opcion = 0;
        System.out.print("Indice software a agregar: ");
        try {
            opcion = leer.nextInt();
        } catch (NumberFormatException error) {
            agregarServiciosClientes(cliente);
        }
        servicioCliente.setSoftware(softwareList.get(opcion - 1));
        AreaComercialBack.agregarServiciosClientes(servicioCliente);
    }

    public static Tecnico crearTecnicoNuevo() {
        Tecnico tecnico = new Tecnico();
        System.out.println("Ingrese nombre");
        tecnico.setNombre(leer.next());
        System.out.println("Ingrese apellido");
        tecnico.setApellido(leer.next());
        System.out.println("Ingrese DNI");
        tecnico.setDni(leer.nextInt());
        tecnico.setEstado(true);
        return tecnico;
    }

    public static ServicioTecnico nuevosServicioTenicos(Tecnico tecnico, List<Software> softwaresPosibles) {
        int indice = 1;
        ServicioTecnico servicioTecnico = new ServicioTecnico();
        System.out.println("Servicios: ");
        for (Software software : softwaresPosibles) {
            if (software.isEstado()) {
                System.out.println(indice + ". " + software.getNombre());
                indice++;
            }
        }
        int opcion = 1;
        System.out.print("Indice software a agregar: ");
        try {
            opcion = leer.nextInt();
        } catch (NumberFormatException error) {
            nuevosServicioTenicos(tecnico, softwaresPosibles);
        }
        servicioTecnico.setTecnico(tecnico);
        servicioTecnico.setSoftware(softwaresPosibles.get(opcion - 1));
        return servicioTecnico;
    }

    public static boolean otro(String text) {
        int opcion = 0;
        do {
            System.out.println();
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

    public static Tecnico buscarTecnicoParametros() {
        int opcion = 0;
        Tecnico tecnico;
        do {
            System.out.println();
            System.out.println("-------RRHH--------");
            System.out.println("Buscar tecnico por");
            System.out.println("1 - Nombre");
            System.out.println("2 - Apellido");
            System.out.println("3 - DNI");
            System.out.println("4 - Volver al menu");
            try {
                System.out.print("Opcion: ");
                opcion = leer.nextInt();
            } catch (NumberFormatException error) {
                buscarTecnicoParametros();
            }
            String consulta = null;
            String parametro = null;
            int valorInt = 0;
            String valorString = null;

            switch (opcion) {
                case 1:
                    consulta = "SELECT t FROM Tecnico t WHERE nombre = :nombre";
                    parametro = "nombre";
                    System.out.print("Nombre: ");
                    valorString = leer.next();
                    break;
                case 2:
                    consulta = "SELECT t FROM Apellido t WHERE apellido = :apellido";
                    parametro = "apellido";
                    System.out.print("apellido: ");
                    valorString = leer.next();
                    break;
                case 3:
                    consulta = "SELECT t FROM Tecnico t WHERE dni = :dni";
                    parametro = "dni";
                    System.out.print("DNI: ");
                    valorInt = leer.nextInt();
                    break;
                case 4:
                    RRHHManagerFront.recursosHumanos();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
            tecnico = RRHHManagerBack.buscarTecnicoParametros(consulta, parametro, valorInt, valorString);
        } while (opcion == 6);
        return tecnico;
    }

    public static Tecnico modificarDatosTecnicos(Tecnico tecnico) {
        int opcion = 0;
        System.out.println();
        System.out.println("------- RRHH --------");
        System.out.println("Modificar: ");
        System.out.println("1 - Nombre");
        System.out.println("2 - Apellido");
        System.out.println("3 - dni");
        System.out.println("4 - estado");
        System.out.println("5 - Agregar Servicios");
        System.out.println("6 - Quitar Servicios");
        System.out.println("7 - Volver al menu");
        try {
            System.out.print("Opcion: ");
            opcion = leer.nextInt();
        } catch (NumberFormatException error) {
            modificarDatosTecnicos(tecnico);
        }
        switch (opcion) {
            case 1:
                System.out.print("Nuevo nombre: ");
                tecnico.setNombre(leer.next());
                break;
            case 2:
                System.out.print("Nuevo apellido: ");
                tecnico.setApellido(leer.next());
                break;
            case 3:
                System.out.print("Nuevo dni: ");
                try {
                    tecnico.setDni(leer.nextInt());
                } catch (NumberFormatException error) {
                    System.out.println("Formato celular incorrecto");
                    modificarDatosTecnicos(tecnico);
                }
                break;
            case 4:
                System.out.println("Estado: " + ((tecnico.isEstado()) ? "activo" : "inactivo"));
                int opcion1 = 0;
                do {
                    System.out.println("Nuevo Estado: ");
                    System.out.println("1- Activo");
                    System.out.println("2- Inactivo");
                    try {
                        opcion1 = leer.nextInt();
                    } catch (NumberFormatException error) {
                        System.out.println("Formato incorrecto");
                        modificarDatosTecnicos(tecnico);
                    }
                } while (opcion1 < 1 || opcion1 > 2);
                tecnico.setEstado((opcion1 == 1));
                break;
            case 5:
                RRHHManagerBack.agregarExpertiseTecnico(tecnico);
                break;
            //case 6: bajaServicios(cliente); break;
            case 7:
                buscarClientesParametros();
                break;
            default:
                System.out.println("Opción no válida. Por favor, elija una opción válida.");
                modificarDatosTecnicos(tecnico);
        }
        return tecnico;
    }
}
