package mobile.zxjt.page.module.popup;

/**
 * Endless types of Popup! Why can't the developer reuse one?
 */
public enum PopupType {

	STYLE_1 {
		// 股票、基金、融资融券
		@Override
		public PopupBean<IAlert> getAlert() {
			return new PopupBean<>("Alert", Alert.class);
		}

		@Override
		public PopupBean<IConfirm> getConfirm() {
			return new PopupBean<>("Confirm", Confirm.class);
		}
	},
	STYLE_2 {
		// 港股通、LOF
		@Override
		public PopupBean<IAlert> getAlert() {
			return new PopupBean<IAlert>("Alert2", Alert2.class);
		}

		@Override
		public PopupBean<IConfirm> getConfirm() {
			return new PopupBean<>("Confirm2", Confirm.class);
		}
	},
	STYLE_3 {
		// ETF、股转
		@Override
		public PopupBean<IAlert> getAlert() {
			return new PopupBean<IAlert>("Alert3", Alert2.class);
		}

		@Override
		public PopupBean<IConfirm> getConfirm() {
			return new PopupBean<>("Confirm2", Confirm.class);
		}
	},
	STYLE_JJDT {
		// 基金定投
		@Override
		public PopupBean<IAlert> getAlert() {
			return new PopupBean<IAlert>("Alert", Alert.class);
		}

		@Override
		public PopupBean<IConfirm> getConfirm() {
			return new PopupBean<>("Confirm_JJDT", Confirm.class);
		}
	},
	STYLE_YJGJ {
		// 一键归集
		@Override
		public PopupBean<IAlert> getAlert() {
			return new PopupBean<IAlert>("Alert", Alert.class);
		}

		@Override
		public PopupBean<IConfirm> getConfirm() {
			return new PopupBean<>("Confirm_YJGJ", Confirm.class);
		}
	};

	public abstract PopupBean<IAlert> getAlert();

	public abstract PopupBean<IConfirm> getConfirm();

}
