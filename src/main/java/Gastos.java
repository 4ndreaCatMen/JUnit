public class Gastos {
    private String concepto;
    private double cantidad;

    public Gastos(String concepto, double cantidad) {
        this.concepto = concepto;
        this.cantidad = cantidad;
    }

    public boolean realizarGastos(Usuarios usuario) {
        if (cantidad > 0 && cantidad <= usuario.getSaldo()) {
            usuario.actualizarSaldo(-cantidad);
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
