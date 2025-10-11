package clinica.d.dispatch;
import clinica.model.*;

public class Nino extends Paciente implements IPrioridad{
	/**
	 * Crea un Paciente de rango etario Niño con sus datos de Paciente
	 * @param dni Documento Nacional de Identidad
	 * @param nya Nombre y Apellido
	 * @param ciudad Ciudad de residencia
	 * @param telefono Teléfono de contacto
	 * @param domicilio Domicilio de residencia con dirección y altura
	 * @param nroHC Número de historia clínica
	 * @param rangoEtario Rango etario del paciente
	 */
	public Nino(String dni, String nya, String ciudad, String telefono, Domicilio domicilio, int nroHC, String rangoEtario) 
	{
		super(dni,nya,ciudad,telefono,domicilio,nroHC,rangoEtario);
	}

	@Override
	public boolean prioridadSala(IPrioridad paciente) 
	{
		return paciente.compararConNino();
	}

	@Override
	public boolean compararConNino() 
	{
		return false;
	}

	@Override
	public boolean compararConJoven() 
	{
		return true;
	}

	@Override
	public boolean compararConMayor() 
	{
		return false;
	}
	
}

