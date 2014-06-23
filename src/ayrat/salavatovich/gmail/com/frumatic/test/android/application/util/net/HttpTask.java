package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.PendingIntent.CanceledException;
import android.os.AsyncTask;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.model.PopularStory;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.log.Log;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.pool.ExecutorPool;
import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public abstract class HttpTask<T> extends AsyncTask<Void, Void, T> {

	public static final String TAG = HttpTask.class.getSimpleName();
	protected HttpHandler httpHandler;

	public DefaultHttpClient getHttpClient() {
		return SimpleHttpDownloader.getInstance().getHttpClient();
	}

	protected Executor getExecutor() {
		return ExecutorPool.getExecutorService();
	}

	@Override
	protected T doInBackground(Void... params) {
		T result = null;
		DefaultHttpClient httpClient = getHttpClient();
		try {
			HttpResponse httpResponse = doResponse(httpClient);
			if (notNull(httpResponse)) {
				StatusLine statusLine = httpResponse.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (StatusCode.getStatusCode(statusCode) == StatusCode.OK) {
					result = getResult(httpResponse);
				}
			}
		} catch (MalformedURLException e) {
			Log.v(TAG, e.toString());
		} catch (ClientProtocolException e) {
			Log.v(TAG, e.toString());
		} catch (CanceledException e) {
			Log.v(TAG, e.toString());
		} catch (IOException e) {
			Log.v(TAG, e.toString());
		} finally {
			if (notNull(httpClient)) {
				httpClient.getConnectionManager().shutdown();
			}
		}

		return result;
	}

	@Override
	protected void onPostExecute(T result) {
		httpHandler.onResponse(result);
	}

	protected abstract T getResult(HttpResponse httpResponse)
			throws MalformedURLException, ClientProtocolException, IOException,
			CanceledException;

	public AsyncTask<Void, Void, T> executeOnExecutor() {
		return executeOnExecutor(getExecutor());
	}

	protected HttpResponse doResponse(HttpClient httpClient) {
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpHandler.getHttpRequestMethod());
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

		return response;
	}

}
