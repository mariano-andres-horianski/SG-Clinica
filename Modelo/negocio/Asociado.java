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
            // 0 = atención domicilio
            // 1 = traslado clínica
            
            switch (accion) {
                case 0:
                    System.out.println(getNya() + " -> Pide atención a domicilio"); // temporal
                    clinica.getAmbulancia().solicitarAtencionDomicilio();
                    break;
                case 1:
                    System.out.println(getNya() + " -> Pide traslado a clínica"); // temporal
                    clinica.getAmbulancia().solicitarTrasladoClinica();
                    break;
            }

            try {
                Thread.sleep(2000); // espera entre trámites
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
