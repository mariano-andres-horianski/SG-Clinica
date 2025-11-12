package vista.formularios;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FormularioCreateAsociado extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textNYA;
    private JTextField textDNI;
    private JTextField textCiudad;
    private JTextField textTelefono;
    private JTextField textDomicilio;

    private JButton okButton;
    private JButton cancelButton;

    public FormularioCreateAsociado() {
        setTitle("Nuevo Asociado");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 320);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Formulario con etiquetas y campos
        contentPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel lblNYA = new JLabel("Nombre y Apellido:");
        lblNYA.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(lblNYA);
        textNYA = new JTextField();
        contentPanel.add(textNYA);

        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(lblDNI);
        textDNI = new JTextField();
        contentPanel.add(textDNI);

        JLabel lblCiudad = new JLabel("Ciudad:");
        lblCiudad.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(lblCiudad);
        textCiudad = new JTextField();
        contentPanel.add(textCiudad);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(lblTelefono);
        textTelefono = new JTextField();
        contentPanel.add(textTelefono);

        JLabel lblDomicilio = new JLabel("Domicilio:");
        lblDomicilio.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPanel.add(lblDomicilio);
        textDomicilio = new JTextField();
        contentPanel.add(textDomicilio);

        // Panel inferior con botones
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        okButton = new JButton("Guardar");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        cancelButton = new JButton("Cancelar");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    // Getters (mismos nombres que antes)
    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JTextField getTextNYA() {
        return textNYA;
    }

    public JTextField getTextDNI() {
        return textDNI;
    }

    public JTextField getTextCiudad() {
        return textCiudad;
    }

    public JTextField getTextTelefono() {
        return textTelefono;
    }

    public JTextField getTextDomicilio() {
        return textDomicilio;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

}
