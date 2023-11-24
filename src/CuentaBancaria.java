import java.security.InvalidParameterException;

public class CuentaBancaria {
    protected float saldo;
    protected int numeroDeConsignaciones;
    protected int numeroDeRetiros;
    protected float tasaAnual; // Porcentaje
    protected float comisionMensual;

    public CuentaBancaria(float saldo, float tasaAnual) {
        this.saldo = saldo;
        this.numeroDeConsignaciones = 0;
        this.numeroDeRetiros = 0;
        this.tasaAnual = tasaAnual;
        this.comisionMensual = 0;
    }

    public float getSaldo() {
        return saldo;
    }
    public int getNumeroDeConsignaciones() {
        return numeroDeConsignaciones;
    }

    public int getNumeroDeRetiros() {
        return numeroDeRetiros;
    }

    public float getTasaAnual() {
        return tasaAnual;
    }

    public float getComisionMensual() {
        return comisionMensual;
    }

    public float consignarDinero(float cantidad) {
        if (cantidad < 0.f)
            throw new InvalidParameterException("La cantidad tiene que ser positiva");

        return agregarDinero(cantidad);
    }

    public float retirarDinero(float cantidad) {
        if (cantidad < 0.f)
            throw new InvalidParameterException("La cantidad tiene que ser positiva");

        if (cantidad > saldo)
            throw new ArithmeticException("La cantidad para retirar es mayor que el saldo");

        return removerDinero(cantidad);
    }

    public float calcularInteresMensual() {
        return saldo * tasaAnual / 100.f;
    }

    public float extractoMensual() {
        saldo += calcularInteresMensual();
        saldo -= comisionMensual;
        return saldo;
    }

    public void imprimir() {
        System.out.println("Saldo previo: " + convertirEnDinero(saldo));
        System.out.println("Numero de consignaciones: " + numeroDeConsignaciones);
        System.out.println("Numero de retiros: " + numeroDeRetiros);
        System.out.println("Tasa anual: " + tasaAnual + "%");
        System.out.println("Interes: " + convertirEnDinero(calcularInteresMensual()));
        System.out.println("Comision mensual: " + convertirEnDinero(comisionMensual));
        System.out.println("--------------------");
        System.out.println("Saldo nuevo: " + convertirEnDinero(extractoMensual()));
    }

    public static String convertirEnDinero(float dinero) {
        int cantidad = (int) (dinero * 100.f);
        if (cantidad % 100 == 0)
            return "$ " + (cantidad / 100) + '.' + "00";
        else if (cantidad % 100 < 10)
            return "$ " + (cantidad / 100) + '.' + "0" + (cantidad % 100);
        else
            return "$ " + (cantidad / 100) + '.' + (cantidad % 100);
    }

    protected float agregarDinero(float cantidad) {
        numeroDeConsignaciones++;
        return saldo += cantidad;
    }

    protected float removerDinero(float cantidad) {
        numeroDeRetiros++;
        return saldo -= cantidad;
    }
}
