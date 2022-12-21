package com.example.bakeryapp.service;

import com.example.bakeryapp.entity.Bakery;
import com.example.bakeryapp.entity.User;
import com.example.bakeryapp.repository.BakeryRepository;
import com.example.bakeryapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BakeryService {
    @Autowired
    private BakeryRepository bakeryRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        // save the user to the database
        userRepository.save(user);
    }
    public boolean login(String username, String password) {
        // check the username and password against the database
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user != null;
    }


    public List<Bakery> getAllBakeries() {
        return bakeryRepository.findAll();
    }

    public Bakery getBakeryById(Long id) {
        Optional<Bakery> optionalBakery = bakeryRepository.findById(id);
        if (optionalBakery.isPresent()) {
            return optionalBakery.get();
        }
        return null;
    }



    // additional methods for create, update, and delete bakery items
    public Bakery createBakery(Bakery bakery) {
        return bakeryRepository.save(bakery);
    }

    public Bakery updateBakery(Bakery bakery) {
        return bakeryRepository.save(bakery);
    }

    public void deleteBakery(Long id) {
        bakeryRepository.deleteById(id);
    }


}
