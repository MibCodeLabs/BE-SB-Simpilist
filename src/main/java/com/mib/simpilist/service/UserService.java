package com.mib.simpilist.service;

import com.mib.simpilist.dto.Auth.Registration.UserRegistrationRequest;
import com.mib.simpilist.exception.ResourceNotFoundException;
import com.mib.simpilist.model.User;
import com.mib.simpilist.repository.UserRepo;
import com.mib.simpilist.service.Security.PasswordService;
import com.mib.simpilist.utililty.factory.UserFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Service
@Slf4j(topic = "UserService")
public class UserService {
    private final UserRepo userRepo;
    private final PasswordService passwordService;

    public UserService(@Lazy UserRepo userRepo,@Lazy PasswordService passwordService){
        this.userRepo=userRepo;
        this.passwordService=passwordService;
    }

    private Optional<User> findUserByIdOptional(Long id){
        return userRepo.findById(id);
    }

    private User findUserById(Long id){
        return findUserByIdOptional(id).orElseThrow(()->{
            log.error("User with id:{} does not exists",id);
            return new ResourceNotFoundException("not found");
        });
    }

    public Optional<User> findUserByEmailOptional(String email){
        return userRepo.findByEmailLike(email);
    }

    public User findUserByEmail(String email) {
        return findUserByEmailOptional(email).orElseThrow(
                () -> {
                    log.error("No user Found with email:{}", email);
                    return new ResourceNotFoundException("No User Found having email");
                }
        );
    }

    private void RegisterNewUser(UserRegistrationRequest userRegistrationRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Pair<String,String> saltAndHash= passwordService.generateSaltAndSaltedHash(userRegistrationRequest.getPassword());
        User user=UserFactory.buildRegistrationUser(userRegistrationRequest,saltAndHash.getFirst(),saltAndHash.getSecond());
        save(user);
    }

    private User save(User user){
        log.info("Saving new user {}",user);
        return userRepo.save(user);
    }


}
