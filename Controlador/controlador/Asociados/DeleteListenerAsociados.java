package controlador.Asociados;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import persistencia.DAOAsociadoYDTO.AsociadoDTO;
/*
public class DeleteListenerAsociados extends MouseAdapter{
	private AsociadoDTO socio;
	private ActionListenerAsociados controladorSocios;

    public DeleteListenerAsociados(AsociadoDTO socio, ActionListenerAsociados controladorSocios) {
        this.socio = socio;
        this.controladorSocios = controladorSocios;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) { // click izquierdo
            int opt = JOptionPane.showConfirmDialog(
                null,
                "�Est� seguro de que desea eliminar al socio: " + socio.getNya() + "?",
                "Confirmar eliminaci�n",
                JOptionPane.YES_NO_OPTION
            );

            if (opt == JOptionPane.YES_OPTION) {
            	ActionEvent res = new ActionEvent(e.getSource(),0,"DELETE");
                controladorSocios.actionPerformed(res);
            }
        }
    }
}
*/