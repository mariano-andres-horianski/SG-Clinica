
package clinica.ambulancia;

public class AmbulanciaRegresandoDelTallerState implements IAmbulanciaState{
	private Ambulancia ambulancia;

	public AmbulanciaRegresandoDelTallerState(Ambulancia ambulancia) {
		this.ambulancia = ambulancia;
	}

	@Override
	public void atencionADomicilio() {

	}

	@Override
	public void trasladoAClinica() {
		System.out.println("AMBULANCIA OCUPADA: La ambulancia est� regresando del taller");
	}

	@Override
	public void retornoAClinica() {
		this.ambulancia.setEstado(new AmbulanciaDisponibleState(this.ambulancia));
	}

	@Override
	public void mantenimiento() {
		System.out.println("AMBULANCIA OCUPADA: La ambulancia est� regresando del taller");
	}

	}
