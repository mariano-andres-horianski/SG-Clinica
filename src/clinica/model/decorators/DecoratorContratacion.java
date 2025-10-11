package clinica.model.decorators;

import clinica.model.IMedico;
/**
 * Decorator que añade la contratación del médico
 */
public abstract class DecoratorContratacion extends DecoratorMedico 
{
	/**
	 * Añade el tipo de contratación a un médico
	 * @param encapsulado El médico al que añadirle contratacion
	 */
	public DecoratorContratacion(IMedico encapsulado) 
	{
		super(encapsulado);
	}
}
