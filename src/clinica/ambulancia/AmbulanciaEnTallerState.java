package clinica.ambulancia;

public class AmbulanciaEnTallerState implements IAmbulanciaState{
	private Ambulancia ambulancia;

	public AmbulanciaEnTallerState(Ambulancia ambulancia) {
		this.ambulancia = ambulancia;
	}

	@Override
	public void atencionADomicilio() {

	}

	@Override
	public void trasladoAClinica() {
		System.out.println("AMBULANCIA OCUPADA: La ambulancia est� en el taller");
	}

	@Override
	public void retornoAClinica() {

	}

	@Override
	public void mantenimiento() {
		this.ambulancia.setEstado(new AmbulanciaRegresandoDelTallerState(this.ambulancia));
	}

	}
