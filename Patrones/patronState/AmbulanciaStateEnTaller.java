package patronState;

import negocio.Ambulancia;

public class AmbulanciaStateEnTaller implements IAmbulanciaState {
	private Ambulancia ambulancia;

	public AmbulanciaStateEnTaller(Ambulancia ambulancia) {
		this.ambulancia = ambulancia;
	}

	@Override
	public void solicitarAtencionDomicilio() {

	}

	@Override
	public void solicitarTrasladoClinica() {
		
	}

	@Override
	public void retornoAutomatico() {

	}

	@Override
	public void solicitarMantenimiento() {
		ambulancia.setEstado(new AmbulanciaStateRegresandoT(ambulancia));
	}
	
	@Override
	public boolean puedeAtencionDomicilio() {
		return true;
	}

	@Override
	public boolean puedeTrasladoClinica() {
		return false;
	}

	@Override
	public boolean puedeRetornoAutomatico() {
		return true;
	}

	@Override
	public boolean puedeMantenimiento() {
		return true;
	}

	@Override
	public String toString() {
		return "En taller";
	}
}
