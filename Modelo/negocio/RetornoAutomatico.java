package negocio;

public class RetornoAutomatico implements Runnable {
    private Ambulancia ambulancia;

    public RetornoAutomatico(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(5000); // cada 5 segundos genera el evento temporal
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Evento: Retorno automático solicitado por el sistema");
            ambulancia.retornoAutomatico();
        }
    }
}
