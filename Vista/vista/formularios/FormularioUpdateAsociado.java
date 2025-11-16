package vista.formularios;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controlador.Asociados.ActionListenerAsociados;

import java.awt.*;
import java.awt.event.ActionEvent;

import persistencia.DAOAsociadoYDTO.AsociadoDTO;

public class FormularioUpdateAsociado extends JDialog {
    private JTextField txtDni, txtNya, txtCiudad, txtTelefono, txtDomicilio;
    private JButton btnGuardar, btnCancelar;
    private ActionListenerAsociados controlador;
    public FormularioUpdateAsociado(AsociadoDTO socio,ActionListenerAsociados controlador) {
        setTitle("Editar Asociado");
        this.controlador = controlador;
        setModal(true); // Hace que bloquee la ventana principal
        setSize(350, 300);
        setLayout(new GridLayout(6, 2, 5, 5));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Crear campos
        txtDni = new JTextField(socio.getDni());
        txtDni.setEditable(false); // DNI inmutable
        txtNya = new JTextField(socio.getNya());
        txtCiudad = new JTextField(socio.getCiudad());
        txtTelefono = new JTextField(socio.getTelefono());
        txtDomicilio = new JTextField(socio.getDomicilioStr());

        // Botones
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        // Agregar al layout
        add(new JLabel("DNI:"));
        add(txtDni);
        add(new JLabel("Nombre y Apellido:"));
        add(txtNya);
        add(new JLabel("Ciudad:"));
        add(txtCiudad);
        add(new JLabel("Tel�fono:"));
        add(txtTelefono);
        add(new JLabel("Domicilio:"));
        add(txtDomicilio);
        add(btnGuardar);
        add(btnCancelar);

        // Accion del boton guardar
        btnGuardar.addActionListener(e -> {
            socio.setNya(txtNya.getText());
            socio.setCiudad(txtCiudad.getText());
            socio.setTelefono(txtTelefono.getText());
            socio.setDomicilioStr(txtDomicilio.getText());
            controlador.actionPerformed(new ActionEvent(socio,0,"UPDATE"));
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
        configurarValidacion();
    }

	public JTextField getTxtDni() {
		return txtDni;
	}

	public JTextField getTxtNya() {
		return txtNya;
	}

	public JTextField getTxtCiudad() {
		return txtCiudad;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public JTextField getTxtDomicilio() {
		return txtDomicilio;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	private void validarFormulario() {
	    boolean valido = true;

	    if (txtNya.getText().trim().isEmpty()) valido = false;
	    if (txtCiudad.getText().trim().isEmpty()) valido = false;
	    if (txtTelefono.getText().trim().isEmpty()) valido = false;
	    if (txtDomicilio.getText().trim().isEmpty()) valido = false;

	    try {
	        Long.parseLong(txtTelefono.getText());
	    } catch (Exception e) {
	        valido = false;
	    }

	    btnGuardar.setEnabled(valido);
	}

	private void configurarValidacion() {
	    DocumentListener listener = new DocumentListener() {
	        @Override public void insertUpdate(DocumentEvent e) { validarFormulario(); }
	        @Override public void removeUpdate(DocumentEvent e) { validarFormulario(); }
	        @Override public void changedUpdate(DocumentEvent e) { validarFormulario(); }
	    };

	    txtNya.getDocument().addDocumentListener(listener);
	    txtCiudad.getDocument().addDocumentListener(listener);
	    txtTelefono.getDocument().addDocumentListener(listener);
	    txtDomicilio.getDocument().addDocumentListener(listener);
	}

    
}
