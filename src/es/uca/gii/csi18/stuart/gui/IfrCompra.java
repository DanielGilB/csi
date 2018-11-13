package es.uca.gii.csi18.stuart.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.uca.gii.csi18.stuart.data.Compra;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class IfrCompra extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtImporte;
	private Compra _compra = null;

	/**
	 * Create the frame.
	 */
	public IfrCompra() {
		setResizable(true);
		setClosable(true);
		setTitle("Compra");
		setBounds(100, 100, 900, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(61, 65, 73, 14);
		getContentPane().add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(153, 62, 86, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Importe");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(61, 111, 73, 14);
		getContentPane().add(lblNewLabel);
		
		txtImporte = new JTextField();
		txtImporte.setBounds(153, 108, 86, 20);
		getContentPane().add(txtImporte);
		txtImporte.setColumns(10);
		
		JButton butGuardar = new JButton("Guardar");
		butGuardar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		butGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Double dImporte = Double.parseDouble(txtImporte.getText());
					String sNombre = txtNombre.getText();
					if(_compra == null)
						_compra = Compra.Create(sNombre, dImporte);
					else {
						_compra.setNombre(sNombre);
						_compra.setImporte(dImporte);
						_compra.Update();
					}
					
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Existen campos incorrectos: " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				} 
			}
		});
		butGuardar.setBounds(61, 175, 178, 23);
		getContentPane().add(butGuardar);

	}
}
