package negocio;

import java.util.Random;

import clinica.SingletonClinica;
import clinica.model.Domicilio;
import clinica.model.Persona;

public class Asociado extends Persona implements Runnable {
	private SingletonClinica clinica;
	private Random random = new Random();

	public Asociado(String dni, String nya, String ciudad, String telefono, Domicilio domicilio) {
		super(dni, nya, ciudad, telefono, domicilio);
		this.clinica = SingletonClinica.getInstance();
	}

	@Override
    public void run() {

        while (clinica.isSimulacionActiva()) {
        	

            int accion = random.nextInt(2); 
            // 0 = atencion domicilio
            // 1 = traslado clinica
            
            switch (accion) {
                case 0:
                    clinica.notificarEvento(getNya() + " pidió atención a domicilio");
                    clinica.getAmbulancia().solicitarAtencionDomicilio(getNya());
                    break;
                case 1:
                    clinica.notificarEvento(getNya() + " pidió traslado a la clínica");
                    clinica.getAmbulancia().solicitarTrasladoClinica(getNya());
                    break;
            }

            try {
                Thread.sleep(5000 + random.nextInt(2000)); // espera entre trámites
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

	@Override
	public String toString() {
		return this.getNya() + " (DNI: " + this.getDni() + ")";
	}
	
	

}
