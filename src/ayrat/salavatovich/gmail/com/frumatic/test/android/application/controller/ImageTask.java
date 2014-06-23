package ayrat.salavatovich.gmail.com.frumatic.test.android.application.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Stack;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.cache.disk.DiskCache;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.log.Log;
import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public class ImageTask {

	private ImageQueue imageQueue = new ImageQueue();
	private Thread imageLoaderThread = new Thread(new ImageQueueManager());

	public ImageTask() {
		imageLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
	}

	public void displayImage(String url, ImageView imageView) {
		Bitmap bitmap = null;
		DiskCache diskCache = DiskCache.getInstance();
		bitmap = diskCache.getBitmapFromCache(url);

		if (notNull(bitmap)) {
			imageView.setImageBitmap(bitmap);
		} else {
			queueImage(url, imageView);
		}
	}
	
	public void cleanQueue() {
		imageQueue.clean();
	}

	private void queueImage(String url, ImageView imageView) {
		imageQueue.clean(imageView);
		ImageRef image = new ImageRef(url, imageView);

		synchronized (imageQueue.imageRefs) {
			imageQueue.imageRefs.push(image);
			imageQueue.imageRefs.notifyAll();
		}

		if (imageLoaderThread.getState() == Thread.State.NEW) {
			imageLoaderThread.start();
		}
	}

	private Bitmap getBitmap(String url) {
		Bitmap bitmap = null;
		DiskCache diskCache = DiskCache.getInstance();
		try {
			String newUrl = getFinalRedirectedUrl(url);
			HttpURLConnection conn = (HttpURLConnection) new URL(newUrl)
					.openConnection();
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			Log.w(TAG, url);
			Log.w(TAG, newUrl);
			bitmap = BitmapFactory.decodeStream(conn.getInputStream());
			diskCache.addBitmapToCache(url, bitmap);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

		return bitmap;
	}

	static class ImageRef {
		public String url;
		public ImageView imageView;

		public ImageRef(String url, ImageView imageView) {
			this.url = url;
			this.imageView = imageView;
		}
	}

	private class ImageQueue {
		private Stack<ImageRef> imageRefs = new Stack<ImageRef>();

		public void clean(ImageView view) {

			for (int i = 0; i < imageRefs.size();) {
				if (imageRefs.get(i).imageView == view)
					imageRefs.remove(i);
				else
					i++;
			}
		}

		public void clean() {
			for (int i = 0; i < imageRefs.size();)
				imageRefs.remove(i);
		}

	}

	private class ImageQueueManager implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					if (imageQueue.imageRefs.size() == 0) {
						synchronized (imageQueue.imageRefs) {
							imageQueue.imageRefs.wait();
						}
					}

					if (imageQueue.imageRefs.size() != 0) {
						ImageRef image;

						synchronized (imageQueue.imageRefs) {
							image = imageQueue.imageRefs.pop();
						}

						Bitmap bmp = getBitmap(image.url);
						Object tag = image.imageView.getTag();

						if (notNull(tag) && ((String) tag).equals(image.url)) {
							BitmapDisplayer bmpDisplayer = new BitmapDisplayer(
									bmp, image.imageView);

							Activity a = (Activity) image.imageView
									.getContext();

							a.runOnUiThread(bmpDisplayer);
						}
					}

					if (Thread.interrupted())
						break;
				}
			} catch (InterruptedException e) {
			}
		}
	}

	private class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		ImageView imageView;

		public BitmapDisplayer(Bitmap b, ImageView i) {
			bitmap = b;
			imageView = i;
		}

		public void run() {
			if (notNull(bitmap))
				imageView.setImageBitmap(bitmap);
		}
	}

}
