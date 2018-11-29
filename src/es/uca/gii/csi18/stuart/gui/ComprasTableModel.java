package es.uca.gii.csi18.stuart.gui;

import java.util.ArrayList;
import es.uca.gii.csi18.stuart.data.Compra;

public class ComprasTableModel extends javax.swing.table.AbstractTableModel{
	
	private ArrayList<Compra> _aData;
	
	public ComprasTableModel( ArrayList<Compra> aData) {
		_aData = aData;
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return _aData.size(); 
	}

	@Override
	public Object getValueAt(int iRow, int iCol) {
		Compra compra = _aData.get(iRow);
		switch(iCol) {
			case 0: return compra.getNombre();
			case 1: return compra.getImporte();
			case 2: return compra.getDescuento();
		}
		throw new IllegalStateException("Columna incorrecta.");
	}
	
	public Compra getData(int iRow) {
		return _aData.get(iRow);
	}

}
