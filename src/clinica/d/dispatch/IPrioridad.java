package clinica.d.dispatch;
	/**
	 * Esta interfaz se encarga de asegurar la implementacion de los metodos para la solucion de prioridades en la sala de espera 
	 * paciente con mayor prioridad segun el siguiente criterio:
	 * 			Entre un NiÃ±o y un Joven, la Sala queda para el NiÃ±o
	 * 			Entre un Joven y un Mayor, la Sala queda para el Joven
	 * 			Entre un Mayor y un NiÃ±o, la Sala queda para el Mayor
	 * 
	 */

public interface IPrioridad
{
	
	/**
	 * Evalua si el paciente pasado por parámetro tiene prioridad por sobre el que está en la sala
	 * <p>El método delegará la evaluación en el argumento implementando así el doble dispatch</p>
	 * <b>pre: </b>el paciente enviado como argumento debe existir<br>
	 * @param paciente el paciente que se compara con este
	 * @return verdadero si este paciente tiene prioridad sobre el enviado como parámetro y falso si no la tiene
	 */
	public boolean prioridadSala(IPrioridad paciente);
	/**
	 * Compara la prioridad de este paciente con la de un niño
	 * @return verdadero si el que compara es un mayor
	 */
	public boolean compararConNino();
	/**
	 * Compara la prioridad de este paciente con la de un joven
	 * @return verdadero si el que compara es un niño
	 */
	public boolean compararConJoven();
	/**
	 * Compara la prioridad de este paciente con la de un niño
	 * @return verdadero si el que compara es un joven
	 */
	public boolean compararConMayor();
}

