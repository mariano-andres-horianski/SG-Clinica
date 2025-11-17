package negocio;

import clinica.SingletonClinica;
import patronState.*;

/**
 * Representa la Ambulancia de la clínica, actuando como el "Contexto" en el patrón State y como un "Monitor" para la gestión de concurrencia.
 *
 * <p>Esta clase centraliza el acceso al recurso (la ambulancia) mediante métodos sincronizados ({@code synchronized}).
 * Utiliza  wait() y notifyAll() para gestionar las solicitudes concurrentes de los Asociado y el Operario cuando el estado actual
 * IAmbulanciaState no permite una acción.
 */
public class Ambulancia {
	private IAmbulanciaState estado;
	private SingletonClinica clinica;
	/**
	 * Construye una nueva instancia de Ambulancia.
	 * <p><b>Postcondición:</b> El estado inicial de la ambulancia se establece en "Disponible" new AmbulanciaStateDisponible(this).
	 *
	 * @param clinica La instancia única (Singleton) de la clínica, usada para comunicar eventos y verificar el estado de la simulación.
	 */
	public Ambulancia(SingletonClinica clinica) {
		this.estado = new AmbulanciaStateDisponible(this);
		this.clinica = clinica;
		
		assert this.estado != null : "No se inicializó correctamente el estado de la ambulancia";
	}

	/**
	 * Método sincronizado para solicitar atención a domicilio.
	 * Si el estado actual no permite esta acción y la simulación está activa, el hilo solicitante (Asociado) se bloquea hasta que
	 * el estado cambie.
	 * <p><b>Precondición:</b> El hilo actual debe poseer el monitor de este objeto (garantizado por 'synchronized').
	 * <p><b>Postcondición:</b> 
	 * <pre>
	 * Si la simulación se finaliza el hilo se despierta y sale del método sin ejecutar la acción.
	 * Si la acción es permitida, se delega la ejecución al estado y se notifica a todos los hilos en espera.
	 * </pre>
	 * @param s Identificador del solicitante (ej. nombre del asociado) para informar en el log.
	 */
	public synchronized void solicitarAtencionDomicilio(String s) {
		while (!estado.puedeAtencionDomicilio() && clinica.isSimulacionActiva()) {
			accesoDenegado(s + " no puede solicitar atención a domicilio");
			informar(estado.toString());
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (clinica.isSimulacionActiva())
			estado.solicitarAtencionDomicilio();
		notifyAll();
	}

	/**
	 * Método sincronizado para solicitar traslado a la clínica. 
	 * <p><b>Precondición:</b> El hilo actual debe poseer el monitor de este objeto.
	 * <p><b>Postcondición:</b> La solicitud es procesada por el estado actual o el hilo es liberado por finalización de simulación. Se notifica a todos los hilos.
	 * @param s Identificador del solicitante (Asociado). 
	 */
	public synchronized void solicitarTrasladoClinica(String s) {
		while (!estado.puedeTrasladoClinica() && clinica.isSimulacionActiva()) {
			accesoDenegado(s + " no puede solicitar traslado a clínica");
			informar(estado.toString());
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (clinica.isSimulacionActiva())
			estado.solicitarTrasladoClinica();
		notifyAll();
	}

	/**
	 * Método sincronizado para el evento temporal de retorno automático. 
	 *
	 * <p><b>Precondición:</b> El hilo actual debe poseer el monitor de este objeto.
	 * <p><b>Postcondición:</b> La solicitud es procesada por el estado actual o el hilo es liberado por finalización de simulación. Se notifica a todos los hilos.
	 *
	 */
	public synchronized void retornoAutomatico() {
		while (!estado.puedeRetornoAutomatico() && clinica.isSimulacionActiva()) {
			// accesoDenegado(); puede siempre el retorno
			informar(estado.toString());
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (clinica.isSimulacionActiva())
			estado.retornoAutomatico();
		notifyAll();
	}

	/**
	 * Método sincronizado para solicitar mantenimiento (Operario). 
	 *
	 * <p><b>Precondición:</b> El hilo actual debe poseer el monitor de este objeto.
	 * <p><b>Postcondición:</b> La solicitud es procesada por el estado actual o el hilo es liberado por finalización de simulación. Se notifica a todos los hilos.
	 *
	 */
	public synchronized void solicitarMantenimiento() {
		while (!estado.puedeMantenimiento() && clinica.isSimulacionActiva()) {
			accesoDenegado("Operario no pudo solicitar mantenimiento");
			informar(estado.toString());
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (clinica.isSimulacionActiva())
			estado.solicitarMantenimiento();
		notifyAll();
	}

	/**
	 * Finaliza el monitor de la ambulancia.
	 *
	 * <p><b>Precondición:</b> La simulación debe haber sido marcada como inactiva (clinica.isSimulacionActiva() == false).
	 * <p><b>Postcondición:</b> 
	 * <pre>
	 * Todos los hilos en espera (wait()) son despertados. 
	 * Al despertar, evaluarán la condición de sus bucles while, verán que clinica.isSimulacionActiva() es falso, y saldrán de los métodos
	 * sincronizados, permitiendo que los hilos (Asociados, etc.) terminen.
	 * </pre>
	 */
	public synchronized void finalizar() {
	    notifyAll();
	}

	/**
	 * Método auxiliar privado para notificar a la clínica (y a la vista) que la ambulancia está ocupada, indicando su estado actual.
	 *
	 * @param s El estado actual.
	 */
	private void informar(String s) {
		clinica.notificarAmbulanciaOcupada(s);
		
	}

	/**
	 * Método auxiliar privado para notificar a la clínica (y a la vista) que una solicitud fue denegada por el estado actual.
	 *
	 * @param s Mensaje detallado de la denegación.
	 */
	private void accesoDenegado(String s) {
		String msg = "PEDIDO DENEGADO: " + s;
		clinica.notificarEvento(msg);
	}

	/**
	 * Obtiene el objeto de estado actual de la ambulancia.
	 *
	 * @return El objeto que implementa IAmbulanciaState y representa el estado actual.
	 */
	public IAmbulanciaState getEstado() {
		return estado;
	}

	/**
	 * Establece un nuevo estado para la ambulancia.
	 * Este método es invocado por las clases de estado (Patrón State) durante una transición.
	 * También notifica a la clínica (Modelo/Observable) sobre el cambio para que la vista (Observer) pueda actualizarse.
	 * <p><b>Precondición:</b> estado no debe ser nulo.
	 * <p><b>Postcondición:</b>
	 * <pre>
	 * El estado interno de la ambulancia se actualiza.
	 * Se notifica a la clínica (clinica.notificarCambioEstadoAmbulancia()) para que actualice a los observadores.
	 * </pre>
	 * @param estado El nuevo objeto de estado. 
	 */
	public void setEstado(IAmbulanciaState estado) {
		assert estado != null : "El estado no puede ser null";
		this.estado = estado;
		clinica.notificarCambioEstadoAmbulancia(estado.toString());
	}
}
