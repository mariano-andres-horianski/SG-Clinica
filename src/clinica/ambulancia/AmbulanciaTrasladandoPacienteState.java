package clinica.ambulancia;

public class AmbulanciaTrasladandoPacienteState implements IAmbulanciaState{
	private Ambulancia ambulancia;

	public AmbulanciaTrasladandoPacienteState(Ambulancia ambulancia) {
		this.ambulancia = ambulancia;
	}

	@Override
	public void atencionADomicilio() {
		
	}

	@Override
	public void trasladoAClinica() {
		System.out.println("AMBULANCIA OCUPADA: La ambulancia est� trasladando un paciente");
	}

	@Override
	public void retornoAClinica() {
		this.ambulancia.setEstado(new AmbulanciaDisponibleState(this.ambulancia));
	}

	@Override
	public void mantenimiento() {
		System.out.println("AMBULANCIA OCUPADA: La ambulancia est� trasladando un paciente");
	}

	}