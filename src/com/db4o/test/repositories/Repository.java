package com.db4o.test.repositories;

import android.content.Context;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.test.IncrementedId;


/**
 * Created by IntelliJ IDEA.
 * User: rupert bates
 * Date: 26/01/11
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class Repository {
    protected static ObjectContainer db = null;

    public Repository(String dbFilePath)
    {
        db = Db4o.openFile( Db4o.newConfiguration(), dbFilePath);
    }
    public long getNextId() {
		ObjectSet<IncrementedId> result = db.queryByExample(IncrementedId.class);
		IncrementedId ii = null;
		long nextId;
		if(result.hasNext()){
			ii = (IncrementedId)result.next();
		}
		else{
			ii = new IncrementedId();
		}
		nextId = ii.getNextID();
		db.store(ii);
		return nextId;
	}
    public void close() {
    	if(db != null){
    		db.close();
    		db = null;
    	}
    }
}
