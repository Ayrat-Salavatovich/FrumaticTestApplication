package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.net;

import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.Constants.*;

public class SimpleHttpDownloader {

	public static final String TAG = SimpleHttpDownloader.class.getSimpleName();

	private SimpleHttpDownloader() {
	}

	public static class Singleton {
		public static final SimpleHttpDownloader INSTANCE = new SimpleHttpDownloader();
	}

	public static SimpleHttpDownloader getInstance() {
		return Singleton.INSTANCE;
	}

	private DefaultHttpClient createClient() {
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		registry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(
				getConnManagerParams(), registry);
		return new DefaultHttpClient(connectionManager, getHttpParams());
	}

	public DefaultHttpClient getHttpClient() {
		DefaultHttpClient client = null;
		synchronized (this) {
			client = createClient();
		}

		return client;
	}

	private HttpParams getHttpParams() {
		HttpParams http = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(http, CONNECTION_TIMEOUT_MS);
		HttpConnectionParams.setSoTimeout(http, SOCKET_TIMEOUT_MS);

		return http;
	}

	protected HttpParams getConnManagerParams() {
		HttpParams connManagerParams = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(connManagerParams,
				MAX_TOTAL_CONNECTION);
		ConnManagerParams.setMaxConnectionsPerRoute(connManagerParams,
				new ConnPerRouteBean(MAX_CONNECTIONS_PER_ROUTE));

		return connManagerParams;
	}

}
