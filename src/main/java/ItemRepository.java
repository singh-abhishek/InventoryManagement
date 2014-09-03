import java.util.List;


public interface ItemRepository {

	public abstract boolean add(Item item);

	public abstract List<Item> findAll();

}
