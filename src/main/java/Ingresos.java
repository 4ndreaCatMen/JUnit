public class Ingresos {
    private String concepto;
    private double cantidad;

    public Ingresos(String concepto, double cantidad) {
        this.concepto = concepto;
        this.cantidad = cantidad;
    }

    public boolean realizarIngreso(Usuarios usuario) {
        if (cantidad > 0) {
            usuario.actualizarSaldo(cantidad);
            return true;
        }
        return false;
    }

    public String getConcepto() {
        return concepto;
    }

    public double getCantidad() {
        return cantidad;
    }
}
