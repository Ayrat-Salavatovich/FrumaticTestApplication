package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common;

import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.Constants.EMPTY_STRING;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.Build;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.log.Log;

public final class ConvenienceHelper {

	public static final String TAG = ConvenienceHelper.class.getSimpleName();

	private ConvenienceHelper() {
	}

	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}

		return false;
	}

	public static boolean notNull(Object obj) {
		if (obj == null) {
			return false;
		}

		return true;
	}

	public static void closeStream(Closeable closeable) {
		if (isNull(closeable)) {
			return;
		}

		try {
			closeable.close();
		} catch (IOException ignore) {
		}
	}

	public static Date conversionStringToDate(String dateString)
			throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
				Locale.ENGLISH).parse(dateString);
	}

	public static String conversionDateToString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date);
	}

	public static boolean hasFroyo() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static JSONObject convertInputStreamToJSONObject(
			final InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = EMPTY_STRING;
		StringBuilder in = new StringBuilder();
		while (notNull(line = bufferedReader.readLine()))
			in.append(line);

		JSONTokener tokener = new JSONTokener(in.toString());
		JSONObject json = null;
		try {
			json = new JSONObject(tokener);
		} catch (JSONException e) {
			Log.v(TAG, e.toString());
		}

		return json;
	}

	public static String getFinalRedirectedUrl(String url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		conn.setInstanceFollowRedirects(false);
		conn.connect();

		int status = conn.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_TEMP
					|| status == HttpURLConnection.HTTP_MOVED_PERM
					|| status == HttpURLConnection.HTTP_SEE_OTHER)
				return getFinalRedirectedUrl(conn.getHeaderField("Location"));
		}

		return url;
	}

}
