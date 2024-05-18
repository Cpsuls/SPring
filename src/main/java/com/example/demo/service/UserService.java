package com.example.demo.service;
import com.example.demo.models.DatasDB;
import com.example.demo.models.User;
import com.example.demo.repos.DatasDBRepository;
import com.example.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> listAll(String keyword){
        if(keyword!=null){
            return userRepository.search(keyword);
        }
        return userRepository.findAll();
    }
    public void save(User user){
        userRepository.save(user);
    }
    public User get(Long id){
        return userRepository.findById(id).get();
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public void delete(Long id){
        userRepository.deleteById(id);
    }
}

