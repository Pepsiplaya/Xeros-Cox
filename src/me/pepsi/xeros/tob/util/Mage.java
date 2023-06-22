package me.pepsi.xeros.tob.util;

public enum Mage {
	
	AUGURY("Augury", -2),
	MYSTIC_MIGHT("Mystic Might", -1);
	
	private final String name;
	private final int selection;
	
	Mage(final String name, final int selection) {
		this.name = name;
		this.selection = selection;
	}
	public int getSelection() {
		return selection;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}
}