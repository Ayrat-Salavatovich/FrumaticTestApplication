package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public class NetworkHelper {

	public static NetworkInfo getNetworkInfo(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return connectivityManager.getActiveNetworkInfo();
	}

	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo networkInfo = getNetworkInfo(context);

		return (notNull(networkInfo) && networkInfo.isAvailable());
	}

	public static boolean isConnected(Context context) {
		NetworkInfo networkInfo = getNetworkInfo(context);

		return notNull(networkInfo) && networkInfo.isConnected();
	}

}
