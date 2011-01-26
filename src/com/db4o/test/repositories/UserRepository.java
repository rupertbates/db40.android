package com.db4o.test.repositories;

import com.db4o.test.model.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rupert bates
 * Date: 26/01/11
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
public class UserRepository extends Repository{
    public UserRepository(String dbFilePath){
        super(dbFilePath);
    }

    public void saveUser(User user) {
    	if(user.id == 0)
    		user.id = getNextId();
    	db.store(user);
	    db.commit();
    }
    public List<User> getUsers()
    {
        return db.query(User.class);
    }
}
