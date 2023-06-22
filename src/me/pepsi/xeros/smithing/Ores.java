package me.pepsi.xeros.smithing;

public enum Ores {
	
	NONE("None", -1, -1, -1, -1, -1),
	BRONZE("Bronze", 436, 438, 14, 14, 2414),
	IRON("Iron", 440, -1, 123, -1, 3996),
	STEEL("Steel", 440, 453, 14, 14, 3996),
	GOLD("Gold", 444, -1, 123, -1, 3996),
	MITHRIL("Mithril", 447, 453, 14, 14, 4158),
	ADAMANT("Adamant", 449, 453, 14, 14, 7442),
	RUNE("Rune", 451, 453, 14, 14, 7447);
	
	private final String name;
	private final int itemId;
	private final int itemId2;
	private final int quantity;
	private final int quantity2;
	private final int data;
	
	Ores(final String name, final int itemId, final int itemId2, final int quantity, final int quantity2, final int data) {
		this.name = name;
		this.itemId = itemId;
		this.itemId2 = itemId2;
		this.quantity = quantity;
		this.quantity2 = quantity2;
		this.data = data;
	}
	
	public int getData() {
		return data;
	}
	
	public int getQuantity2() {
		return quantity2;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public int getItemId2() {
		return itemId2;
	}

	public int getItemId() {
		return itemId;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}
}