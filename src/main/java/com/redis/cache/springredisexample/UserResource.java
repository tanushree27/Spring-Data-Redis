package com.redis.cache.springredisexample;

import com.redis.cache.springredisexample.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@EnableCaching
@RestController
@RequestMapping("/rest/user")
public class UserResource {

    private UserRepository userRepository;

    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/add/{id}/{name}")
    public User add(@PathVariable("id") final String id, @PathVariable("name") final String name){

        userRepository.save(new User(id, name, 20000L));
        return userRepository.findById(id);
    }

    @GetMapping("/update/{id}/{name}")
    public User update(@PathVariable("id") final String id,
                    @PathVariable("name") final String name){

        userRepository.save(new User(id, name, 1000L));
        return userRepository.findById(id);
    }


    @GetMapping("/newUser/{id}/{name}")
    @Cacheable(key = "#id", value = "USER")
    public User newUser(@PathVariable("id") final String id,
                       @PathVariable("name") final String name){

        userRepository.save(new User(id, name, 1000L));
        System.out.println("Called newUser from DB");
        return userRepository.findById(id);
    }

    @GetMapping("/delete/{id}")
    public Map<String, User> delete(@PathVariable("id") final String id){

        userRepository.delete(id);
        return all();
    }

    @GetMapping("/all")
    public Map<String, User> all(){

        return userRepository.findAll();
    }


}
