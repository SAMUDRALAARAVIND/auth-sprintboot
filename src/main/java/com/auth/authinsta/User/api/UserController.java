package com.auth.authinsta.User.api;

import com.auth.authinsta.User.UserRepository;
import com.auth.authinsta.User.UserService;
import com.auth.authinsta.User.model.*;
import com.auth.authinsta.auth.UserContext;
import com.auth.authinsta.exceptions.GenericDataResponse;
import com.auth.authinsta.exceptions.ResponseExceptions.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserContext userContext;

    @PostMapping("/login")
    public GenericDataResponse<String> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new BadRequestException("Invalid input: "+bindingResult.getAllErrors().get(0));
        }
        return userService.loginUser(userLoginRequest);
    }

    @PostMapping("/signup")
    public GenericDataResponse<Integer> addUser(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new BadRequestException("Invalid input: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new GenericDataResponse<>(userService.addUser(userRequest));
    }

    @GetMapping("/details")
    public List<IamResponseProjection> getUsers(){
        Integer userId = userContext.getUserId();

        return userRepository.findIamUserInfo(userId);
    }
}
