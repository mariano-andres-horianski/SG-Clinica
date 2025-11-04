package clinica.ambulancia;

public class AmbulanciaRegresandoSinPacienteState implements AmbulanciaState{
	private Ambulancia ambulancia;

	public AmbulanciaRegresandoSinPacienteState(Ambulancia ambulancia) {
		this.ambulancia = ambulancia;
	}

	@Override
	public void atencionADomicilio() {
		this.ambulancia.setEstado(new AmbulanciaAtendiendoADomicilioState(this.ambulancia));
	}

	@Override
	public void trasladoAClinica() {
		this.ambulancia.setEstado(new AmbulanciaTrasladandoPacienteState(this.ambulancia));
	}

	@Override
	public void retornoAClinica() {
		this.ambulancia.setEstado(new AmbulanciaDisponibleState(this.ambulancia));
	}

	@Override
	public void mantenimiento() {
		System.out.println("AMBULANCIA OCUPADA: La ambulancia está regresando sin paciente");
	}

	}
