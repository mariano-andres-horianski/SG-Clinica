package patronState;

import negocio.*;

public class AmbulanciaStateDisponible implements IAmbulanciaState {
	private Ambulancia ambulancia;

	public AmbulanciaStateDisponible(Ambulancia ambulancia) {
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

	}

	@Override
	public void solicitarMantenimiento() {
		ambulancia.setEstado(new AmbulanciaStateEnTaller(ambulancia));
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
		return true;
	}

	@Override
	public String toString() {
		return "Disponible";
	}
}
