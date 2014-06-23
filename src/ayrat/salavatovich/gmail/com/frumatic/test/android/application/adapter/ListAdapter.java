package ayrat.salavatovich.gmail.com.frumatic.test.android.application.adapter;

import java.util.Collection;
import java.util.List;

import android.widget.BaseAdapter;

public abstract class ListAdapter<T> extends BaseAdapter {
	protected List<T> list;

	public ListAdapter(List<T> listObjects) {
		list = listObjects;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).hashCode();
	}

	public void clear() {
		list.clear();
	}

	public void addAll(Collection<? extends T> collection) {
		list.addAll(collection);
	}

}
