package com.ums.service;

import com.ums.entity.AppUser;
import com.ums.payload.LoginDto;
import com.ums.payload.UserDto;
import com.ums.repository.AppUserRepository;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;



//@RequiredArgsConstructor
//@AllArgsConstructor
//@NoArgsConstructor
@Service
public class  UserServiceImpl implements UserService{
    private JWTService jwtService;
    private AppUserRepository userRepository;

    public UserServiceImpl(JWTService jwtService,AppUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository=userRepository;
    }


    @Override
    public UserDto addUser(UserDto userDto) {
        AppUser user = mapToEntity(userDto);
        AppUser savedUser = userRepository.save(user);
        UserDto dto = mapToDto(savedUser);
        return dto;
    }

    @Override
    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> opUser = userRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()){
            AppUser user = opUser.get();
            if(BCrypt.checkpw(loginDto.getPassword(),user.getPassword())) {
             return  jwtService.generateToken(user);
            }
        }
        return null;
    }

    UserDto mapToDto(AppUser user){
       UserDto dto = new UserDto();
       dto.setId(user.getId());
       dto.setName(user.getName());
       dto.setUsername(user.getUsername());
       dto.setUserRole(user.getUserRole());
       dto.setEmailId(user.getEmailId());
       //dto.setPassword(savedUser.getPassword());
       return dto;
    }

    AppUser mapToEntity(UserDto userDto){
        AppUser user = new AppUser();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmailId(userDto.getEmailId());
        user.setUserRole(userDto.getUserRole());
        //user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setPassword(BCrypt.hashpw(userDto.getPassword(),BCrypt.gensalt(10)));
        return user;
    }
}
