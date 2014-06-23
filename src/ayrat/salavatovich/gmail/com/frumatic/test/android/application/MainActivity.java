package ayrat.salavatovich.gmail.com.frumatic.test.android.application;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.cache.disk.DiskCache;
import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public class MainActivity extends FragmentActivity {

	public static final String TAG = MainActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (isNull(savedInstanceState))
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment(), "test").commit();

		prepare();
	}

	private void prepare() {
		DiskCache.getInstance().init(this);
	}
}
