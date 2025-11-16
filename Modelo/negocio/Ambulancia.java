package negocio;

import clinica.SingletonClinica;
import patronState.*;

public class Ambulancia {
	private IAmbulanciaState estado;
	private SingletonClinica clinica;

	public Ambulancia(SingletonClinica clinica) {
		this.estado = new AmbulanciaStateDisponible(this);
		this.clinica = clinica;
	}

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

	public synchronized void finalizar() {
	    notifyAll();
	}
	
	private void informar(String s) {
		clinica.notificarAmbulanciaOcupada(s);
		
	}
	
	private void accesoDenegado(String s) {
		String msg = "PEDIDO DENEGADO: " + s;
		clinica.notificarEvento(msg);
	}

	public IAmbulanciaState getEstado() {
		return estado;
	}

	public void setEstado(IAmbulanciaState estado) {
		this.estado = estado;
		clinica.notificarCambioEstadoAmbulancia(estado.toString());
	}
}
