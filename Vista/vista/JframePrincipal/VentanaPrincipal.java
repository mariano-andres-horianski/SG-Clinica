package vista.JframePrincipal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.CardLayout;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JToggleButton;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;

import ControladorAsociados.ActionListenerAsociados;
import vista.PanelCentral.Panel_Inicio;

import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ActionListenerAsociados controladorAsociados;
	private JButton boton_navegacionAsociados;
	private JButton boton_navegacionInicio;
	private JButton boton_navegacionPacientes;
	private JButton boton_navegacionAtencion;
	private JButton btnAgregarPaciente;
	private JButton btnFacturacion;
	private JPanel panel_Central;

	/**
	 * Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	 */
	public JPanel getPanel_Central() {
		return panel_Central;
	}

	public void setPanel_Central(JPanel panel_Central) {
		this.panel_Central = panel_Central;
	}

	/**
	 * Create the frame.
	 */
	
	public VentanaPrincipal() {
		setTitle("Sistema - Gestion de Clinica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 384);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem menu_Archivo = new JMenuItem("Archivo");
		menuBar.add(menu_Archivo);
		
		JMenuItem menu_Gestion = new JMenuItem("Gestion");
		menuBar.add(menu_Gestion);
		
		JMenuItem menu_Simulacion = new JMenuItem("Simulacion");
		menuBar.add(menu_Simulacion);
		
		JMenuItem menu_Herramientas = new JMenuItem("Herramientas");
		menuBar.add(menu_Herramientas);
		
		JMenuItem menu_Ayuda = new JMenuItem("Ayuda");
		menuBar.add(menu_Ayuda);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel_Central = new JPanel();
		contentPane.add(panel_Central, BorderLayout.CENTER);
		panel_Central.setLayout(new CardLayout(0, 0));
		//
		Panel_Inicio panel_Inicio = new Panel_Inicio();
		panel_Inicio.setSize(new Dimension(0, 200));
		panel_Inicio.setBounds(new Rectangle(0, 0, 0, 200));
		panel_Inicio.setPreferredSize(new Dimension(10, 200));
		panel_Central.add(panel_Inicio, "name_1387589521300");
		panel_Inicio.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_info = new JPanel();
		panel_info.setPreferredSize(new Dimension(10, 80));
		panel_info.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_Inicio.add(panel_info, BorderLayout.NORTH);
		panel_info.setLayout(new GridLayout(1, 4, 15, 0));
		
		JPanel panel_AsocRegistrados = new JPanel();
		panel_AsocRegistrados.setPreferredSize(new Dimension(80, 30));
		panel_AsocRegistrados.setBackground(new Color(255, 255, 255));
		panel_info.add(panel_AsocRegistrados);
		panel_AsocRegistrados.setLayout(new BorderLayout(0, 0));
		
		JLabel label_numAsocRegistrados = new JLabel("  00");
		label_numAsocRegistrados.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_AsocRegistrados.add(label_numAsocRegistrados, BorderLayout.CENTER);
		
		JLabel lblAsociadosRegistrados = new JLabel("   Asociados Registrados");
		panel_AsocRegistrados.add(lblAsociadosRegistrados, BorderLayout.SOUTH);
		
		JPanel panel_PacientesEnEspera = new JPanel();
		panel_PacientesEnEspera.setPreferredSize(new Dimension(80, 30));
		panel_PacientesEnEspera.setBackground(Color.WHITE);
		panel_info.add(panel_PacientesEnEspera);
		panel_PacientesEnEspera.setLayout(new BorderLayout(0, 0));
		
		JLabel label_numAsocRegistrados_1 = new JLabel("  00");
		label_numAsocRegistrados_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_PacientesEnEspera.add(label_numAsocRegistrados_1, BorderLayout.CENTER);
		
		JLabel label_PacientesEnEspera = new JLabel("   Pacientes En Espera");
		panel_PacientesEnEspera.add(label_PacientesEnEspera, BorderLayout.SOUTH);
		
		JPanel panel_AsocRegistrados_2 = new JPanel();
		panel_AsocRegistrados_2.setPreferredSize(new Dimension(80, 30));
		panel_AsocRegistrados_2.setBackground(Color.WHITE);
		panel_info.add(panel_AsocRegistrados_2);
		panel_AsocRegistrados_2.setLayout(new BorderLayout(0, 0));
		
		JLabel label_numPacientesEnAtencion = new JLabel("  00");
		label_numPacientesEnAtencion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_AsocRegistrados_2.add(label_numPacientesEnAtencion, BorderLayout.CENTER);
		
		JLabel label_PacientesEnAtencion = new JLabel("   Pacientes En Atencion");
		label_PacientesEnAtencion.setVerticalAlignment(SwingConstants.TOP);
		label_PacientesEnAtencion.setHorizontalAlignment(SwingConstants.LEFT);
		panel_AsocRegistrados_2.add(label_PacientesEnAtencion, BorderLayout.SOUTH);
		
		JPanel panel_EstadoDeAmbulancia = new JPanel();
		panel_EstadoDeAmbulancia.setPreferredSize(new Dimension(80, 30));
		panel_EstadoDeAmbulancia.setBackground(Color.WHITE);
		panel_info.add(panel_EstadoDeAmbulancia);
		panel_EstadoDeAmbulancia.setLayout(new BorderLayout(0, 0));
		
		JLabel label_numAmbulancia = new JLabel("  00");
		label_numAmbulancia.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_EstadoDeAmbulancia.add(label_numAmbulancia, BorderLayout.WEST);
		
		JLabel label_Ambulancia = new JLabel("   Estado De Ambulancia");
		panel_EstadoDeAmbulancia.add(label_Ambulancia, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_Inicio.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("   Eventos Recientes");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_Inicio.add(panel_2, BorderLayout.SOUTH);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(13, 50));
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JPanel panel_VistaActual = new JPanel();
		toolBar.add(panel_VistaActual);
		panel_VistaActual.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInicio = new JLabel("Inicio");
		lblInicio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInicio.setHorizontalAlignment(SwingConstants.CENTER);
		panel_VistaActual.add(lblInicio, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		toolBar.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		toolBar.add(panel_4);
		
		btnAgregarPaciente = new JButton(" + Agregar Paciente");
		panel_4.add(btnAgregarPaciente);
		
		JPanel panel_5 = new JPanel();
		toolBar.add(panel_5);
		
		btnFacturacion = new JButton(" Facturacion");
		panel_5.add(btnFacturacion);
		
		JPanel panel_Navegacion = new JPanel();
		panel_Navegacion.setSize(new Dimension(130, 0));
		panel_Navegacion.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel_Navegacion, BorderLayout.WEST);
		panel_Navegacion.setLayout(new GridLayout(0, 1, 0, 0));
		
		boton_navegacionInicio = new JButton("Inicio");
		panel_Navegacion.add(boton_navegacionInicio);
		
		boton_navegacionAsociados = new JButton("Asociados");
		boton_navegacionAsociados.addActionListener(this.controladorAsociados);
		panel_Navegacion.add(boton_navegacionAsociados);
		
		boton_navegacionPacientes = new JButton("Pacientes");
		panel_Navegacion.add(boton_navegacionPacientes);
		
		boton_navegacionAtencion = new JButton("Atencion");
		panel_Navegacion.add(boton_navegacionAtencion);
		
		JPanel panel_aux1 = new JPanel();
		panel_Navegacion.add(panel_aux1);
		
		JPanel panel_aux2 = new JPanel();
		panel_Navegacion.add(panel_aux2);
		
		JPanel panel = new JPanel();
		panel_Navegacion.add(panel);

	}

	public void setControladorAsociados(ActionListenerAsociados controladorAsociados) {
		this.controladorAsociados = controladorAsociados;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public ActionListenerAsociados getControladorAsociados() {
		return controladorAsociados;
	}

	public JButton getBoton_navegacionAsociados() {
		return boton_navegacionAsociados;
	}

	public JButton getBoton_navegacionInicio() {
		return boton_navegacionInicio;
	}

	public JButton getBoton_navegacionPacientes() {
		return boton_navegacionPacientes;
	}

	public JButton getBoton_navegacionAtencion() {
		return boton_navegacionAtencion;
	}
	public JButton getBtnAgregarPaciente() {
		return btnAgregarPaciente;
	}

	public JButton getBtnFacturacion() {
		return btnFacturacion;
	}
	
	
}
