package up.light.pojo;

import java.util.List;

public class Locator {
	private String context;
	private List<ByBean> bys;
	private boolean cache;

	public Locator(String context, List<ByBean> bys, boolean cache) {
		this.context = context;
		this.bys = bys;
		this.cache = cache;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public List<ByBean> getBys() {
		return bys;
	}

	public void setBys(List<ByBean> bys) {
		this.bys = bys;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	@Override
	public String toString() {
		return "Locator [context=" + context + ", bys=" + bys + ", cache=" + cache + "]";
	}

}
