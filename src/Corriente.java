import java.security.InvalidParameterException;

public class Corriente extends CuentaBancaria {
    float sobregiro;

    public Corriente(float saldo, float tasaAnual) {
        super(saldo, tasaAnual);
        this.sobregiro = 0.f;
    }

    public float getSobregiro() {
        return sobregiro;
    }

    @Override
    public float consignarDinero(float cantidad) {
        if (cantidad < 0.f)
            throw new InvalidParameterException("La cantidad tiene que ser positiva");

        if (sobregiro > 0.f) {
            if (sobregiro > cantidad) {
                sobregiro -= cantidad;
                return 0.f;
            }
            else {
                cantidad -= sobregiro;
                sobregiro = 0.f;
            }
        }

        return agregarDinero(cantidad);
    }

    @Override
    public float retirarDinero(float cantidad) {
        if (cantidad < 0.f)
            throw new InvalidParameterException("La cantidad tiene que ser positiva");

        if (cantidad > saldo) {
            cantidad -= saldo;
            sobregiro = cantidad;
            return removerDinero(saldo);
        }
        else {
            return removerDinero(cantidad);
        }
    }

    @Override
    public void imprimir() {
        System.out.println("Tipo de cuenta: corriente");
        System.out.println("Saldo previo: " + convertirEnDinero(saldo));
        System.out.println("Numero de transacciones: " + (numeroDeConsignaciones + numeroDeRetiros));
        System.out.println("Tasa anual: " + tasaAnual + "%");
        System.out.println("Interes: " + convertirEnDinero(calcularInteresMensual()));
        System.out.println("Comision mensual: " + convertirEnDinero(comisionMensual));
        System.out.println("--------------------");
        System.out.println("Saldo nuevo: " + convertirEnDinero(extractoMensual()));
        System.out.println("Sobregiro: - " + convertirEnDinero(sobregiro));
    }
}
