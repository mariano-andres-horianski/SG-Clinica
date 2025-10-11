package clinica.model.decorators;

import clinica.model.IMedico;
/**
 * Decorator que añade le asigna contratación de tipo residente al médico
 * <p>Aumenta sus honorarios en 5%</p>
 */
public class DecoratorContratacionResidente extends DecoratorContratacion 
{
	/**
	 * Añade el tipo de contratación residente a un médico
	 * @param encapsulado El médico a decorar con contratación residente
	 */
	public DecoratorContratacionResidente(IMedico encapsulado) 
	{
		super(encapsulado);
	}
	
	public double getHonorario() 
	{
		return this.encapsulado.getHonorario() * 1.05;
	}
}
