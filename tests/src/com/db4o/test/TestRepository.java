package com.db4o.test;


import com.db4o.test.model.User;
import com.db4o.test.repositories.UserRepository;

import org.junit.*;

import java.util.List;

public class TestRepository {

    @Test
    public void can_insert_user(){
        UserRepository repository = new UserRepository("d:\\projects\\db40.android\\test.db4o");
        User u = new User();
        u.firstName = "Rupert";
        u.surname = "Bates";
        repository.saveUser(u);
        List<User> users = repository.getUsers();
        Assert.assertTrue(users.size() > 0);
    }

}
