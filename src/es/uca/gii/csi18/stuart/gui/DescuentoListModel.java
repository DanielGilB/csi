package es.uca.gii.csi18.stuart.gui;

import java.util.ArrayList;
import java.util.List;

import es.uca.gii.csi18.stuart.data.Compra;
import es.uca.gii.csi18.stuart.data.Descuento;

public class DescuentoListModel extends javax.swing.AbstractListModel<Descuento>
	implements javax.swing.ComboBoxModel<Descuento> {
	
	private List<Descuento> _aData;
	private Object _oSelectedItem = null;
	
	DescuentoListModel(List<Descuento> lDescuento){
		_aData = lDescuento;
	}
	
	@Override
	public Object getSelectedItem() {
		return _oSelectedItem;
	}
	
	@Override
	public void setSelectedItem(Object oSelectedItem) {
		_oSelectedItem = oSelectedItem;
	}
	
	@Override
	public Descuento getElementAt(int iIndex) {
		return _aData.get(iIndex);
	}

	@Override
	public int getSize() {
		return _aData.size();
	}



}
