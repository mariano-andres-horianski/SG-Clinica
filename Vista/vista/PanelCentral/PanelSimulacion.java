package vista.PanelCentral;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.Simulacion.ActionListenerSimulacion;

public class PanelSimulacion extends JPanel {

    private static final long serialVersionUID = 1L;

    private ActionListenerSimulacion controladorSimulacion;
    private JButton btnMantenimiento;
    private JButton btnFinalizar;
    private JButton btnComenzar;
    private JTextArea textAreaEventos;
    private JLabel label_numAmbulancia;
    private JLabel label_numAsocRegistrados;

    public PanelSimulacion(ActionListenerSimulacion controladorSimulacion) {

        Panel_Inicio panel_Inicio = new Panel_Inicio();
        panel_Inicio.setPreferredSize(new Dimension(390, 278));
        panel_Inicio.setLayout(new BorderLayout());

        // ================== PANEL INFO ==================
        JPanel panel_info = new JPanel();
        panel_info.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_info.setLayout(new GridLayout(1, 2, 15, 0)); 
        panel_Inicio.add(panel_info, BorderLayout.NORTH);

        // --- Asociados registrados ---
        JPanel panel_AsocRegistrados = new JPanel(new BorderLayout());
        panel_AsocRegistrados.setBackground(Color.WHITE);
        panel_info.add(panel_AsocRegistrados);

        label_numAsocRegistrados = new JLabel("  00");
        label_numAsocRegistrados.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panel_AsocRegistrados.add(label_numAsocRegistrados, BorderLayout.CENTER);
        panel_AsocRegistrados.add(new JLabel("   Asociados Registrados"), BorderLayout.SOUTH);

        // --- Estado de Ambulancia ---
        JPanel panel_EstadoDeAmbulancia = new JPanel(new BorderLayout());
        panel_EstadoDeAmbulancia.setBackground(Color.WHITE);
        panel_info.add(panel_EstadoDeAmbulancia);

        label_numAmbulancia = new JLabel("  --");
        label_numAmbulancia.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panel_EstadoDeAmbulancia.add(label_numAmbulancia, BorderLayout.CENTER);
        panel_EstadoDeAmbulancia.add(new JLabel("   Estado De Ambulancia"), BorderLayout.SOUTH);

        // ================== PANEL LOG ==================
        JPanel panel_log = new JPanel();
        panel_log.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel_log.setLayout(new BoxLayout(panel_log, BoxLayout.X_AXIS));
        panel_Inicio.add(panel_log, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        panel_log.add(scrollPane);

        JLabel tituloEventos = new JLabel("   Eventos Recientes");
        tituloEventos.setHorizontalAlignment(SwingConstants.LEFT);
        scrollPane.setColumnHeaderView(tituloEventos);

        // Crear text area y agregarla al scrollpane
        textAreaEventos = new JTextArea();
        textAreaEventos.setEditable(false); // que no se pueda escribir
        scrollPane.setViewportView(textAreaEventos);


        // ================== PANEL BOTONES ==================
        JPanel panel_botones = new JPanel();
        panel_Inicio.add(panel_botones, BorderLayout.SOUTH);

        btnMantenimiento = new JButton("Mantenimiento");
        btnFinalizar = new JButton("Finalizar");
        btnComenzar = new JButton("Comenzar");
        
        btnComenzar.setActionCommand("INICIAR_SIM");
        btnMantenimiento.setActionCommand("MANTENIMIENTO");
        btnFinalizar.setActionCommand("FINALIZAR_SIMULACION");
        
        btnComenzar.addActionListener(controladorSimulacion);
        btnMantenimiento.addActionListener(controladorSimulacion);
        btnFinalizar.addActionListener(controladorSimulacion);
        
        btnMantenimiento.setEnabled(false);
        btnFinalizar.setEnabled(false);

        panel_botones.add(btnComenzar);
        panel_botones.add(btnMantenimiento);
        panel_botones.add(btnFinalizar);

       
        this.setLayout(new BorderLayout());
        this.add(panel_Inicio, BorderLayout.CENTER);
    }
    
    public void agregarEvento(String e) {
        String tiempo = java.time.LocalTime.now().withNano(0).toString();
        textAreaEventos.append("[" + tiempo + "] " + e + "\n");
    }
    
    public void actualizarEstadoAmbulancia(String estado) {
        label_numAmbulancia.setText(estado.toString());
    }
    
    public void actualizarNumAsociados(String estado) {
        label_numAsocRegistrados.setText("  " + estado.toString());
    }
    
    public void comienzaSimulacion() {
    	btnComenzar.setEnabled(false);
		btnFinalizar.setEnabled(true);
		btnMantenimiento.setEnabled(true);
    }

    public void finalizaSimulacion() {
    	btnComenzar.setEnabled(true);
		btnFinalizar.setEnabled(false);
		btnMantenimiento.setEnabled(false);
    }
    
    public JButton getBtnMantenimiento() { return btnMantenimiento; }
    public JButton getBtnFinalizar() { return btnFinalizar; }
    public JButton getBtnComenzar() { return btnComenzar; }
}
