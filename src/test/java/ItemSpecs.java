import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class ItemSpecs {

	@Test
	public void itemsAreNotEqual() throws Exception {
		Item pen = new Item("pen",10);
		assertFalse(pen.equals(new Object()));
		
	}
	
	@Test
	public void itemsAreEqual() throws Exception {
		Item bluePenOne = new Item("pen",10);
		Item bluePentwo = new Item("pen", 10);
		assertTrue(bluePenOne.equals(bluePentwo));
	}
	
	@Test
	public void itemIsNotEqualToNull(){
		Item pen = new Item("pen", 1);
		assertFalse(pen.equals(null));
	}
	
	@Test
	public void itemIsEqualToItself(){
		Item pen = new Item("pen", 1);
		assertTrue(pen.equals(pen));
	}
	
	@Test
	public void setContainsItem() throws Exception {
		Item bluePenOne = new Item("bluePen", 10);
		Item bluePenTwo = new Item("bluePenOne", 10);
		Set<Item> itemSet = new HashSet<Item>();
		itemSet.add(bluePenOne);
		itemSet.add(bluePenTwo);
		assertTrue(itemSet.contains(new Item("bluePen", 10)));
		assertTrue(itemSet.contains(bluePenOne));
	}
}
