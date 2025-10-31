package clinica.habitaciones;
import clinica.d.dispatch.*;
	/**
	 * Representa la sala de espera de la clinica 
	 * Informa sobre la ocupacion de la sala y guarda la referencia del paciente que actualmente la ocupa
	 * 
	 */
public class SalaEspera {
	/**Booleano indicando si est� o no ocupada la habitaci�n*/
	private boolean ocupacion;
	private IPrioridad paciente;
	
	public SalaEspera() {
		this.ocupacion = false;
		this.paciente = null;
	}
	
	/**
	 * Ocupa la sala con el paciente indicado
	 * <b>pre: </b>el paciente enviado como argumento est� registrado<br>
	 * @param paciente El paciente que ocupar� la sala
	 */
	public void ocuparSala(IPrioridad paciente) {
		this.paciente = paciente;
		this.ocupacion = true;
	}
	
	public void desocupar() {
		this.paciente = null;
		this.ocupacion = false;
	}
	
	public IPrioridad getPaciente() {
		return paciente;
	}
	
	public boolean isOcupacion() {
		return ocupacion;
	}
	
}
