package ayrat.salavatovich.gmail.com.frumatic.test.android.application.model.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import ayrat.salavatovich.gmail.com.frumatic.test.android.application.model.PopularStoriesSerializer;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.model.PopularStory;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.log.Log;

public class JsonPopularStories implements PopularStoriesSerializer {

	public static final String TAG = JsonPopularStories.class.getSimpleName();

	private JsonPopularStories() {
	}

	public static class Singleton {
		public static final JsonPopularStories INSTANCE = new JsonPopularStories();
	}

	public static JsonPopularStories getInstance() {
		return Singleton.INSTANCE;
	}

	@Override
	public List<PopularStory> fromJson(JSONArray json) {
		List<PopularStory> stories = new ArrayList<PopularStory>();
		JsonPopularStory popularStory = JsonPopularStory.getInstance();
		for (int i = 0; i < json.length(); i++) {
			try {
				stories.add(popularStory.fromJson(json.getJSONObject(i)));
			} catch (JSONException e) {
				Log.w(TAG, e.toString());
			}
		}

		return stories;
	}

}
