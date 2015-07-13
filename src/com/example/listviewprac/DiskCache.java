package com.example.listviewprac;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

public class DiskCache 
{
	//private DiskLruCache mDiskCache;
	//rplace by lrucache
	private static File cacheDir = null;
	
	public DiskCache(Context ctx)
	{
		cacheDir = ctx.getCacheDir();
		try {
			cacheDir.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void addBitmap(String key, Bitmap bmp)
	{
		FileOutputStream os;
		try {
			File file = new File(cacheDir, getFileName(key));
			file.createNewFile();
			os = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Bitmap getBitmap(String key)
	{
		File file = new File(cacheDir, getFileName(key));
		try {
			InputStream is = new FileInputStream(file.getAbsolutePath());
			return BitmapFactory.decodeStream(is);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean doesExist(String key)
	{
		File file = new File(cacheDir, getFileName(key));
		if(file.exists())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private String getFileName(String key)
	{
		return key.subSequence(key.lastIndexOf("/")+1, key.length()-1).toString();
	}
	
	
	
  
}
