package up.light.pojo;

import java.util.concurrent.TimeUnit;

public class ImplicitlyWait {
	public final long timeout;
	public final TimeUnit unit;

	public ImplicitlyWait(long timeout, TimeUnit unit) {
		this.timeout = timeout;
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "ImplicitlyWait [timeout=" + timeout + ", unit=" + unit + "]";
	}

}
