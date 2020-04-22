package up.light;

public enum Platforms {
	ANDROID, IOS, WEB;

	public static Platforms fromString(String name) {
		if (name != null) {
			String upper = name.toUpperCase();
			for (Platforms p : Platforms.values()) {
				if (upper.equals(p.name())) {
					return p;
				}
			}
		}
		return null;
	}

}
