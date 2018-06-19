package com.services;

import com.model.TSUser;
import com.repositories.UserRepo;

import java.util.List;

public class UsersService {
    UserRepo repo;

    public UsersService(UserRepo repo){
        this.repo = repo;
    }


    public TSUser findUser(int Id){
        return this.repo.findOne(Id);
    }

    public TSUser findByUsername(String username){
        return this.repo.findUserName(username);
    }

    public List<TSUser> getAll() {
        return repo.findAll();
    }
}
