package me.pepsi.xeros.skeleton;

public enum Prayer {
	
	EAGLE_EYE("Eagle Eye", -1),
	RIGOUR("Rigour", -2),
	PIETY("Piety", -3);
	
	private final String name;
	private final int selection;
	
	Prayer(final String name, final int selection) {
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