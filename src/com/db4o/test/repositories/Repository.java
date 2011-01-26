package com.db4o.test.repositories;

import android.content.Context;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;


/**
 * Created by IntelliJ IDEA.
 * User: rupert bates
 * Date: 26/01/11
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class Repository {
    protected static ObjectContainer db = null;

    public Repository(Context context)
    {
        db = Db4o.openFile( Db4o.newConfiguration(), db4oDBFullPath(context));
    }
    public String db4oDBFullPath(Context ctx) {
		return ctx.getDir("data", 0) + "/" + "android.db4o";
	}
}
