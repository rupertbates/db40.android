/**
 * 
 */
package com.db4o.test;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.Configuration;
import com.db4o.query.Predicate;

/**
 * @author German Viscuso
 *
 */
public class DBHelper {
	
	//public static long latestId = 0;
	private static ObjectContainer oc = null;
	private Context context;

	private static boolean needsPrePopulation=false;
	/**
     * 
     * @param ctx
     */
    public DBHelper(Context ctx) {
    	context = ctx;
    }
    
    private ObjectContainer db(){
    	try {
    		if(oc == null || oc.ext().isClosed())
    			oc = Db4o.openFile(dbConfig(), db4oDBFullPath(context));
    		return oc;
    	} catch (Exception e) {
        	Log.e(DBHelper.class.getName(), e.toString());
        	return null;
        }
    }
    
    private Configuration dbConfig(){
    	Configuration c = Db4o.newConfiguration();
    	//Index entries by Id
    	//c.objectClass(PassEntry.class).objectField("id").indexed(true);
    	//Configure proper activation + update depth
    	//TODO
    	return c;
    }
	
	public String db4oDBFullPath(Context ctx) {
		return ctx.getDir("data", 0) + "/" + "android.db4o";
	}
	
	/**
     * Close database connection
     */
    public void close() {
    	if(oc != null){
    		oc.close();
    		oc = null;
    	}
    }
	
	/**
     * 
     * @param entry
     */
    public void savePassword(PassEntry entry) {
    	if(entry.id == 0)
    		entry.id = getNextId();
    	db().store(entry);
	    db().commit();
    }
    
    /**
     * 
     * @param entry
     */
    public long saveCategory(CategoryEntry entry) {
    	if(entry.id == 0)
    		entry.id = getNextId();
    	db().store(entry);
	    db().commit();
	    return entry.id;
    }
    
    /**
     * Fetch PassEntry by Id using db4o's Query By Example (QBE)
     * @param Id
     */
    private ObjectSet<PassEntry> fetchPasswordsById(long Id) {
    	PassEntry pe = new PassEntry();
    	pe.id = Id;
    	return db().queryByExample(pe);
    }
    
    public int countPasswords(long Id){
    	ObjectSet<PassEntry> passwords = fetchPasswordsById(Id);
    	return passwords == null ? 0 : passwords.size();
    }
    
    /**
     * Fetch CategoryEntries by Id using db4o's Query By Example (QBE)
     * @param Id
     */
    private ObjectSet<CategoryEntry> fetchCategoriesById(long Id) {
    	CategoryEntry ct = new CategoryEntry();
    	ct.id = Id;
    	return db().queryByExample(ct);
    }
    
    
    public List<CategoryEntry> fetchAllCategoryRows(){
    	return db().query(CategoryEntry.class);
    }

    public void commit(){
    	db().commit();
    }
    
    public void rollback(){
    	db().rollback();
    }
    
    /**
     * Fetch CategoryEntry by Id using db4o's Query By Example (QBE)
     * @param Id
     */
    public CategoryEntry fetchCategoryById(long Id) {
    	ObjectSet<CategoryEntry> result = fetchCategoriesById(Id);
        if (result.hasNext())
        	return (CategoryEntry)result.next();
        else
        	return null;
    }
    
    public void deleteDatabase(){
    	close();
    	new File(db4oDBFullPath(context)).delete();
    }
    
    /**
     * 
     * @param Id
     */
    public void deletePassword(long Id) {
        //Search by Id
    	ObjectSet<PassEntry> result = fetchPasswordsById(Id);
        //Delete object
    	while(result.hasNext())
    		db().delete((PassEntry)result.next());
    	db().commit();
    }
    
    /**
     * 
     * @param Id
     */
    public void deleteCategory(long Id) {
        //Search by Id
    	ObjectSet<CategoryEntry> result = fetchCategoriesById(Id);
        //Delete object
    	while(result.hasNext())
    		db().delete((CategoryEntry)result.next());
    	db().commit();
    }
    
    /**
     * 
     * @return
     */
    public List<PassEntry> fetchAllPassRows(){
    	return db().query(PassEntry.class);
    }
    
    /**
     * 
     * @return
     */
    public List<PassEntry> fetchPasswordsForCategory(final CategoryEntry category){
    	List<PassEntry> result = db().query(new Predicate<PassEntry>(){
    		public boolean match(PassEntry entry){
    			if (entry.category.equals(category)){
    				return true;
    			}
    			return false;
    		}
    	});
    	return result;
    }
    
    /**
     * 
     * @param Id
     * @return
     */
    public PassEntry fetchPasswordById(long Id) {
        ObjectSet<PassEntry> result = fetchPasswordsById(Id);
        if (result.hasNext())
        	return (PassEntry)result.next();
        else
        	return null;
    }
    
    /**
     * 
     * @return
     */
    /*public String fetchMasterKey() {
    	ObjectSet<PBEKey> result = db().queryByExample(new PBEKey());
    	if(result.hasNext()){
    		String key = ((PBEKey)result.next()).getKey();
    		if(key != null)
    			return key;
    	}
    	return "";
    }
    
    *//**
     * 
     * @param PBEKey
     *//*
    public void storeMasterKey(String pbekey) {
    	ObjectSet<PBEKey> result = db().queryByExample(new PBEKey());
    	PBEKey key;
    	if(result.hasNext()){
    		key = (PBEKey)result.next();
    		key.setKey(pbekey);
    	}
    	else{
    		key = new PBEKey(pbekey);
    	}
    	db().store(key);
    	db().commit();
    }*/
    
    public int passwordCount(){
    	List<PassEntry> passwords = fetchAllPassRows();
    	return passwords == null ? 0: passwords.size();
    }
    
    public long getNextId() {
		ObjectSet<IncrementedId> result = db().queryByExample(IncrementedId.class);
		IncrementedId ii = null;
		long nextId;
		if(result.hasNext()){
			ii = (IncrementedId)result.next();
		}
		else{
			ii = new IncrementedId();	
		}
		nextId = ii.getNextID();
		db().store(ii);
		return nextId;
	}
    
    public String fullDesc(){
    	String s = "";
    	List<PassEntry> passwords = fetchAllPassRows();
    	for (PassEntry pe : passwords){
    		s += Long.toString(pe.id) + "-" +pe.description + "\n";
    	}
    	return s;
    }

    public void backup(String path){
    	db().ext().backup(path);
    }
    
    public void restore(String path){
    	deleteDatabase();
    	new File(path).renameTo(new File(db4oDBFullPath(context)));
    	new File(path).delete();
    }
    
    public boolean getPrePopulate()
    {
    	return needsPrePopulation;
    }

    public void clearPrePopulate()
    {
    	needsPrePopulation=false;
    }

}



