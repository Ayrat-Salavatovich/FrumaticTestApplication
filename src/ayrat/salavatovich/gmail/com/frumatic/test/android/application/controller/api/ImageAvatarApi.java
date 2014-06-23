package ayrat.salavatovich.gmail.com.frumatic.test.android.application.controller.api;

public class ImageAvatarApi {

	public static final String TAG = ImageAvatarApi.class.getSimpleName();

	public static String getImageUrl(Image image) {
		return "http://test.selfishbeta.com/file/" + image.getId() + "?width="
				+ image.getSize().width + "&height="
				+ image.getSize().height + "&crop=true";
	}

	public static class Image {
		private String id;
		private Size size;

		public Image(String id) {
			this.id = id;
			size = new Size();
		}

		public Image(String id, int width, int height) {
			this.id = id;
			size = new Size(width, height);
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Size getSize() {
			return size;
		}

		public void setSize(int width, int height) {
			size.width = width;
			size.height = height;
		}

		private static class Size {
			int width, height;

			public Size() {
				setDefaultSize();
			}

			public Size(int width, int height) {
				this.width = width;
				this.height = height;
			}

			public void setDefaultSize() {
				width = 72;
				height = 72;
			}

		}
	}

}
