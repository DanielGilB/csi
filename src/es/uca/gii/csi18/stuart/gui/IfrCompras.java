package es.uca.gii.csi18.stuart.gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.uca.gii.csi18.stuart.data.Compra;
import es.uca.gii.csi18.stuart.data.Descuento;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class IfrCompras extends JInternalFrame {
	private JTextField txtNombre;
	private JTextField txtImporte;
	private JTable tabResult;
	private Container pnlParent;

	
	public IfrCompras(Container frame) {
		pnlParent = frame;
		setClosable(true);
		setResizable(true);
		setTitle("Compras");
		setBounds(100, 100, 846, 353);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNombre = new JLabel("Nombre");
		panel.add(lblNombre);
		
		txtNombre = new JTextField();
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblImporte = new JLabel("Importe");
		panel.add(lblImporte);
		
		txtImporte = new JTextField();
		panel.add(txtImporte);
		txtImporte.setColumns(10);
		
		JLabel lblDescuento = new JLabel("Descuento");
		panel.add(lblDescuento);
		
		JComboBox<Descuento> cmbDescuento = new JComboBox();
		cmbDescuento.setEditable(true);
		panel.add(cmbDescuento);

		try {
			cmbDescuento.setModel(new DescuentoListModel(Descuento.Select()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No hay descuentos: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		
		JButton butBuscar = new JButton("Buscar"); 
		butBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
					String sNombre = txtNombre.getText().isEmpty() ?
							null : txtNombre.getText();
					Double dImporte = txtImporte.getText().isEmpty() ?
							null : new Double(Double.parseDouble(txtImporte.getText()));
					/*Integer iDescuento = cmbDescuento.getSelectedIndex();*/
				try {	
					tabResult.setModel(new ComprasTableModel(Compra.Select(sNombre, dImporte, null)));
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Existen campos incorrectos: " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				} 	
			}
		});
		
		panel.add(butBuscar);
		
		tabResult = new JTable();
		tabResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int iRow = ((JTable)e.getSource()).getSelectedRow();
					Compra compra = ((ComprasTableModel)tabResult.getModel()).getData(iRow);
				
					if(compra != null) {
						IfrCompra  ifrCompra = new IfrCompra(compra);
						ifrCompra.setBounds(150, 50, 350, 300);
						pnlParent.add(ifrCompra, 0);
						ifrCompra.setVisible(true);
					}
				}
			}
		});
		getContentPane().add(tabResult, BorderLayout.CENTER);
	}
}
