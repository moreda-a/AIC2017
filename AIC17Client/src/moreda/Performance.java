package moreda;

public abstract class Performance {
	private String name;
	protected Plat plat;
	protected Plat lastPlat;

	public Performance(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public long evaluate(Plat lastPlat, Plat plat) {
		this.plat = plat;
		return evaluate();
	}

	public abstract long evaluate();
}
