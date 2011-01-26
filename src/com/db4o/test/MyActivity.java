package com.db4o.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.db4o.R;

public class MyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        PassEntry p = new PassEntry();


    }
    public String db4oDBFullPath(Context ctx) {
		return ctx.getDir("data", 0) + "/" + "android.db4o";
	}

}
