package com.stackstitch.docker.mapper;

import com.stackstitch.docker.dto.UserDto;
import com.stackstitch.docker.entity.User;

public class DtoToEntity {

    public static User mapUserDtoToUser(UserDto userDto){
        return new User(userDto.getId(), userDto.getFirstName(), userDto.getLastName(), userDto.getAddress(), userDto.getTelephoneNo());
    }
}
