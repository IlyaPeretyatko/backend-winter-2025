package ru.cft.peretyatko.mapper;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.cft.peretyatko.dto.user.UserGetResponse;
import ru.cft.peretyatko.dto.user.UserPatchRequest;
import ru.cft.peretyatko.dto.user.UserPostRequest;
import ru.cft.peretyatko.dto.user.UserPostResponse;
import ru.cft.peretyatko.model.User;
import ru.cft.peretyatko.model.Wallet;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User toUser(UserPostRequest userPostRequest) {
        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        Wallet wallet = new Wallet();
        wallet.setBalance(100);
        user.setWallet(wallet);
        wallet.setUser(user);
        user.setPassword(BCrypt.hashpw(userPostRequest.getPassword(), BCrypt.gensalt(12)));
        user.setLastName(userPostRequest.getLastName());
        user.setFirstName(userPostRequest.getFirstName());
        user.setMiddleName(userPostRequest.getMiddleName());
        user.setEmail(userPostRequest.getEmail());
        user.setPhone(userPostRequest.getPhone());
        user.setBirthdate(userPostRequest.getBirthdate());
        return user;
    }

    public UserPostResponse toUserPostResponse(User user) {
        UserPostResponse userPostResponse = new UserPostResponse();
        userPostResponse.setId(user.getId());
        return userPostResponse;
    }

    public UserGetResponse toUserGetResponse(User user) {
        UserGetResponse userGetResponse = new UserGetResponse();
        userGetResponse.setLastName(user.getLastName());
        userGetResponse.setFirstName(user.getFirstName());
        userGetResponse.setMiddleName(user.getMiddleName());
        userGetResponse.setBirthdate(user.getBirthdate());
        return userGetResponse;
    }

    public void updateUser(UserPatchRequest userPatchRequest, User user) {
        if (userPatchRequest.getLastName() != null) {
            user.setLastName(userPatchRequest.getLastName());
        }
        if (userPatchRequest.getFirstName() != null) {
            user.setFirstName(userPatchRequest.getFirstName());
        }
        if (userPatchRequest.getMiddleName() != null) {
            user.setMiddleName(userPatchRequest.getMiddleName());
        }
        if (userPatchRequest.getBirthdate() != null) {
            user.setBirthdate(userPatchRequest.getBirthdate());
        }
    }

}
