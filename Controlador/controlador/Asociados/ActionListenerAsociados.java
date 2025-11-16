package controlador.Asociados;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clinica.SingletonClinica;
import persistencia.BasedeDatos.BDConexion;
import persistencia.DAOAsociadoYDTO.AsociadoDAOMySQL;
import persistencia.DAOAsociadoYDTO.AsociadoDTO;
import persistencia.Excepciones.AsociadoExistenteException;
import persistencia.Excepciones.AsociadoNotFoundException;
import persistencia.Excepciones.DatoInvalidoException;
import vista.JframePrincipal.VentanaPrincipal;
import vista.PanelCentral.PanelAsociados;
import vista.PanelCentral.PanelSimulacion;
import vista.formularios.ConfirmDialog;
import vista.formularios.FormularioCreateAsociado;
import vista.formularios.FormularioUpdateAsociado;

public class ActionListenerAsociados implements ActionListener {
	private VentanaPrincipal ventanaPrincipal;
	private AsociadoDAOMySQL BD;
	private PanelAsociados panelAsociados;
	private SingletonClinica clinica; // necesario para usar el HashMap de asociados, porque el AsociadoDTO no es Runnable
	public ActionListenerAsociados() {
		this.clinica = SingletonClinica.getInstance();
		try {
			this.BD = new AsociadoDAOMySQL();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		this.ventanaPrincipal.setControladorAsociados(this);
		this.ventanaPrincipal.setVisible(true);
		this.ventanaPrincipal.getBoton_navegacionAsociados().addMouseListener(new ReadListenerAsociados(this));
		this.ventanaPrincipal.getBoton_navegacionInicio().addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand().toUpperCase();
		HashMap<String, AsociadoDTO> asociados;
		PanelAsociados listado;
		JPanel panelCentral = ventanaPrincipal.getPanel_Central();;
		CardLayout cl;
		
		switch(comando) {
			case "INICIO":
				JPanel panelInicio = new JPanel();
			    panelInicio.add(new JLabel("Bienvenido al sistema"));

			    panelCentral = ventanaPrincipal.getPanel_Central();

			    panelCentral.add(panelInicio, "PANEL_INICIO");

			    cl = (CardLayout) panelCentral.getLayout();
			    cl.show(panelCentral, "PANEL_INICIO");

			    panelCentral.revalidate();
			    panelCentral.repaint();
			    break;

			case "CREATE"://usuario hizo click en "guardar" en el formulario de creacion
			    AsociadoDTO nuevoSocio = (AsociadoDTO)e.getSource();

			    try {
			        BD.agregar(nuevoSocio);
			    } catch (AsociadoExistenteException e1) {
			        e1.printStackTrace();
			    }

			    // Actualizar tabla del panel ya creado
			    asociados = BD.obtenerTodosMap();
			    panelAsociados.refrescarTabla(asociados);
			    break;
			case "READ"://el usuario hizo click en el boton "asociados" para modificar el card layout, no hay pop up
				//Modificar el panel central de la ventana principal para que contenga una lista de asociados
				asociados = BD.obtenerTodosMap();

			    panelAsociados = new PanelAsociados(asociados, this);

			    panelCentral = ventanaPrincipal.getPanel_Central();
			    panelCentral.add(panelAsociados, "PANEL_ASOCIADOS");

			    cl = (CardLayout) panelCentral.getLayout();
			    cl.show(panelCentral, "PANEL_ASOCIADOS");

			    panelAsociados.getBtnAgregar().addMouseListener(new CreateListenerAsociados(this));
			    break;
			case "UPDATE":
				AsociadoDTO socio = (AsociadoDTO) e.getSource();
				try {
					BD.actualizar(socio);
				} catch (AsociadoNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			    asociados = BD.obtenerTodosMap();
			    panelAsociados.refrescarTabla(asociados);  // ← ESTA ES LA LINEA CORRECTA
			    break;
			case "DELETE":
			    AsociadoDTO socioEliminado = (AsociadoDTO)e.getSource();
				try {
					BD.eliminarPorDni(socioEliminado.getDni());
				} catch (AsociadoNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			    asociados = BD.obtenerTodosMap();
			    panelAsociados.refrescarTabla(asociados);
			    break;
			case "SELECT_UPDATE":
				FormularioUpdateAsociado form = new FormularioUpdateAsociado((AsociadoDTO)e.getSource(),this);
				form.setLocationRelativeTo(null);
	            form.setVisible(true);
	            
				break;
			case "SELECT_DELETE":
				ConfirmDialog popUpEliminar = new ConfirmDialog(null,
					    "¿Está seguro que desea eliminar este asociado?",
					    "DELETE",
					    this,
					    (AsociadoDTO)e.getSource()
					);
				popUpEliminar.setVisible(true);
				break;
		}
		
	}


	
}
