package ayrat.salavatovich.gmail.com.frumatic.test.android.application.model.json;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import ayrat.salavatovich.gmail.com.frumatic.test.android.application.model.PopularStory;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.model.PopularStorySerializer;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.log.Log;
import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public class JsonPopularStory implements PopularStorySerializer {

	public static final String TAG = JsonPopularStory.class.getSimpleName();

	private JsonPopularStory() {

	}

	public static class Singleton {
		public static final JsonPopularStory INSTANCE = new JsonPopularStory();
	}

	public static JsonPopularStory getInstance() {
		return Singleton.INSTANCE;
	}

	@Override
	public PopularStory fromJson(JSONObject json) {
		PopularStory story = new PopularStory();
		try {
			story.setTitle(json.getString("title"));
			story.setDescription(json.getString("description"));
			story.setCreateDateTime(conversionStringToDate(json.getJSONObject(
					"creationDate").getString("$date")));
			story.setImageHashCode(json.getJSONObject("coverId").getString(
					"$oid"));
			story.setId(json.getJSONObject("_id").getString("$oid"));
		} catch (JSONException e) {
			Log.w(TAG, e.toString());
		} catch (ParseException e) {
			Log.w(TAG, e.toString());
		}

		return story;
	}

}
