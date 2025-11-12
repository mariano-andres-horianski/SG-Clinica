package vista.PanelCentral;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import ControladorAsociados.ActionListenerAsociados;
import persistencia.DAOAsociadoYDTO.AsociadoDTO;
import vista.RendererAsociados.ButtonRenderer;
import vista.RendererAsociados.*;
import java.awt.Component;
public class PanelAsociados extends JPanel{
	private ActionListenerAsociados controladorAsociados;
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JButton btnAgregar;
	/**
	 * Create the panel.
	 */
	public PanelAsociados(HashMap<String, AsociadoDTO> socios, ActionListenerAsociados controladorAsociados) {
		this.controladorAsociados = controladorAsociados;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Botón superior
        btnAgregar = new JButton("Agregar asociado");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(btnAgregar);
        add(topPanel, BorderLayout.NORTH);

        // ---- Modelo de tabla
        modelo = new DefaultTableModel(new Object[]{"Nombre y Apellido", "DNI", "Ciudad", "Teléfono", "Domicilio", "Acciones"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna de botones es editable
                return column == 5;
            }
        };

        // ---- Cargar datos
        if (socios.isEmpty()) {
            modelo.addRow(new Object[]{"No hay socios registrados.", "", "", "", "", ""});
        } else {
            for (AsociadoDTO s : socios.values()) {
                modelo.addRow(new Object[]{
                    s.getNya(),
                    s.getDni(),
                    s.getCiudad(),
                    s.getTelefono(),
                    s.getDomicilioStr(),
                    null // columna de botones
                });
            }
        }

        // ---- Crear tabla
        tabla = new JTable(modelo);
        tabla.setRowHeight(30);

        // ---- Añadir botones a la última columna
        TableColumn colAcciones = tabla.getColumnModel().getColumn(5);
        colAcciones.setCellRenderer(new ButtonRenderer());
        //colAcciones.setCellEditor(new ButtonEditor(new JCheckBox(), controladorAsociados));
        modelo.addColumn("Acciones");
        tabla = new JTable(modelo);
        
        tabla.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("Acciones").setCellEditor(new ButtonEditor(tabla, controladorAsociados));
        add(new JScrollPane(tabla), BorderLayout.CENTER);
	}
	
	public void addMouseListenerTabla(MouseListener listener) {
        tabla.addMouseListener(listener);
    }

    public JTable getTabla() {
        return tabla;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

	public JButton getBtnAgregar() {
		return btnAgregar;
	}
    
}
