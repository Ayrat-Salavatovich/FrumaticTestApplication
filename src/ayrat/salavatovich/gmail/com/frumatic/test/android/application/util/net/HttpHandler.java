package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.net;

import org.apache.http.client.methods.HttpUriRequest;

public abstract class HttpHandler {

	public abstract HttpUriRequest getHttpRequestMethod();

	public abstract void onResponse(Object obj);

	public abstract void execute();

}
