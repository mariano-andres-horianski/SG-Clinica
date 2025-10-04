package temp;

public class Nino extends Paciente implements IPrioridad{

	public Nino(String dni, String nya, String ciudad, String telefono, Domicilio domicilio, int nroHC, String rangoEtario) {
		super(dni,nya,ciudad,telefono,domicilio,nroHC,rangoEtario);
	}

	@Override
	public boolean prioridadSala(IPrioridad paciente) {
		return paciente.compararConNino();
	}

	@Override
	public boolean compararConNino() {
		return false;
	}

	@Override
	public boolean compararConJoven() {
		return true;
	}

	@Override
	public boolean compararConMayor() {
		return false;
	}
	
}

