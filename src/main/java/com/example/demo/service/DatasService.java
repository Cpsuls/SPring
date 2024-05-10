package com.example.demo.service;
import com.example.demo.models.DatasDB;
import com.example.demo.repos.DatasDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatasService {
    @Autowired
    private DatasDBRepository datasDBRepository;

    public List<DatasDB> listAll(String keyword){
        if(keyword!=null){
            return datasDBRepository.search(keyword);
        }
        return datasDBRepository.findAll();
    }

    public List<DatasDB> getAll(){
        return datasDBRepository.findAll();
    }
    public void save(DatasDB datasDB){
        datasDBRepository.save(datasDB);
    }
    public DatasDB get(Long id){
        return datasDBRepository.findById(id).get();
    }
    public void delete(Long id){
        datasDBRepository.deleteById(id);

    }
}
