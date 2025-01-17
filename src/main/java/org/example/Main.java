package org.example;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de BasedeDatos
        BasedeDatos gestor = new BasedeDatos();
        Scanner scanner = new Scanner(System.in);

        try {
            // Conectar a la base de datos
            gestor.conectar();
            System.out.println("Introduce tu DNI:");
            String dni = scanner.nextLine();

            // Verificar si el usuario existe en la base de datos
            if (gestor.autenticarUsuarios(dni)) {
                // Crear un usuario con datos simulados
                Usuarios usuario = new Usuarios(dni, "NombreEjemplo", 1000.0);
                boolean salir = false;

                while (!salir) {
                    System.out.println("¿Qué deseas realizar? (1) Gasto (2) Ingreso (3) Salir");
                    int opcion = scanner.nextInt();

                    switch (opcion) {
                        case 1:
                            System.out.println("Introduce el concepto del gasto:");
                            String conceptoGasto = scanner.next();
                            System.out.println("Introduce la cantidad del gasto:");
                            double cantidadGasto = scanner.nextDouble();

                            Gastos gasto = new Gastos(conceptoGasto, cantidadGasto);
                            if (gasto.realizarGastos(usuario)) {
                                gestor.guardarTransaccion(dni, "Gasto", conceptoGasto, cantidadGasto);
                                System.out.println("Gasto registrado. Saldo actual: " + usuario.getSaldo());
                            } else {
                                System.out.println("Cantidad inválida o saldo insuficiente.");
                            }
                            break;

                        case 2:
                            System.out.println("Introduce el concepto del ingreso:");
                            String conceptoIngreso = scanner.next();
                            System.out.println("Introduce la cantidad del ingreso:");
                            double cantidadIngreso = scanner.nextDouble();

                            Ingresos ingreso = new Ingresos(conceptoIngreso, cantidadIngreso);
                            if (ingreso.realizarIngresos(usuario)) {
                                gestor.guardarTransaccion(dni, "Ingreso", conceptoIngreso, cantidadIngreso);
                                System.out.println("Ingreso registrado. Saldo actual: " + usuario.getSaldo());
                            } else {
                                System.out.println("Cantidad inválida.");
                            }
                            break;

                        case 3:
                            salir = true;
                            break;

                        default:
                            System.out.println("Opción inválida.");
                            break;
                    }
                }
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        } finally {
            try {
                gestor.cerrarConexion();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
