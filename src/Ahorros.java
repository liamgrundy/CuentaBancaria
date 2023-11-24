import java.security.InvalidParameterException;

public class Ahorros extends CuentaBancaria {
    private boolean isActiva;

    public Ahorros(float saldo, float tasaAnual) {
        super(saldo, tasaAnual);
        isActiva = saldo >= 10000.f;
    }

    public boolean getIsActiva() {
        return isActiva;
    }

    @Override
    public float consignarDinero(float cantidad) {
        if (cantidad < 0.f)
            throw new InvalidParameterException("La cantidad tiene que ser positiva");
        if (!isActiva)
            throw new ArithmeticException("La cuenta no esta activa");

        return agregarDinero(cantidad);
    }

    @Override
    public float retirarDinero(float cantidad) {
        if (cantidad < 0.f)
            throw new InvalidParameterException("La cantidad tiene que ser positiva");

        if (cantidad > saldo)
            throw new ArithmeticException("La cantidad para retirar es mayor que el saldo");
        if (!isActiva)
            throw new ArithmeticException("La cuenta no esta activa");

        return removerDinero(cantidad);
    }

    @Override
    public float extractoMensual() {
        saldo += calcularInteresMensual();
        if (numeroDeRetiros > 0)
            comisionMensual = 1000 * (numeroDeRetiros - 4);

        if (saldo < comisionMensual) // En este caso el examen no describe que hacer
            throw new RuntimeException("No se puede actualizar el saldo porque saldo < comisionMensual");

        saldo -= comisionMensual;
        isActiva = saldo >= 10000.f;
        return saldo;
    }

    @Override
    public void imprimir() {
        System.out.println("Tipo de cuenta: ahorros");
        System.out.println("Saldo previo: " + convertirEnDinero(saldo));
        System.out.println("Numero de transacciones: " + (numeroDeConsignaciones + numeroDeRetiros));
        System.out.println("Tasa anual: " + tasaAnual + "%");
        System.out.println("Interes: " + convertirEnDinero(calcularInteresMensual()));
        System.out.println("Comision mensual: " + convertirEnDinero(comisionMensual));
        System.out.println("--------------------");
        System.out.println("Saldo nuevo: " + convertirEnDinero(extractoMensual()));
        System.out.println("Estado de la cuenta: " + (isActiva ? "activa" : "no es activa"));
    }
}
