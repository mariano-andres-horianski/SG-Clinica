package clinica.ambulancia;

public class AmbulanciaAtendiendoADomicilioState implements AmbulanciaState{
	private Ambulancia ambulancia;

	public AmbulanciaAtendiendoADomicilioState(Ambulancia ambulancia) {
		this.ambulancia = ambulancia;
	}

	@Override
	public void atencionADomicilio() {
		
	}

	@Override
	public void trasladoAClinica() {
		System.out.println("AMBULANCIA OCUPADA: La ambulancia está atendiendo a domicilio");
	}

	@Override
	public void retornoAClinica() {
		this.ambulancia.setEstado(new AmbulanciaRegresandoSinPacienteState(this.ambulancia));
	}

	@Override
	public void mantenimiento() {
		System.out.println("AMBULANCIA OCUPADA: La ambulancia está atendiendo a domicilio");
	}

}