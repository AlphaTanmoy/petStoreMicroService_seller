package com.store.seller.service;

import com.store.seller.error.BadRequestException;
import com.store.seller.model.User;
import com.store.seller.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void UserRepository(UserRepository userRepository) throws BadRequestException{
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(String id) throws BadRequestException {
        return userRepository.findById(id);
    }

    public User findUserByEmail(String email) throws BadRequestException{
        User findUser = userRepository.findByEmail(email);
        BadRequestException badRequestException = new BadRequestException();
        badRequestException.setErrorMessage("User with " + email + " does not exist");
        if(findUser==null) throw badRequestException;
        else return findUser;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
}
