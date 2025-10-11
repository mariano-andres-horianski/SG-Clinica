package clinica.model.decorators;

import clinica.model.IMedico;
/**
 * Decorator que le asigna contratación permanente al médico
 * <p>Aumenta sus honorarios en 10%</p>
 */
public class DecoratorContratacionPermanente extends DecoratorContratacion 
{
	/**
	 * Añade el tipo de contratación permanente a un médico
	 * @param encapsulado El médico a decorar con contratación permanente
	 */
	public DecoratorContratacionPermanente(IMedico encapsulado) 
	{
		super(encapsulado);
	}
	
	public double getHonorario() 
	{
		return this.encapsulado.getHonorario() * 1.1;
	}
}
