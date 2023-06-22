package me.pepsi.xeros.raids.util;

public enum Range {
	
	RIGOUR("Rigour", -2),
	EAGLE_EYE("Eagle Eye", -1);
	
	private final String name;
	private final int selection;
	
	Range(final String name, final int selection) {
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