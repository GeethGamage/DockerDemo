package com.stackstitch.docker.mapper;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.entity.User;

public class EntityToDto {

    public static UserDto mapUserToUserDto(User user){
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getTelephoneNo());
    }
}
