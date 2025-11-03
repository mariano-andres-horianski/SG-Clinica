package patronState;

import negocio.Ambulancia;

public class AmbulanciaStateAtendiendo implements IAmbulanciaState {
	private Ambulancia ambulancia;

	public AmbulanciaStateAtendiendo(Ambulancia ambulancia) {
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
		ambulancia.setEstado(new AmbulanciaStateRegresandoSP(ambulancia));
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
		return "Atendiendo a domicilio";
	}
}
