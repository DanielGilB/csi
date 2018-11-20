package es.uca.gii.csi18.stuart.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain window = new FrmMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrmMain() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (InstantiationException e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Gesti\u00F3n de compras");
		frame.setBounds(200, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mitNuevo = new JMenu("Nuevo");
		menuBar.add(mitNuevo);
		
		JMenuItem mitNuevoCompra = new JMenuItem("Compra");
		mitNuevoCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				IfrCompra ifrCompra = new IfrCompra(null);
				ifrCompra.setBounds(150, 50, 350, 300);
				frame.getContentPane().add(ifrCompra);
				ifrCompra.setVisible(true);
			}
		});
		mitNuevo.add(mitNuevoCompra);
		
		JMenu mitBuscar = new JMenu("Buscar");
		menuBar.add(mitBuscar);
		
		JMenuItem mitBuscarCompra = new JMenuItem("Compra");
		mitBuscarCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IfrCompras ifrCompras = new IfrCompras(frame);
				ifrCompras.setBounds(150, 50, 350, 350);
				frame.getContentPane().add(ifrCompras, 0);
				ifrCompras.setVisible(true);
			}
		});
		mitBuscar.add(mitBuscarCompra);
		frame.getContentPane().setLayout(null);
	}

}
