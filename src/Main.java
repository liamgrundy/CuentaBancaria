import javax.swing.*;
import java.security.InvalidParameterException;

public class Main {
    private static CuentaBancaria cuenta;
    public static void main(String[] args) {
        while (true) {
            try {
                switch (Integer.parseInt(JOptionPane.showInputDialog("Elige una opcion:\n" +
                        "[1] Crear una cuenta\n" +
                        "[2] Consignar dinero\n" +
                        "[3] Retirar dinero\n" +
                        "[4] Ver datos\n" +
                        "[5] Cerrar programa"))) {
                    case 1:
                        crearCuenta();
                        break;
                    case 2:
                        consignarDinero();
                        break;
                    case 3:
                        retirarDinero();
                        break;
                    case 4:
                        verDatos();
                        break;
                    case 5:
                        return;
                }
            }
            catch (Exception e) {
                continue;
            }
        }
    }

    private static float getFloat(String parameterName, String message) {
        String resultado = JOptionPane.showInputDialog(message);
        if (resultado == null || resultado.isEmpty())
            throw new IllegalArgumentException("El valor de " + parameterName + " fue entrado incorrectamente");

        return Float.parseFloat(resultado);
    }

    private static void crearCuenta() {
        try {
            switch (Integer.parseInt(JOptionPane.showInputDialog("Elige tipo de cuenta:\n" +
                    "[1] Ahorros\n" +
                    "[2] Corriente"))) {
                case 1:
                    cuenta = new Ahorros(
                            getFloat("saldo", "Entra el saldo"),
                            getFloat("tasa anual", "Entra la tasa anual"));
                    JOptionPane.showMessageDialog(null, "Creado una cuenta de ahorros exitosamente");
                    return;
                case 2:
                    cuenta = new Corriente(
                            getFloat("saldo", "Entra el saldo"),
                            getFloat("tasa anual", "Entra la tasa anual"));
                    JOptionPane.showMessageDialog(null, "Creado una cuenta corriente exitosamente");
                    return;
                default:
                    throw new InvalidParameterException("el usuario no entro una opcion valido");
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "La cuenta no fue creado exitosamente: " +
                    e.getMessage());
        }
    }

    private static void consignarDinero() {
        if (cuenta == null)
            crearCuenta();
        if (cuenta == null)
            return;

        try {
            cuenta.consignarDinero(getFloat("cantidad", "Entra cantidad de dinero para consignar:"));

            JOptionPane.showMessageDialog(null, "Consignado dinero exitosamente");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El dinero no fue consignado exitosamente" +
                    e.getMessage());
        }
    }

    private static void retirarDinero() {
        if (cuenta == null)
            crearCuenta();
        if (cuenta == null)
            return;

        try {
            cuenta.retirarDinero(getFloat("cantidad", "Entra cantidad de dinero para retirar:"));

            JOptionPane.showMessageDialog(null, "Retirado dinero exitosamente");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El dinero no fue retirado exitosamente: " +
                    e.getMessage());
        }
    }

    private static void verDatos() {
        if (cuenta == null)
            crearCuenta();
        if (cuenta == null)
            return;

        if (cuenta.getClass() == Ahorros.class) {
            Ahorros cuentaDeAhorros = (Ahorros) cuenta;
            JOptionPane.showMessageDialog(null,
                    "Tipo de cuenta: ahorros\n" +
                            "Saldo previo: " + CuentaBancaria.convertirEnDinero(cuenta.getSaldo()) + '\n' +
                            "Numero de transacciones: " + (cuenta.getNumeroDeConsignaciones() +
                            cuenta.getNumeroDeRetiros()) + '\n' +
                            "Tasa anual: " + cuenta.getTasaAnual() + "%\n" +
                            "Interes: " + CuentaBancaria.convertirEnDinero(cuenta.calcularInteresMensual()) + '\n' +
                            "Comision mensual: " + CuentaBancaria.convertirEnDinero(cuenta.getComisionMensual()) + '\n' +
                            "--------------------\n" +
                            "Saldo nuevo: " + CuentaBancaria.convertirEnDinero(cuenta.extractoMensual()) + '\n' +
                            "Estado de la cuenta: " + (cuentaDeAhorros.getIsActiva() ? "activa" : "no es activa")
            );
        }
        else {
            Corriente cuentaCorriente = (Corriente) cuenta;
            JOptionPane.showMessageDialog(null,
                    "Tipo de cuenta: corriente\n" +
                            "Saldo previo: " + CuentaBancaria.convertirEnDinero(cuenta.getSaldo()) + '\n' +
                            "Numero de transacciones: " + (cuenta.getNumeroDeConsignaciones() +
                            cuenta.getNumeroDeRetiros()) + '\n' +
                            "Tasa anual: " + cuenta.getTasaAnual() + "%\n" +
                            "Interes: " + CuentaBancaria.convertirEnDinero(cuenta.calcularInteresMensual()) + '\n' +
                            "Comision mensual: " + CuentaBancaria.convertirEnDinero(cuenta.getComisionMensual()) + '\n' +
                            "--------------------\n" +
                            "Saldo nuevo: " + CuentaBancaria.convertirEnDinero(cuenta.extractoMensual()) + '\n' +
                            "Sobregiro: - " + cuentaCorriente.getSobregiro()
            );
        }
    }
}
