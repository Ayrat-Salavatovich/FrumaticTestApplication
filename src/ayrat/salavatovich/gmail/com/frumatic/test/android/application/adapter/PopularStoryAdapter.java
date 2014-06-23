package ayrat.salavatovich.gmail.com.frumatic.test.android.application.adapter;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.R;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.controller.ImageTask;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.controller.PopularStoryTask;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.controller.api.ImageAvatarApi;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.controller.api.ImageAvatarApi.Image;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.controller.api.PopularStoryApi;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.model.PopularStory;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.net.HttpHandler;
import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public class PopularStoryAdapter extends ListAdapter<PopularStory> {

	public static final String TAG = PopularStoryAdapter.class.getSimpleName();

	private LayoutInflater mInflater;
	private String searchText;
	private ImageTask imageManager;
	private int limit = 8;
	private int offset = 0;
	private AtomicBoolean load = new AtomicBoolean(false);

	public void init(String searchRequest) {
		this.searchText = searchRequest;
		clear();
		limit = 8;
		offset = 0;
		loadMore();
		imageManager.cleanQueue();
	}

	public PopularStoryAdapter(LayoutInflater layoutInflater) {
		this(layoutInflater, new ArrayList<PopularStory>());
	}

	public PopularStoryAdapter(LayoutInflater layoutInflater,
			List<PopularStory> values) {
		super(values);
		this.mInflater = layoutInflater;
		imageManager = new ImageTask();
		loadMore();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view = convertView;
		if (isNull(view)) {
			view = mInflater.inflate(R.layout.popular_story, parent, false);
			holder = newViewHolder(view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		PopularStory story = getItem(position);
		if (notNull(story)) {
			holder.shortDescriptionTextView.setText(story
					.getShortDescription(20));
			holder.createDateTimeTextView.setText(conversionDateToString(story
					.getCreateDateTime()));
			String imageUrl = ImageAvatarApi.getImageUrl(new Image(story
					.getImageHashCode()));
			holder.titleTextView.setText(story.getTitle() + imageUrl);
			holder.coverImageView.setTag(imageUrl);
			imageManager.displayImage(imageUrl, holder.coverImageView);
		}

		return view;
	}

	public void loadMore() {
		if (load.get())
			return;

		load.set(true);
		new HttpHandler() {

			@Override
			public void onResponse(Object obj) {
				List<PopularStory> stories = (List<PopularStory>) obj;
				addAll(stories);
				offset += stories.size();
				notifyDataSetChanged();
				load.set(false);
			}

			@Override
			public HttpUriRequest getHttpRequestMethod() {
				HttpPost httpPost = null;
				StringEntity entity;
				try {
					httpPost = new HttpPost(PopularStoryTask.getUrl().toURI());
					entity = new StringEntity(PopularStoryApi
							.findPopularStoriesByQuery(searchText, limit,
									offset).toString(), "UTF-8");

					entity.setContentType("application/json");
					httpPost.setEntity(entity);
				} catch (URISyntaxException e) {
					Log.e(TAG, e.toString());
				} catch (UnsupportedEncodingException e) {
					Log.e(TAG, e.toString());
				}

				return httpPost;
			}

			@Override
			public void execute() {
				new PopularStoryTask(this).execute();
			}

		}.execute();
	}

	private ViewHolder newViewHolder(final View rootView) {
		if (isNull(rootView)) {
			throw new IllegalArgumentException("Error: rootView is null");
		}

		ImageView coverImageView = (ImageView) rootView
				.findViewById(R.id.imageViewCover);
		TextView titleTextView = (TextView) rootView
				.findViewById(R.id.textViewTitle);
		TextView shortDescriptionTextView = (TextView) rootView
				.findViewById(R.id.textViewShortDescription);
		TextView createDateTimeTextView = (TextView) rootView
				.findViewById(R.id.textViewCreateDateTime);
		ViewHolder holder = new ViewHolder(coverImageView, titleTextView,
				shortDescriptionTextView, createDateTimeTextView);

		return holder;
	}

	static class ViewHolder {
		ImageView coverImageView;
		TextView titleTextView;
		TextView shortDescriptionTextView;
		TextView createDateTimeTextView;

		public ViewHolder(ImageView coverImageView, TextView titleTextView,
				TextView shortDescriptionTextView,
				TextView createDateTimeTextView) {
			this.coverImageView = coverImageView;
			this.titleTextView = titleTextView;
			this.shortDescriptionTextView = shortDescriptionTextView;
			this.createDateTimeTextView = createDateTimeTextView;
		}
	}

}
