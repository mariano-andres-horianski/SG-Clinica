package ControladorAsociados;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import vista.formularios.FormularioCreateAsociado;
/**
 * Listener para hacer un pop up de un form para crear un asociado nuevo
 */
public class CreateListenerAsociados extends MouseAdapter{
	
	@Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            FormularioCreateAsociado form = new FormularioCreateAsociado();

            form.setLocationRelativeTo(null);
            form.setVisible(true);
            
        }
    }
}
