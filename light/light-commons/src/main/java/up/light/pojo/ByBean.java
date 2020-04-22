package up.light.pojo;

public class ByBean {
	public final String by;
	public final String value;

	public ByBean(String by, String value) {
		this.by = by;
		this.value = value;
	}

	@Override
	public String toString() {
		return "ByBean(" + by + ", " + value + ")";
	}

}
