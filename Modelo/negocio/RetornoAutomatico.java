package negocio;

import clinica.SingletonClinica;

public class RetornoAutomatico implements Runnable {
    private Ambulancia ambulancia;
    private SingletonClinica clinica;

    public RetornoAutomatico(Ambulancia ambulancia, SingletonClinica clinica) {
        this.ambulancia = ambulancia;
        this.clinica = clinica;
    }

    @Override
    public void run() {
        while(clinica.isSimulacionActiva()) {

            clinica.notificarEvento("RETORNO: Retorno automático solicitado");
            ambulancia.retornoAutomatico();
            
            try {
                Thread.sleep(5000); // cada 5 segundos genera el evento temporal
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
