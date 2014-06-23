package ayrat.salavatovich.gmail.com.frumatic.test.android.application.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ayrat.salavatovich.gmail.com.frumatic.test.android.application.model.PopularStory;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.controller.api.PopularStoryApi;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.model.json.JsonPopularStories;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.log.Log;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.net.HttpHandler;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.net.JsonHttpTask;

public class PopularStoryTask extends JsonHttpTask<List<PopularStory>> {

	public static final String TAG = PopularStoryTask.class.getSimpleName();

	public PopularStoryTask(HttpHandler handler) {
		this.httpHandler = handler;
	}

	@Override
	protected List<PopularStory> getResultFromJson(JSONObject json) {
		List<PopularStory> popularStories = new ArrayList<PopularStory>();
		JSONObject jsonResult;
		JSONArray jsonStories;
		try {
			jsonResult = json.getJSONObject("result");
			jsonStories = jsonResult.getJSONArray("stories");
			popularStories = JsonPopularStories.getInstance().fromJson(
					jsonStories);
		} catch (JSONException e) {
			Log.v(TAG, e.toString());
		}
		return popularStories;
	}

	public static URL getUrl() {
		URL url = null;
		try {
			url = PopularStoryApi.getApiFindPopularStoriesUrl();
		} catch (MalformedURLException e) {
			Log.v(TAG, e.toString());
		}

		return url;
	}

}
