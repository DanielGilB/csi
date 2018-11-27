package es.uca.gii.csi18.stuart.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.uca.gii.csi18.stuart.data.Compra;
import es.uca.gii.csi18.stuart.data.Descuento;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;

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
	public IfrCompra(Compra compra) {
		_compra = compra;			
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
		txtNombre.setBounds(153, 62, 249, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblImporte = new JLabel("Importe");
		lblImporte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblImporte.setBounds(61, 111, 73, 14);
		getContentPane().add(lblImporte);
		
		txtImporte = new JTextField();
		txtImporte.setBounds(153, 108, 86, 20);
		getContentPane().add(txtImporte);
		txtImporte.setColumns(10);
		
		JLabel lblDescuento = new JLabel("Descuento");
		lblDescuento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescuento.setBounds(61, 158, 73, 14);
		getContentPane().add(lblDescuento);
		
		JComboBox<Descuento> cmbDescuento = new JComboBox();
		try {
			cmbDescuento.setModel(new DescuentoListModel(Descuento.Select()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No hay descuentos: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		cmbDescuento.setBounds(153, 157, 249, 20);
		getContentPane().add(cmbDescuento);
		
		if(_compra != null) {
			txtNombre.setText(_compra.getNombre());
			String dImporte = new Double (_compra.getImporte()).toString();
			txtImporte.setText(dImporte);
			cmbDescuento.setSelectedIndex(_compra.getDescuento().getId()); //aquí ponemos el descuento mediante su Id - no estoy seguro habria que probarlo en ejecucion
		}
		
		JButton butGuardar = new JButton("Guardar");
		butGuardar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		butGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Double dImporte = Double.parseDouble(txtImporte.getText());
					String sNombre = txtNombre.getText();
					Descuento descuento = (Descuento) cmbDescuento.getSelectedItem(); // el cast no estoy seguro y puede que lanze el throw de abajo
					
					if(descuento == null)
						throw new Exception("Selecciona un descuento");
					
					if(_compra == null)
						_compra = Compra.Create(sNombre, dImporte, descuento);
					else{
						_compra.setNombre(sNombre);
						_compra.setImporte(dImporte);
						_compra.setDescuento(descuento);
						_compra.Update();
						_compra.Update();
					}
					
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Existen campos incorrectos: " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				} 
			}
		});
		butGuardar.setBounds(61, 202, 178, 23);
		getContentPane().add(butGuardar);
	}
}
