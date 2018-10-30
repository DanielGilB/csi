package es.uca.gii.csi18.stuart.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi18.stuart.data.Compra;
import es.uca.gii.csi18.stuart.data.Data;

class CompraTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception { Data.LoadDriver(); }
	
	
	@Test
	void testConstructor() throws Exception {
		Compra compra = new Compra(1);
		assertEquals(compra.getId(), 1);
		assertEquals(compra.getNombre(), "compra1");
		assertEquals(compra.getImporte(), 12.99);
	}

	@Test
	void testCreate() throws Exception {
		Compra compra = Compra.Create("compraTest", 9.99);
		assertEquals(compra.getId(), 8);
		assertEquals(compra.getNombre(), "compraTest");
		assertEquals(compra.getImporte(), 9.99);
	}

}
