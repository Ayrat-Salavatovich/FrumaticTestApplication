package ayrat.salavatovich.gmail.com.frumatic.test.android.application;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.adapter.PopularStoryAdapter;

public class MainFragment extends Fragment {

	private ListView result;
	private EditText query;
	private PopularStoryAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		query = (EditText) rootView.findViewById(R.id.editTextQuery);
		result = (ListView) rootView.findViewById(R.id.listViewResult);

		init();
		addListners();

		return rootView;
	}

	private void init() {
		adapter = new PopularStoryAdapter(getActivity().getLayoutInflater());
		result.setAdapter(adapter);
	}

	private void addListners() {
		query.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				search(getQuery());
				return false;
			}
		});
		result.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount - 5;
				
				if (loadMore)
					adapter.loadMore();
			}
		});
	}

	private String getQuery() {
		return query.getText().toString();
	}

	private void search(String query) {
		adapter.init(query);
	}
}
