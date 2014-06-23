package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public class ExecutorPool {

	static private ExecutorService executorService = Executors
			.newCachedThreadPool();

	private ExecutorPool() {
	}

	public static ExecutorService getExecutorService() {
		if (isNull(executorService)) {
			executorService = Executors.newCachedThreadPool();
		}

		return executorService;
	}

	public static void closeQuietly() {
		if (notNull(executorService)) {
			executorService.shutdown();
		}
	}

}
