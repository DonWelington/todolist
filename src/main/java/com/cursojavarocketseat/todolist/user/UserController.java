package com.cursojavarocketseat.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {

        var novoUsuario = this.userRepository.findByUsername(userModel.getUsername());

        if(novoUsuario != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome de usuario ja cadastrado!");
        }

       var passworddHashed =  BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
       userModel.setPassword(passworddHashed);

       var userCreated = this.userRepository.save(userModel);
       return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }

}
