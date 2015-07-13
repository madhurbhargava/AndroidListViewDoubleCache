package com.example.listviewprac;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Simple Single Level Cache
 * @author mbhargava
 *
 */
public class MultipleItemAdapter extends BaseAdapter {
	
	public static final int VIEW_IMAGE_LEFT_ALIGNED = 0;
	public static final int VIEW_IMAGE_RIGHT_ALIGNED = 1;
	private Context mCtx;
	String[] mData;
	String[] mUrls;
	
	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
	private static final String DISK_CACHE_SUBDIR = "thumbnails";
	
	private LruCache<String, Bitmap> mImagemap = null; 
	private DiskCache mDiskCache = null;
	private ExecutorService es = null;
	
	public MultipleItemAdapter(final Context ctx, String[] data, String[] urls)
	{
		mCtx = ctx;
		mData = data;
		mUrls = urls;
		mImagemap = new LruCache<String, Bitmap>(((int)(Runtime.getRuntime().maxMemory()/1024))/8);
		mDiskCache = new DiskCache(ctx);
		
		es = Executors.newFixedThreadPool(5);
	}
	
	

	/**
	 * Return get view count
	 */
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if(position % 2 != 1)
		{
			//this is even
			return VIEW_IMAGE_LEFT_ALIGNED;
		}
		else
		{
			//this is odd
			return VIEW_IMAGE_RIGHT_ALIGNED;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	class ViewHolder
	{
		ImageView img;
		TextView tv1;
		TextView tv2;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int type = getItemViewType(position);
		ViewHolder holder;
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			holder = new ViewHolder();
			if(type == VIEW_IMAGE_LEFT_ALIGNED)
			{
				
				convertView = inflater.inflate(R.layout.textview_listitem_imgleft, null);
			}
			else
			{
				convertView = inflater.inflate(R.layout.textview_listitem_imgright, null);
			}
			holder.img = (ImageView)convertView.findViewWithTag("img");
			holder.tv1 = (TextView)convertView.findViewWithTag("tv1");
			holder.tv2 = (TextView)convertView.findViewWithTag("tv2");
			convertView.setTag(holder);
			
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		
		holder.tv1.setText(mData[position]);
		if(type == VIEW_IMAGE_LEFT_ALIGNED)
		{
			holder.tv2.setText("Image Left Aligned");
		}
		else
		{
			holder.tv2.setText("Image Right Aligned");
		}
		
		
		holder.img.setTag(position);
		
		//if(mImagemap.get(mUrls[position]) != null)
		if(mDiskCache.doesExist(mUrls[position]))
		{
			holder.img.setImageBitmap(mDiskCache.getBitmap(mUrls[position]));//mImagemap.get(mUrls[position]));
		}
		else
		{
			holder.img.setImageResource(R.drawable.ic_launcher);
			
			//es.submit(task);
			BitmapDownloaderTask task = new BitmapDownloaderTask(position, holder.img, mUrls[position]);
			task.executeOnExecutor(es, null);
			//es.submit(task);
			//es.execute(task);
			//new BitmapDownloaderTask(position, holder.img, mUrls[position]).execute();
		}
		return convertView;
	}
	
	private class BitmapDownloaderTask extends AsyncTask<String,Void,Bitmap>
	{
		private int mPos;
		private ImageView mImg;
		private String mUrl;
		
		public BitmapDownloaderTask(int position, ImageView imgview, String url) {
			// TODO Auto-generated constructor stub
			mPos = position;
			mImg = imgview;
			mUrl = url;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			Bitmap icon = null;
			try {
				InputStream is = new URL(mUrl).openStream();
				icon = BitmapFactory.decodeStream(is);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return icon;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			/*if(mImagemap.get(mUrl) == null)
			{
				mImagemap.put(mUrl, result);
			}*/
			
			if(!mDiskCache.doesExist(mUrl))
			{
				mDiskCache.addBitmap(mUrl, result);
			}
			
			//this will ensure that the image is still vv visible
			if((Integer)mImg.getTag() == mPos)
			{
				mImg.setImageBitmap(result);
			}
		}
		
	}
	
	

}
