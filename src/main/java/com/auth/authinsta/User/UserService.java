package com.auth.authinsta.User;

import com.auth.authinsta.User.entity.UserEntity;
import com.auth.authinsta.User.model.*;
import com.auth.authinsta.auth.JwtManager;
import com.auth.authinsta.auth.UserContext;
import com.auth.authinsta.exceptions.GenericDataResponse;
import com.auth.authinsta.exceptions.ResponseExceptions.BadRequestException;
import com.auth.authinsta.exceptions.ResponseExceptions.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtManager jwtManager ;

    @Autowired
    private UserContext userContext;

    private UserEntity buildUserEntity(UserRequest userRequest){
        return UserEntity.builder()
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .lastName(userRequest.getLastName())
                .firstName(userRequest.getFirstName())
                .password(userRequest.getPassword())
                .build();
    }

    public Integer getUserId(UserRequest userRequest) {
        List<Integer> userIds = userRepository.findByEmail(userRequest.getEmail());
        if(userIds != null && !userIds.isEmpty()) {
            return userIds.get(0);
        }
        return null;
    }

    public GenericDataResponse<String> loginUser(UserLoginRequest userInfo){
        List<AuthorizedUserInfoProjection> user = userRepository.findUserProperties(userInfo.getEmail() , userInfo.getPassword());

        if(user.isEmpty()){
            throw new ForbiddenException("Invalid credentials");
        }

        String token = jwtManager.generateToken(
                user.get(0).getId(),
                user.get(0).getRole()
        );

        return new GenericDataResponse<>(token);
    }

    public Integer addUser(UserRequest userRequest){
        Integer userId = getUserId(userRequest);

        if(userId == null){
            // when user doesn't exist then we can add this user.
            UserEntity userInfo = buildUserEntity(userRequest);
            userRepository.save(userInfo);
            return userInfo.getId();
        }

        throw new BadRequestException("User already exists");
    }

}
