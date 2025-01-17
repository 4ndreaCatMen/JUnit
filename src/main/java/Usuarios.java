public class Usuarios {
        private String dni;
        private String nombre;
        private double saldo;

        public Usuarios(String dni, String nombre, double saldo) {
            this.dni = dni;
            this.nombre = nombre;
            this.saldo = saldo;
        }

        public String getDni() {
            return dni;
        }

        public String getNombre() {
            return nombre;
        }

        public double getSaldo() {
            return saldo;
        }

        public void actualizarSaldo(double cantidad) {
            this.saldo += cantidad;
        }
}


