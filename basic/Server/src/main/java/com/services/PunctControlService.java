package com.services;

import com.model.PunctControl;
import com.repositories.PunctControlRepository;

import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
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
}
