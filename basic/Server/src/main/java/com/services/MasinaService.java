package com.services;

import com.model.Masina;
import com.repositories.MasinaHBNRepositpry;

import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class MasinaService {

    MasinaHBNRepositpry repository;

    public MasinaService(MasinaHBNRepositpry repository){
        this.repository = repository;
    }

    public Masina findOne(Integer id){
        return repository.findOne(id);
    }

    public List<Masina> getAll(){
        return repository.findAll();
    }
}
