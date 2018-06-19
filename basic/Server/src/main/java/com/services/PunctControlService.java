package com.services;

import com.model.PunctControl;
import com.repositories.PunctControlRepository;

import java.util.List;

public class PunctControlService {

    PunctControlRepository repository;

    public PunctControlService(PunctControlRepository repository){
        this.repository = repository;
    }

    public PunctControl findOne(Integer id){
        return repository.findOne(id);
    }

    public List<PunctControl> getAll(){
        return repository.findAll();
    }

    public void save(PunctControl punctControl) {
        repository.save(punctControl);
    }
}
