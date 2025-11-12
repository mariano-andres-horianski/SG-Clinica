package ControladorAsociados;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import persistencia.DAOAsociadoYDTO.AsociadoDTO;
import vista.formularios.FormularioUpdateAsociado;
/**
 * Listener para crear un pop up cuando se intenta actualziar un asociado
 */
public class UpdateListenerAsociado extends MouseAdapter {
	private AsociadoDTO socio;
	
	@Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            // Crear el formulario de edici�n con el socio actual
            FormularioUpdateAsociado form = new FormularioUpdateAsociado(socio);

            // Mostrar el formulario como di�logo modal
            form.setLocationRelativeTo(null); // Centrado
            form.setVisible(true);
            
        }
    }
}
