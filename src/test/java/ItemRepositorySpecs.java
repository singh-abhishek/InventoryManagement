import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ItemRepositorySpecs {

	private final int NO_ITEMS = 0;

	private static ItemRepositorySupport repoSupport = new ItemRepositorySupport();
	private static ItemRepository repository;

	@BeforeClass
	public static void createConnection() throws SQLException,
			ClassNotFoundException {
		repository = repoSupport.createTableAndRepository();
	}

	@After
	public void clean() throws SQLException {
		repoSupport.cleanTable();
	}

	@AfterClass
	public static void dropTable() throws SQLException {
		repoSupport.dropTable();
	}

	@Test
	public void emptyRepositoryReturnsNothing() throws SQLException {

		List<Item> items = repository.findAll();
		assertEquals(NO_ITEMS, items.size());
	}

	@Test
	public void repositoryContainingOneItemReturnsOne() throws SQLException,
			ClassNotFoundException {
		Item pen = new Item("pen", 1);
		repoSupport.insert(pen);
		List<Item> items = repository.findAll();
		assertEquals(1, items.size());
		assertEquals(pen, items.get(0));
	}

	@Test
	public void addsUniqueItemOnly() throws Exception {
		Item pen = new Item("pen", 1);
		assertTrue(repository.add(pen));
	}

	@Test
	public void doesNotAllowItemWithSameName() throws Exception {
		Item pen = new Item("pen", 1);
		repoSupport.insert(pen);
		assertFalse(repository.add(pen));
	}

	@Test
	public void repositoryCannotWorkWithoutAConnection() throws Exception {
		try {
			new JdbcItemRepository(null);
			fail("Repository got a connection, when not expected.");
		} catch (Exception e) {
			assertEquals("Empty Connection!", e.getMessage());
		}
	}
}
