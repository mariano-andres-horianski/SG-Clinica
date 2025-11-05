package patronState;

import negocio.Ambulancia;

public class AmbulanciaStateTrasladando implements IAmbulanciaState {
	private Ambulancia ambulancia;

	public AmbulanciaStateTrasladando(Ambulancia ambulancia) {
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
		ambulancia.setEstado(new AmbulanciaStateDisponible(ambulancia));
	}

	@Override
	public void solicitarMantenimiento() {
		
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
		return false;
	}
	
	@Override
	public String toString() {
		return "Trasladando paciente";
	}
}
