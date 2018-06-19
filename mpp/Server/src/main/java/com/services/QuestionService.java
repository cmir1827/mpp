package com.services;

import com.model.Intrebare;
import com.repositories.IntrebareRepository;

import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class QuestionService {

    IntrebareRepository repository;

    public QuestionService(IntrebareRepository repository){
        this.repository = repository;
    }

    public Intrebare findOne(Integer id){
        return repository.findOne(id);
    }

    public List<Intrebare> getAll(){
        return repository.findAll();
    }
}
