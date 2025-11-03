package patronState;

import negocio.Ambulancia;

public class AmbulanciaStateRegresandoSP implements IAmbulanciaState {
	private Ambulancia ambulancia;

	public AmbulanciaStateRegresandoSP(Ambulancia ambulancia) {
		this.ambulancia = ambulancia;
	}

	@Override
	public void solicitarAtencionDomicilio() {
		ambulancia.setEstado(new AmbulanciaStateAtendiendo(ambulancia));
	}

	@Override
	public void solicitarTrasladoClinica() {
		ambulancia.setEstado(new AmbulanciaStateTrasladando(ambulancia));
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
		return true;
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
		return "Regresando sin paciente";
	}
}
