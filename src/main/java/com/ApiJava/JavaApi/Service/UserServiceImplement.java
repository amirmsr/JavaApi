package com.ApiJava.JavaApi.Service;

import com.ApiJava.JavaApi.Model.User;
import com.ApiJava.JavaApi.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImplement  {

    private final UserRepository userRepository;



    public List<User> get() {
        return userRepository.findAll();
    }




    public User getById(Long id) throws BadRequestException {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("id non trouvé " + id));
    }


    public User post(User newUser) throws BadRequestException {
       boolean existMail = userRepository.existsByMail(newUser.getMail());
       if(existMail){
           throw new BadRequestException("email existe deja" + newUser.getMail());

       }
        return userRepository.save(newUser);
    }


    public User updateById(Long id, User updatedUser) throws BadRequestException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found with id: " + id));

        existingUser.setName(updatedUser.getName());
        existingUser.setMail(updatedUser.getMail());
        existingUser.setPsw(updatedUser.getPsw());

        return userRepository.save(existingUser);
    }

    public void deleteById(Long id) throws BadRequestException {
        // Check if the user with the given ID exists
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("User not found with id: " + id);
        }

        // Delete the user by ID
        userRepository.deleteById(id);
    }


}
