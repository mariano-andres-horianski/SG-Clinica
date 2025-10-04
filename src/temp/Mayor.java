package temp;

public class Mayor extends Paciente implements IPrioridad {

	public Mayor(String dni, String nya, String ciudad, String telefono, Domicilio domicilio, int nroHC, String rangoEtario) {
		super(dni, nya, ciudad, telefono, domicilio, nroHC, rangoEtario);
	}

	@Override
	public boolean prioridadSala(IPrioridad paciente) {
		return paciente.compararConMayor();
	}

	@Override
	public boolean compararConNino() {
		return true;
	}

	@Override
	public boolean compararConJoven() {
		return false;
	}

	@Override
	public boolean compararConMayor() {
		return false;
	}

}

