package negocio;

import patronState.*;

public class Ambulancia {
	private IAmbulanciaState estado;

	public Ambulancia() {
		this.estado = new AmbulanciaStateDisponible(this);
	}

	public synchronized void solicitarAtencionDomicilio() {
		while (!estado.puedeAtencionDomicilio()) {
			informar(estado.toString());
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		estado.solicitarAtencionDomicilio();
		notifyAll();

		// a implementar observer-observable para actualizar la información del estado de la Ambulancia
	}

	public synchronized void solicitarTrasladoClinica() {
		while (!estado.puedeTrasladoClinica()) {
			informar(estado.toString());
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		estado.solicitarTrasladoClinica();
		notifyAll();

		// a implementar observer-observable para actualizar la información del estado de la Ambulancia
	}

	public synchronized void retornoAutomatico() {
		while (!estado.puedeRetornoAutomatico()) {
			informar(estado.toString());
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		estado.retornoAutomatico();
		notifyAll();

		// a implementar observer-observable para actualizar la información del estado de la Ambulancia
	}

	public synchronized void solicitarMantenimiento() {
		while (!estado.puedeMantenimiento()) {
			informar(estado.toString());
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		estado.solicitarMantenimiento();
		notifyAll();

		// a implementar observer-observable para actualizar la información del estado de la Ambulancia
	}

	private void informar(String s) {
		// a implementar, informa por vista la razón
	}

	public IAmbulanciaState getEstado() {
		return estado;
	}

	public void setEstado(IAmbulanciaState estado) {
		this.estado = estado;
	}
}
