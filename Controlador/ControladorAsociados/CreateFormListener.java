package ControladorAsociados;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import persistencia.DAOAsociadoYDTO.AsociadoDTO;
import vista.formularios.FormularioUpdateAsociado;
/**
 * Listener para el boton de "guardar" en el formulario de creacion de socio
 */
public class CreateFormListener extends MouseAdapter{
	private ActionListener controlador;

    public CreateFormListener(ActionListener controlador) {
        this.controlador = controlador;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	AsociadoDTO asociado = new AsociadoDTO();
    	FormularioUpdateAsociado formulario = (FormularioUpdateAsociado) e.getSource();
    	asociado.setCiudad(formulario.getTxtCiudad().getText());
    	asociado.setDni(formulario.getTxtDni().getText());
    	asociado.setDomicilioStr(formulario.getTxtDomicilio().getText());
    	asociado.setNya(formulario.getTxtNya().getText());
    	asociado.setTelefono(formulario.getTxtTelefono().getText());
    	
        if (SwingUtilities.isLeftMouseButton(e)) {
            // Crear y enviar un ActionEvent al controlador
            ActionEvent evento = new java.awt.event.ActionEvent(asociado, 0, "CREATE");

            controlador.actionPerformed(evento);
        }
    }
}
