package me.pepsi.xeros.woodcutter;

public enum Trees {
	
	NONE("", -1),
	TREE("Tree", 1276),
	OAK("Oak", 10820),
	WILLOW("Willow", 10833),
	MAPLE("Maple", 10832),
	YEW("Yew", 10822),
	MAGIC("Magic", 10834);
	
	private final String name;
	private final int[] objectId;
	
	Trees(final String name, final int... objectId) {
		this.name = name;
		this.objectId = objectId;
	}

	public int[] getObjectId() {
		return objectId;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}
	

}