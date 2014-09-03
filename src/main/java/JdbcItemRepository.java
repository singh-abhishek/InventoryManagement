import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


//use prepared statement wherever possible(improves performance)
public class JdbcItemRepository implements ItemRepository{

	private Connection connection;

	public JdbcItemRepository(Connection connection) {
		if(connection==null)
		{
			throw new IllegalArgumentException("Empty Connection!");
		}
		this.connection = connection;
	}

	public List<Item> findAll() {
		List<Item> items = new ArrayList<Item>();
		ResultSet rs;
		try {
			rs = connection.createStatement().executeQuery(
					"select name, quantity FROM item");
			while (rs.next()) {
				Item item = new Item(rs.getString(1), rs.getInt(2));
				items.add(item);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return items;
	}

	public boolean add(Item item) {
		if (alreadyExists(item))
			return false;
		String insertItemQuery = String.format(
				"insert into item (name,quantity) values(\'%s\', %d)",
				item.getName(), item.getQuantity());
		try {
			connection.createStatement().execute(insertItemQuery);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	private boolean alreadyExists(Item item) {
		String insertItemQuery = String.format(
				"select * from item where name=\'%s\'", item.getName());
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(insertItemQuery);
			return rs.next();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
