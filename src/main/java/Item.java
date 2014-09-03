public class Item implements Comparable<Item>{

	private final String name;
	private final int quantity;
	public Item(String name, int quantity)
	{
		this.name = name;
		this.quantity = quantity;

	}
	public int getQuantity() {
		return quantity;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other==null){
			return false;
		}
		if(this.getClass() != other.getClass()){
			return false;
		}
		Item that = (Item) other;
		if(this==that){
			return true;
		}
		return this.name.equals(that.name)&& this.quantity==that.quantity;
		
	}
	
	@Override
	public int hashCode(){
		int hash=1;
		hash= hash* 17 + name.hashCode();
		hash = hash *31 + quantity;
		return hash;
		
	}
	public int compareTo(Item other) {
		return this.name.compareToIgnoreCase(other.name);
	}
	
}
