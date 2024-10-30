package com.stackstitch.docker.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserDto implements Serializable {

    public UserDto(){

    }

    public UserDto(Long id, String firstName, String lastName, String address, String telephoneNo){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephoneNo = telephoneNo;
    }

    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String telephoneNo;
}
