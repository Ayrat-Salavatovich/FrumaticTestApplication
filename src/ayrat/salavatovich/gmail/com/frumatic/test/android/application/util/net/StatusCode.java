package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.net;

public enum StatusCode {

	OK(200), ERROR(-1);

	private int statusCode;

	private StatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public static StatusCode getStatusCode(int genericStatusCode) {
		if (StatusCode.OK.getValue() == genericStatusCode)
			return StatusCode.OK;

		return StatusCode.ERROR;
	}

	public int getValue() {
		return statusCode;
	}

}
