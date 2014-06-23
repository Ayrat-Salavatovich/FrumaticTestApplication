package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import android.app.PendingIntent.CanceledException;
import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public abstract class JsonHttpTask<T> extends HttpTask<T> {

	public static final String TAG = JsonHttpTask.class.getSimpleName();

	@Override
	protected T getResult(HttpResponse httpResponse)
			throws MalformedURLException, ClientProtocolException, IOException,
			CanceledException {
		InputStream inputStream = httpResponse.getEntity().getContent();
		Header contentEncoding = httpResponse
				.getFirstHeader("Content-Encoding");
		if (notNull(contentEncoding)
				&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
			inputStream = new GZIPInputStream(inputStream);
		}
		return getResultFromJson(convertInputStreamToJSONObject(inputStream));
	}

	protected abstract T getResultFromJson(JSONObject json);

}
