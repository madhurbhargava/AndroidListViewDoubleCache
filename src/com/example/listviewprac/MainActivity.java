package com.example.listviewprac;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 
 * @author mbhargava
 *
 */
public class MainActivity extends Activity 
{
	private String[] mImageUrls={
			"http://farm8.staticflickr.com/7315/9046944633_881f24c4fa_s.jpg",
			"http://farm4.staticflickr.com/3777/9049174610_bf51be8a07_s.jpg",
			"http://farm8.staticflickr.com/7324/9046946887_d96a28376c_s.jpg",
			"http://farm3.staticflickr.com/2828/9046946983_923887b17d_s.jpg",
			"http://farm4.staticflickr.com/3810/9046947167_3a51fffa0b_s.jpg",
			"http://farm4.staticflickr.com/3773/9049175264_b0ea30fa75_s.jpg",
			"http://farm4.staticflickr.com/3781/9046945893_f27db35c7e_s.jpg",
			"http://farm6.staticflickr.com/5344/9049177018_4621cb63db_s.jpg",
			"http://farm8.staticflickr.com/7307/9046947621_67e0394f7b_s.jpg",
			"http://farm6.staticflickr.com/5457/9046948185_3be564ac10_s.jpg",
			"http://farm4.staticflickr.com/3752/9046946459_a41fbfe614_s.jpg",
			"http://farm8.staticflickr.com/7403/9046946715_85f13b91e5_s.jpg",
			"http://androidexample.com/media/webservice/LazyListView_images/image0.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image1.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image2.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image3.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image4.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image5.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image6.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image7.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image8.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image9.png",
            "http://androidexample.com/media/webservice/LazyListView_images/image10.png",
            "http://static1.1.sqspcdn.com/static/f/1097317/14874860/1319865121097/2.tablet-edit.jpg"
    };
	
	static final String[] values = new String[] { 
		"Anddroid", 
		"iPhone", 
		"WindowsMobile",
		"Blackberry", 
		"WebOS", 
		"Ubuntu", 
		"Windows7", 
		"Max OS X",
	    "Linux", 
	    "OS/2", 
	    "Ubuntu", 
	    "Windows7", 
	    "Max OS X", 
	    "Linux",    
	    "OS/2", 
	    "Ubuntu", 
	    "Windows7", 
	    "Max OS X", 
	    "Linux", 
	    "OS/2",
	    "Android", 
	    "iPhone", 
	    "WindowsMobile" };
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView)findViewById(R.id.list);
        
        //new Arr
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.textview_listitem_imgright, R.id.textleft1);
        //adapter.addAll(values);
        //adapter.addAll(values);
       MultipleItemAdapter adapter = new MultipleItemAdapter(this, values, mImageUrls);
        list.setAdapter(adapter);
    }
}
