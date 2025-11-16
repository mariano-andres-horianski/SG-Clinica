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

public class ActionListenerSimulacion implements ActionListener, Observer {

	private VentanaPrincipal ventanaPrincipal;
	private PanelSimulacion panelSimulacion;
	private SingletonClinica clinica;
	private AsociadoDAOMySQL BD;

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

	public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		this.ventanaPrincipal.setControladorSimulacion(this);
		this.ventanaPrincipal.getBoton_navegacionSimulacion().addActionListener(this);
		this.panelSimulacion = new PanelSimulacion(ventanaPrincipal.getControladorSimulacion());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
				break;
		}
	}

	
	public void comenzarSimulacion() {
		panelSimulacion.agregarEvento("Simulación iniciada.\n");
		ventanaPrincipal.getBoton_navegacionAsociados().setEnabled(false);
		ventanaPrincipal.getBoton_navegacionSimulacion().setEnabled(false);
		ventanaPrincipal.getBoton_navegacionInicio().setEnabled(false);
		panelSimulacion.comienzaSimulacion();
		clinica.lanzarSimulacion();
	}
	
	public void finalizarSimulacion() {
		panelSimulacion.agregarEvento("Simulación finalizada.\n");
		panelSimulacion.actualizarEstadoAmbulancia("  --");
		ventanaPrincipal.getBoton_navegacionAsociados().setEnabled(true);
		ventanaPrincipal.getBoton_navegacionSimulacion().setEnabled(true);
		ventanaPrincipal.getBoton_navegacionInicio().setEnabled(true);
		panelSimulacion.finalizaSimulacion();
		clinica.finalizarSimulacion();
	}

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
