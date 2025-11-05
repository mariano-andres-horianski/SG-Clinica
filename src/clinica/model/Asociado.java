package clinica.model;

import java.util.Random;

import clinica.SingletonClinica;

public class Asociado extends Persona implements Runnable {
	private int solicitudes;
	private Random random = new Random();

	public Asociado(String dni, String nya, String ciudad, String telefono, Domicilio domicilio, int solicitudes) {
		super(dni, nya, ciudad, telefono, domicilio);
		this.solicitudes = solicitudes;
	}

	public int getSolicitudes() {
		return solicitudes;
	}

	@Override
    public void run() {
        SingletonClinica clinica = SingletonClinica.getInstance();

        for (int i = 0; i < solicitudes; i++) {

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
