package clinica;
import temp.*;
import java.util.ArrayList;

public class SingletonClinica {
	public String nombre, direccion, telefono, ciudad;
	private SingletonClinica instance;
	private SalaEspera salaDeEspera;
	private ArrayList<IPrioridad> patio; 

	private SingletonClinica() {
		ciudad = "Mar del Plata";
		direccion = "Avenida Siempreviva 123";
		telefono = "22300000";
		nombre = "Clínica Colón";
		this.salaDeEspera  = new SalaEspera();
		this.patio = new ArrayList<IPrioridad>();
	}

	public SingletonClinica getInstance() {
		if (instance == null) {
			this.instance = new SingletonClinica();
		}

		return this.instance;
	}
	/**
	 * Este metodo gestiona la ubicacion de los pacientes, determinando si deben ir a la sala de espera o al patio
	 * PreCondicion: el nuevo paciente ya fue registrado en el sistema
	 * @param nuevoPaciente: es el paciente que acaba de ingresar a la clinica
	 */
	public void derivacion(IPrioridad nuevoPaciente) {
		if(!salaDeEspera.isOcupacion())
			this.salaDeEspera.ocuparSala(nuevoPaciente);
		else {
			if (salaDeEspera.getPaciente().prioridadSala(nuevoPaciente) ) {
				patio.add(salaDeEspera.getPaciente());
				this.salaDeEspera.ocuparSala(nuevoPaciente);
			}
			else 
				patio.add(nuevoPaciente);
		}
	}
}
