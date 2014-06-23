package ayrat.salavatovich.gmail.com.frumatic.test.android.application.model;

import java.util.List;

import org.json.JSONArray;

public interface PopularStoriesSerializer {

	List<PopularStory> fromJson(JSONArray json);

}
