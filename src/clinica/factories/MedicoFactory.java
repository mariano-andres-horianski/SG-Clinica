package clinica.factories;

import clinica.exceptions.*;
import clinica.model.*;
import clinica.model.decorators.*;
/**
 * Fábrica responsable de crear instancias de IMedico según
 * los parámetros proporcionados.
 * 
 * Esta clase aplica el patrón <b>Factory Method</b> para encapsular la
 * lógica de creación de médicos con distintas combinaciones de:
 * <ul>
 *   <li>Especialidad (clínico, cirujano, pediatra)</li>
 *   <li>Tipo de contratación (residente, permanente)</li>
 *   <li>Posgrado (magíster, doctorado)</li>
 * </ul>
 * <p>
 * Puede aplicar decoradores que modifican el comportamiento
 * del médico, como sus honorarios, en función de su tipo de contratación
 * o formación académica.
 * </p>
 */
public class MedicoFactory 
{

	/**
     * Crea un médico base (sin posgrado ni contratación especial) según la especialidad indicada.
     *
     * @param dni DNI del médico
     * @param nya nombre y apellido del médico
     * @param ciudad ciudad de residencia
     * @param telefono número de teléfono
     * @param calle calle del domicilio
     * @param altura altura del domicilio
     * @param nroMat número de matrícula profesional
     * @param especialidad tipo de especialidad (clinico, pediatra o cirujano)
     * @return una instancia de {@link IMedico} con la especialidad indicada
     * @throws EspecialidadNotFoundException si la especialidad no es reconocida
     */
	public IMedico crearMedico(String dni, String nya, String ciudad, String telefono, String calle, int altura,
			int nroMat, String especialidad) throws EspecialidadNotFoundException 
	{

		Domicilio d = new Domicilio(calle, altura);

		Especialidad e = null;

		if (especialidad.equalsIgnoreCase("cirujano"))
			e = new MCirujano();
		else if (especialidad.equalsIgnoreCase("clinico"))
			e = new MClinico();
		else if (especialidad.equalsIgnoreCase("pediatra"))
			e = new MPediatra();
		else
			throw new EspecialidadNotFoundException("Especialidad no encotrada");

		return new Medico(dni, nya, ciudad, telefono, d, nroMat, e);
	}

	 /**
     * Crea un médico con tipo de contratación, aplicando el decorator correspondiente.
     *
     * @param dni DNI del médico
     * @param nya nombre y apellido del médico
     * @param ciudad ciudad de residencia
     * @param telefono número de teléfono
     * @param calle calle del domicilio
     * @param altura altura del domicilio
     * @param nroMat número de matrícula profesional
     * @param especialidad tipo de especialidad (clinico, pediatra o cirujano)
     * @param contratacion tipo de contratación (residente o permanente)
     * @return una instancia decorada de {@link IMedico} con contratación aplicada
     * @throws EspecialidadNotFoundException si la especialidad no es reconocida
     * @throws ContratacionNotFoundException si la contratación no es reconocida
     */
	public IMedico crearMedico(String dni, String nya, String ciudad, String telefono, String calle, int altura,
			int nroMat, String especialidad, String contratacion)
			throws EspecialidadNotFoundException, ContratacionNotFoundException 
	{

		IMedico medico = crearMedico(dni, nya, ciudad, telefono, calle, altura, nroMat, especialidad);

		// contrataciÃ³n
		if (contratacion.equalsIgnoreCase("residente")) 
		{
			medico = new DecoratorContratacionResidente(medico);
		} 
		else if (contratacion.equalsIgnoreCase("permanente")) 
		{
			medico = new DecoratorContratacionPermanente(medico);
		} else
			throw new ContratacionNotFoundException("ContrataciÃ³n no encontrada");

		return medico;
	}

	/**
     * Crea un médico con especialidad, tipo de contratación y posgrado,
     * aplicando los decorators correspondientes.
     *
     * @param dni DNI del médico
     * @param nya nombre y apellido del médico
     * @param ciudad ciudad de residencia
     * @param telefono número de teléfono
     * @param calle calle del domicilio
     * @param altura altura del domicilio
     * @param nroMat número de matrícula profesional
     * @param especialidad tipo de especialidad (clinico, pediatra o cirujano)
     * @param contratacion tipo de contratación (residente o permanente)
     * @param posgrado tipo de posgrado (magister o doctorado)
     * @return una instancia decorada de {@link IMedico} con contratación y posgrado aplicados
     * @throws EspecialidadNotFoundException si la especialidad no es reconocida
     * @throws ContratacionNotFoundException si la contratación no es reconocida
     * @throws PosgradoNotFoundException si el posgrado no es reconocido
     */
	public IMedico crearMedico(String dni, String nya, String ciudad, String telefono, String calle, int altura,
			int nroMat, String especialidad, String contratacion, String posgrado)
			throws EspecialidadNotFoundException, ContratacionNotFoundException, PosgradoNotFoundException 
	{

		IMedico medico = crearMedico(dni, nya, ciudad, telefono, calle, altura, nroMat, especialidad, contratacion);

		// posgrado
		if (posgrado.equalsIgnoreCase("magister")) {
			medico = new DecoratorPosgradoMagister(medico);
		} 
		else if (posgrado.equalsIgnoreCase("doctorado")) 
		{
			medico = new DecoratorPosgradoDoctorado(medico);
		} 
		else
			throw new PosgradoNotFoundException("Posgrado no encontrado");

		return medico;
	}
}