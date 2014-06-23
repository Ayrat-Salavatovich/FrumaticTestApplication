package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.log;

import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.Constants.*;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import android.text.TextUtils;

public final class LogTag extends Log {
	private LogTag() {

	}

	public static final String TAG;
	private static final Level CURRENT_LEVEL;

	public enum Level {
		error, warn, info, debug, trace
	}

	static {
		TAG = LogTag.class.getCanonicalName();
		CURRENT_LEVEL = BuildConfig.DEBUG ? Level.trace : Level.info;
	}

	public static void v(String msg) {
		if (isTrace())
			Log.v(TAG, getLocation() + msg);
	}

	public static void v(String msgFormat, Object... args) {
		if (isTrace())
			Log.v(TAG, getLocation() + String.format(msgFormat, args));
	}

	public static void v(String msg, Throwable tr) {
		if (isTrace())
			Log.v(TAG, getLocation() + msg, tr);
	}

	public static void v(String msgFormat, Throwable tr, Object... args) {
		if (isTrace())
			Log.v(TAG, getLocation() + String.format(msgFormat, args), tr);
	}

	public static void d(String msg) {
		if (isDebug())
			Log.d(TAG, getLocation() + msg);
	}

	public static void d(String msgFormat, Object... args) {
		if (isDebug())
			Log.d(TAG, getLocation() + String.format(msgFormat, args));
	}

	public static void d(String msg, Throwable tr) {
		if (isDebug())
			Log.d(TAG, getLocation() + msg, tr);
	}

	public static void d(String msgFormat, Throwable tr, Object... args) {
		if (isDebug())
			Log.d(TAG, getLocation() + String.format(msgFormat, args), tr);
	}

	public static void i(String msg) {
		if (isInfo())
			Log.i(TAG, getLocation() + msg);
	}

	public static void i(String msgFormat, Object... args) {
		if (isInfo())
			Log.i(TAG, getLocation() + String.format(msgFormat, args));
	}

	public static void i(String msg, Throwable tr) {
		if (isInfo())
			Log.i(TAG, getLocation() + msg, tr);
	}

	public static void i(String msgFormat, Throwable tr, Object... args) {
		if (isInfo())
			Log.i(TAG, getLocation() + String.format(msgFormat, args), tr);
	}

	public static void w(String msg) {
		if (isWarn())
			Log.w(TAG, getLocation() + msg);
	}

	public static void w(String msgFormat, Object... args) {
		if (isWarn())
			Log.w(TAG, getLocation() + String.format(msgFormat, args));
	}

	public static void w(String msg, Throwable tr) {
		if (isWarn())
			Log.w(TAG, getLocation() + msg, tr);
	}

	public static void w(String msgFormat, Throwable tr, Object... args) {
		if (isWarn())
			Log.w(TAG, getLocation() + String.format(msgFormat, args), tr);
	}

	public static boolean isLoggable(int level) {
		return Log.isLoggable(TAG, level);
	}

	public static void e(String msg) {
		if (isError())
			Log.e(TAG, getLocation() + msg);
	}

	public static void e(String msgFormat, Object... args) {
		if (isError())
			Log.e(TAG, getLocation() + String.format(msgFormat, args));
	}

	public static void e(String msg, Throwable tr) {
		if (isError())
			Log.e(TAG, getLocation() + msg, tr);
	}

	public static void e(String msgFormat, Throwable tr, Object... args) {
		if (isError())
			Log.e(TAG, getLocation() + String.format(msgFormat, args), tr);
	}

	public static void wtf(String msg) {
		Log.wtf(TAG, getLocation() + msg);
	}

	public static void wtf(String msgFormat, Object... args) {
		Log.wtf(TAG, getLocation() + String.format(msgFormat, args));
	}

	public static void wtf(Throwable tr) {
		wtf(TAG, getLocation() + stackToString(tr), tr);
	}

	public static void wtf(Object msg, Throwable tr) {
		Log.wtf(TAG, getLocation() + msg, tr);
	}

	public String getClassName() {
		return TAG;
	}

	public static boolean isError() {
		return isEnabled(Level.error);
	}

	public static boolean isWarn() {
		return isEnabled(Level.warn);
	}

	public static boolean isInfo() {
		return isEnabled(Level.info);
	}

	public static boolean isDebug() {
		return isEnabled(Level.debug);
	}

	public static boolean isTrace() {
		return isEnabled(Level.trace);
	}

	private static boolean isEnabled(Level level) {
		return CURRENT_LEVEL.compareTo(level) >= 0;
	}

	private static String stackToString(Throwable tr) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
				500);
		byteArrayOutputStream.toString();
		tr.printStackTrace(new PrintStream(byteArrayOutputStream));
		return byteArrayOutputStream.toString();
	}

	private static String getLocation() {
		final String className = Log.class.getName();
		final StackTraceElement[] traces = Thread.currentThread()
				.getStackTrace();
		boolean found = false;

		for (int i = 0; i < traces.length; i++) {
			StackTraceElement trace = traces[i];

			try {
				if (found) {
					if (!trace.getClassName().startsWith(className)) {
						Class<?> clazz = Class.forName(trace.getClassName());
						return "[" + getClassName(clazz) + ":"
								+ trace.getMethodName() + ":"
								+ trace.getLineNumber() + "]: ";
					}
				} else if (trace.getClassName().startsWith(className)) {
					found = true;
					continue;
				}
			} catch (ClassNotFoundException ignore) {
			}
		}

		return "[]: ";
	}

	private static String getClassName(Class<?> clazz) {
		if (clazz != null) {
			if (!TextUtils.isEmpty(clazz.getSimpleName()))
				return clazz.getSimpleName();

			return getClassName(clazz.getEnclosingClass());
		}

		return EMPTY_STRING;
	}

}