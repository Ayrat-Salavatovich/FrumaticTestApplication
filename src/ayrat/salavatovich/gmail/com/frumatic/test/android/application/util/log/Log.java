package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.log;

import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.Constants.*;
import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public class Log {

	static {
		VERBOSE = android.util.Log.VERBOSE;
		DEBUG = android.util.Log.DEBUG;
		INFO = android.util.Log.INFO;
		WARN = android.util.Log.WARN;
		ERROR = android.util.Log.ERROR;
		ASSERT = android.util.Log.ASSERT;
	}

	public static int v(String tag, String msg) {
		return android.util.Log.v(tag, msg);
	}

	public static int v(String tag, String msg, Throwable tr) {
		return android.util.Log.v(tag, msg, tr);
	}

	public static int d(String tag, String msg) {
		return android.util.Log.d(tag, msg);
	}

	public static int d(String tag, String msg, Throwable tr) {
		return android.util.Log.d(tag, msg, tr);
	}

	public static int i(String tag, String msg) {
		return android.util.Log.i(tag, msg);
	}

	public static int i(String tag, String msg, Throwable tr) {
		return android.util.Log.i(tag, msg, tr);
	}

	public static int w(String tag, String msg) {
		return android.util.Log.w(tag, msg);
	}

	public static int w(String tag, String msg, Throwable tr) {
		return android.util.Log.w(tag, msg, tr);
	}

	public static boolean isLoggable(String tag, int level) {
		return android.util.Log.isLoggable(tag, level);
	}

	public static int e(String tag, String msg) {
		return android.util.Log.e(tag, msg);
	}

	public static int e(String tag, String msg, Throwable tr) {
		return android.util.Log.e(tag, msg, tr);
	}

	public static int wtf(String tag, String msg) {
		if (hasFroyo()) {
			return android.util.Log.wtf(tag, msg, null);
		} else {
			return android.util.Log.e(tag, msg);
		}
	}

	public static int wtf(String tag, Throwable tr) {
		return wtf(tag, tr.getMessage(), tr);
	}

	public static int wtf(String tag, Object msg, Throwable tr) {
		if (hasFroyo()) {
			return android.util.Log.wtf(tag, EMPTY_STRING + msg, tr);
		} else {
			return android.util.Log.e(tag, EMPTY_STRING + msg, tr);
		}
	}

	public static final int VERBOSE;

	public static final int DEBUG;

	public static final int INFO;

	public static final int WARN;

	public static final int ERROR;

	public static final int ASSERT;

}
