package mobile.zxjt.page.module.popup;

class PopupBean<T> {
	public final String repo;
	public final Class<? extends T> clazz;

	public PopupBean(String repo, Class<? extends T> clazz) {
		this.repo = repo;
		this.clazz = clazz;
	}

}
