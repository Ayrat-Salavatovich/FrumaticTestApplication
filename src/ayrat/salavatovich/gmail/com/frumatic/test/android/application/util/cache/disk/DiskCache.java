package ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.cache.disk;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.cache.naming.FileNameGenerator;
import static ayrat.salavatovich.gmail.com.frumatic.test.android.application.util.common.ConvenienceHelper.*;

public class DiskCache {

	public static final int BUFFER_SIZE = 32 * 1024;
	public static final Bitmap.CompressFormat COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
	public static final int COMPRESS_QUALITY = 75;
	private static final String TEMP_IMAGE_POSTFIX = ".tmp";
	protected File cacheDir;
	private final Object mCacheLock = new Object();
	private boolean mCacheStarting = true;

	public static class SingletonHolder {
		public static final DiskCache HOLDER_INSTANCE = new DiskCache();
	}

	public static DiskCache getInstance() {
		return SingletonHolder.HOLDER_INSTANCE;
	}

	private DiskCache() {
	}

	public void init(Context context) {
		init(getExternalCacheDir(context));
	}

	public void init(File cacheDir) {
		this.cacheDir = cacheDir;
		initCacheDir();
	}

	public static File getExternalCacheDir(Context context) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return new File(
					Environment.getExternalStorageDirectory().getPath(),
					"/data/" + context.getPackageName() + "/cache/");
		}

		return context.getCacheDir();
	}

	public void initCacheDir() {
		synchronized (mCacheLock) {
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			mCacheStarting = false;
			mCacheLock.notifyAll();
		}
	}

	public File getDirectory() {
		return cacheDir;
	}

	public Bitmap getBitmapFromCache(String name) {
		final String key = FileNameGenerator.generate(name);
		synchronized (mCacheLock) {
			while (mCacheStarting) {
				try {
					mCacheLock.wait();
				} catch (InterruptedException e) {
				}
			}
			File file = getFileByKey(key);
			if (getFileByKey(key).exists())
				return BitmapDecoder.decodeSampledBitmapFromFile(file.getPath());
			
			return null;
		}
	}

	public void addBitmapToCache(String name, Bitmap bitmap) {
		if (isNull(name) || isNull(bitmap)) {
			return;
		}

		synchronized (mCacheLock) {
			if (isNull(getDirectory())) {
				getDirectory().mkdirs();
			}

			try {
				final String key = FileNameGenerator.generate(name);
				save(key, bitmap);
			} catch (IOException e) {
				Log.e(TAG, e.toString());
			}
		}
	}

	private boolean save(String key, Bitmap bitmap) throws IOException {
		File imageFile = getFileByKey(key);
		File tmpFile = new File(imageFile.getAbsolutePath()
				+ TEMP_IMAGE_POSTFIX);
		OutputStream os = new BufferedOutputStream(
				new FileOutputStream(tmpFile), BUFFER_SIZE);
		boolean savedSuccessfully = false;
		try {
			savedSuccessfully = bitmap.compress(COMPRESS_FORMAT,
					COMPRESS_QUALITY, os);
		} finally {
			closeStream(os);
			if (savedSuccessfully && !tmpFile.renameTo(imageFile)) {
				savedSuccessfully = false;
			}
			if (!savedSuccessfully) {
				tmpFile.delete();
			}
		}
		if (notNull(bitmap) && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}

		return savedSuccessfully;
	}

	private File getFileByKey(String name) {
		return new File(cacheDir, name);
	}

	private static boolean deleteIfExists(File file) {
		if (file.exists()) {
			return file.delete();
		}

		return false;
	}

	public synchronized boolean remove(String name) {
		final String key = FileNameGenerator.generate(name);

		return deleteIfExists(getFileByKey(key));
	}

	public void clear() {
		synchronized (mCacheLock) {
			mCacheStarting = true;
			File[] files = cacheDir.listFiles();
			if (notNull(files)) {
				for (File file : files) {
					deleteIfExists(file);
				}
			}
		}
	}

	private static class BitmapDecoder {

		private BitmapDecoder() {
		}

		public static Bitmap decodeSampledBitmapFromFile(String fileName) {
			try {
				return BitmapFactory.decodeFile(fileName);
			} catch (OutOfMemoryError e) {
				Log.w(TAG, e.toString());
				return null;
			}
		}

	}

}
