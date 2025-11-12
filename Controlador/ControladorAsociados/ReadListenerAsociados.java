package ControladorAsociados;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;

import persistencia.DAOAsociadoYDTO.AsociadoDTO;

public class ReadListenerAsociados extends MouseAdapter{
	private ActionListenerAsociados controladorAsociados;
	private JButton botonAsociados;
	public ReadListenerAsociados(ActionListenerAsociados controladorAsociados) {
		this.controladorAsociados=controladorAsociados;
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		ActionEvent e = new ActionEvent(arg0.getSource(),0,"READ");
		this.controladorAsociados.actionPerformed(e);
	}
}
