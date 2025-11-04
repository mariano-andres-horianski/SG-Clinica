package clinica.ambulancia;

public class AmbulanciaDisponibleState implements AmbulanciaState{
	private Ambulancia ambulancia;

	public AmbulanciaDisponibleState(Ambulancia ambulancia) {
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
		
	}

	@Override
	public void mantenimiento() {
		this.ambulancia.setEstado(new AmbulanciaEnTallerState(this.ambulancia));
	}

}
