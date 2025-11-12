package vista.RendererAsociados;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import ControladorAsociados.ActionListenerAsociados;
import persistencia.DAOAsociadoYDTO.AsociadoDTO;
import persistencia.DAOAsociadoYDTO.AsociadoDAOMySQL;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JTable tabla;
    private ActionListenerAsociados controlador;

    public ButtonEditor(JTable tabla, ActionListenerAsociados controlador) {
        this.tabla = tabla;
        this.controlador = controlador;

        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.add(btnEditar);
        panel.add(btnEliminar);

        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String dni = (String) tabla.getValueAt(fila, 1);
                //AsociadoDTO socio = BD.obtenerPorDni(dni);

                // Mandar evento "UPDATE" al controlador
                //ActionEvent evento = new ActionEvent(socio, ActionEvent.ACTION_PERFORMED, "UPDATE");
                //controlador.actionPerformed(evento);
            }
            fireEditingStopped();
        });

        // Botón eliminar (opcional)
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String dni = (String) tabla.getValueAt(fila, 1);
                //AsociadoDTO socio = BD.obtenerPorDni(dni);

                //ActionEvent evento = new ActionEvent(socio, ActionEvent.ACTION_PERFORMED, "DELETE");
                //controlador.actionPerformed(evento);
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
