package clinica.model.decorators;

import clinica.model.IMedico;
/**
 * Decorator que añade el posgrado del médico
 */
public abstract class DecoratorPosgrado extends DecoratorMedico 
{
	/**
	 * Le añade un posgrado a un médico
	 * @param encapsulado El médico al que añadirle posgrado
	 */
	public DecoratorPosgrado(IMedico encapsulado) 
	{
		super(encapsulado);
	}
}
