package org.example;

public class Usuarios {
    private final String dni;

    public Usuarios(String dni) {
        this.dni = dni;
    }

    public double getSaldo() {
        return BaseDatos.getSaldo(dni);
    }

    public void addIngreso(double cantidad) {
        double saldoActual = BaseDatos.getSaldo(dni); // Obtener saldo actual desde la base de datos
        double nuevoSaldo = saldoActual + cantidad; // Sumar el ingreso neto al saldo actual
        BaseDatos.updateSaldo(dni, nuevoSaldo); // Actualizar saldo en la base de datos
    }


    public boolean addGasto(double cantidad) {
        double saldoActual = BaseDatos.getSaldo(dni); // Obtener saldo actual desde la base de datos
        if (cantidad > saldoActual) {
            return false; // No permitir el gasto si el saldo es insuficiente
        }
        double nuevoSaldo = saldoActual - cantidad; // Calcular el nuevo saldo
        BaseDatos.updateSaldo(dni, nuevoSaldo); // Actualizar el saldo en la base de datos
        return true; // Confirmar que el gasto se realizó correctamente
    }

}

