package clinica.model.decorators;

import clinica.model.IMedico;
/**
 * Decorator que añade le da un magister a un médico
 * <p>Añade 5% al honorario del médico.</p>
 */
public class DecoratorPosgradoMagister extends DecoratorPosgrado 
{
	/**
	 * Le añade un posgrado de tipo magister a un médico
	 * @param encapsulado El médico al que añadirle magister
	 */
	public DecoratorPosgradoMagister(IMedico encapsulado) 
	{
		super(encapsulado);
	}
	
	public double getHonorario() 
	{
		return this.encapsulado.getHonorario() * 1.05;
	}
}
