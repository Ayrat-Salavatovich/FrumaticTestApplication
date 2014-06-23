package ayrat.salavatovich.gmail.com.frumatic.test.android.application.controller.api;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public class PopularStoryApi {

	public static final String TAG = PopularStoryApi.class.getSimpleName();

	public static URL getApiFindPopularStoriesUrl()
			throws MalformedURLException {
		return new URL("http://test.selfishbeta.com/api/FindPopularStories");
	}

	public static JSONObject findPopularStories(int limit, int offset) {
		JSONObject request = new JSONObject();
		JSONObject params = new JSONObject();
		JSONObject sort = new JSONObject();
		try {
			sort.put("createTime.milliseconds", -1);
			params.put("limit", limit);
			params.put("offset", offset);
			params.put("sort", sort);

			request.put("params", params);
		} catch (JSONException e) {
			Log.e(TAG, e.toString());
		}

		return request;
	}

	public static JSONObject findPopularStoriesByQuery(String query, int limit,
			int offset) {
		if (isNull(query) || query.isEmpty())
			return findPopularStories(limit, offset);

		JSONObject request = new JSONObject();
		JSONObject params = new JSONObject();
		JSONObject sort = new JSONObject();
		JSONArray from = new JSONArray();
		try {
			sort.put("createTime.milliseconds", -1);
			params.put("limit", limit);
			params.put("offset", offset);
			params.put("query", "title:(\"" + query + "\")");
			params.put("sort", sort);
			request.put("params", params);

			from.put("Story");
			request.put("from", from);
		} catch (JSONException e) {
			Log.e(TAG, e.toString());
		}

		return request;
	}

}
