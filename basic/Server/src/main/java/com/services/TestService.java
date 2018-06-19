package com.services;

import com.model.Intrebare;
import com.model.TestCultura;
import com.repositories.IntrebareRepository;
import com.repositories.TestHBNRepository;

import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class TestService {

    TestHBNRepository repository;

    public TestService(TestHBNRepository repository){
        this.repository = repository;
    }

    public void save(TestCultura testCultura){
        this.repository.save(testCultura);
    }

    public TestCultura findOne(Integer id){
        return repository.findOne(id);
    }

    public List<TestCultura> getAll(){
        return repository.findAll();
    }
}
