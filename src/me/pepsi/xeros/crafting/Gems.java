package me.pepsi.xeros.crafting;

public enum Gems {
	
	NONE("None", -1),
	PROGRESSIVE("Progressive", -2),
	OPAL("Opal", 1625),
	JADE("Jade", 1627),
	SAPPHIRE("Sapphire", 1623),
	EMERALD("Emerald", 1621),
	RUBY("Ruby", 1619),
	DIAMOND("Diamond", 1617),
	DRAGONSTONE("Dragonstone", 1631),
	ONYX("Onyx", 6573);
	
	private final String name;
	private final int itemId;
	
	Gems(final String name, final int itemId) {
		this.name = name;
		this.itemId = itemId;
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