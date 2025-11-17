package controlador.Simulacion;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import clinica.*;
import clinica.model.Domicilio;
import controlador.Asociados.ReadListenerAsociados;
import excepciones.AsociadoDuplicadoException;
import negocio.Asociado;
import persistencia.DAOAsociadoYDTO.AsociadoDAOMySQL;
import persistencia.DAOAsociadoYDTO.AsociadoDTO;
import vista.JframePrincipal.VentanaPrincipal;
import vista.PanelCentral.PanelSimulacion;
/**
 * Controlador para el módulo de simulación, siguiendo el patrón MVC.
 *
 * Esta clase actúa como el nexo entre la Vista (VentanaPrincipal y
 * PanelSimulacion) y el Modelo (SingletonClinica).
 *
 * Implementa {@link ActionListener} para reaccionar a los eventos de la GUI (clics en botones).
 * Implementa {@link Observer} para recibir notificaciones del Modelo  y actualizar la Vista en consecuencia.
 *
 */
public class ActionListenerSimulacion implements ActionListener, Observer {

	private VentanaPrincipal ventanaPrincipal;
	private PanelSimulacion panelSimulacion;
	private SingletonClinica clinica;
	private AsociadoDAOMySQL BD;
	/**
	 * Construye el controlador de simulación.
	 * <p>Obtiene la instancia del modelo (SingletonClinica) y se registra a sí mismo como Observador (Observer) para recibir actualizaciones.
	 * </p>
	 * <p>Extrae los socios de la base de datos y los convierte de AsociadoDTO a Asociado (un runnable)
	 * </p>
	 * <p><b>Postcondición:</b> this se añade a la lista de observadores de SingletonClinica.
	 */
	public ActionListenerSimulacion() {
		this.clinica = SingletonClinica.getInstance();
		try {
			this.BD = new AsociadoDAOMySQL();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clinica.addObserver(this);
		HashMap<String, AsociadoDTO> asociados = BD.obtenerTodosMap();
		for(AsociadoDTO a: asociados.values()) {
			Domicilio d = new Domicilio(a.getDomicilioStr(),0);
			Asociado actual = new Asociado(a.getDni(),a.getNya(),a.getCiudad(),a.getTelefono(),d);
			try {
				clinica.registrarAsociado(actual);
			} catch (AsociadoDuplicadoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Establece y configura la vista principal (JFrame) para este controlador.
	 * Este método es crucial para enlazar el Modelo, la Vista y el Controlador (MVC).
	 * <p><b>Precondición:</b> ventanaPrincipal no debe ser nula.
	 * <p><b>Postcondición:</b> 
	 * <pre>
	 * Este controlador se asigna a la ventanaPrincipal.
	 * Se registra como ActionListener para el botón de navegación de simulación.
	 * Se inicializa el PanelSimulacion, pasándole este controlador.
	 * </pre>
	 * @param ventanaPrincipal La instancia de la VentanaPrincipal (Vista).
	 * 
	 */
	public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal) {
		assert ventanaPrincipal != null : "Ventana principal no debe ser null";
		
		this.ventanaPrincipal = ventanaPrincipal;
		this.ventanaPrincipal.setControladorSimulacion(this);
		this.ventanaPrincipal.getBoton_navegacionSimulacion().addActionListener(this);
		this.panelSimulacion = new PanelSimulacion(ventanaPrincipal.getControladorSimulacion());
		
		assert ventanaPrincipal.getControladorSimulacion() != this : "El controlador de la ventana principal no es este";
		assert this.panelSimulacion != null : "panelSimulacion no fue iniciado correctamente";
	}

	
	/**
	 * Maneja los eventos de acción (clics en botones) provenientes de la vista.
	 * Delega las acciones al modelo (Clinica) o gestiona la navegación de la interfaz.
	 * <p><b>Precondición:</b> e no debe ser nulo.
	 * <p><b>Postcondición:</b> Se ejecuta la lógica correspondiente al comando del botón.
	 * @param e El evento de acción que contiene el comando del botón. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		assert e != null : "e no debe ser null";
		String comando = e.getActionCommand().toUpperCase();
		switch (comando) {

			case "SIMULACION":
				JPanel panelCentral = ventanaPrincipal.getPanel_Central();
			    CardLayout cl = (CardLayout) panelCentral.getLayout();

			    // Agregar solo si no existe ya
			    if (panelSimulacion.getParent() == null) {
			        panelCentral.add(panelSimulacion, "PANEL_SIMULACION");
			    }

			    cl.show(panelCentral, "PANEL_SIMULACION");

			    // Actualizar datos
			    panelSimulacion.actualizarNumAsociados(String.valueOf(clinica.getAsociados().size()));
			    break;
				
			case "INICIAR_SIM":
				if (!clinica.isSimulacionActiva())
					comenzarSimulacion();
				ventanaPrincipal.setBloqueoNavegacion(true);

				break;

			case "MANTENIMIENTO":
				if (clinica.isSimulacionActiva()) {
					Thread operario = new Thread(clinica.getOperario());
				    operario.start();
					panelSimulacion.agregarEvento("OPERARIO: Solicitud de mantenimiento");
				}
				break;

			case "FINALIZAR_SIMULACION":
				if (clinica.isSimulacionActiva())
					finalizarSimulacion();
				ventanaPrincipal.setBloqueoNavegacion(false);

				break;
		}
	}

	/**
	 * Inicia la simulación.
	 * Delega el inicio de los hilos al modelo (Clinica) y actualiza la vista (PanelSimulacion) con un mensaje. 
	 * Deshabilita los botones de navegacion para no poder modificar el modelo mientras la simulacion esta activa.
	 * <p><b>Postcondición:</b>
	 * <pre>
	 * La simulación en SingletonClinica se marca como activa.
	 * Los hilos de Asociados y RetornoAutomático son iniciados por la clínica.
	 * Se añade "Simulación iniciada." al log de eventos de la vista.
	 * </pre>
	 */
	public void comenzarSimulacion() {
		ventanaPrincipal.getBoton_navegacionAsociados().setEnabled(false);
		ventanaPrincipal.getBoton_navegacionSimulacion().setEnabled(false);
		ventanaPrincipal.getBoton_navegacionInicio().setEnabled(false);
		panelSimulacion.comienzaSimulacion();
		panelSimulacion.agregarEvento("Simulación iniciada.\n");
		clinica.lanzarSimulacion();
		
		assert clinica.isSimulacionActiva() != false : "No se inicializó correctamente la simulación";
	}
	/**
	 * Finaliza la simulación.
	 * Delega la detención de los hilos al modelo (Clinica) y resetea la vista.
	 * Rehabilita los botones de navegacion.
	 * <p><b>Postcondición:</b>
	 * <pre>
	 * La simulación en SingletonClinica se marca como inactiva.
	 * El estado de la ambulancia en la vista se resetea a "--".
	 * Se añade "Simulación finalizada." al log de eventos de la vista.
	 * </pre>
	 */
	public void finalizarSimulacion() {
		panelSimulacion.agregarEvento("Simulación finalizada.\n");
		panelSimulacion.actualizarEstadoAmbulancia("  --");
		ventanaPrincipal.getBoton_navegacionAsociados().setEnabled(true);
		ventanaPrincipal.getBoton_navegacionSimulacion().setEnabled(true);
		ventanaPrincipal.getBoton_navegacionInicio().setEnabled(true);
		panelSimulacion.finalizaSimulacion();
		clinica.finalizarSimulacion();
		
		assert clinica.isSimulacionActiva() != true : "No se finalizó correctamente la simulación";
	}

	/**
	 * Método invocado por el Observable (SingletonClinica) cuando hay un cambio en el Modelo.
	 * Actualiza la vista (PanelSimulacion) con la información recibida.
	 * <p><b>Precondición:</b>
	 * o debe ser la instancia de SingletonClinica y arg debe ser una instancia de String.
	 * <p><b>Postcondición:</b> 
	 * Si arg es un estado, actualiza el label de estado de la ambulancia.
	 * Si arg es un evento (otro String), lo añade al log de eventos de la vista.
	 * @param o   El objeto Observable que notificó (debería ser SingletonClinica).
	 * @param arg El argumento/mensaje pasado por el Observable (se espera un String).
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		if((SingletonClinica)o == clinica) {
			String msg = (String) arg;
			
			if(msg.startsWith("ESTADO:")) {
				String estado = msg.substring("ESTADO:".length());
				panelSimulacion.actualizarEstadoAmbulancia(estado);
			} else {
			panelSimulacion.agregarEvento(msg);
			}
		}
		
	}
}
