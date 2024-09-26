package com.chanochoca.app.models;


import com.chanochoca.app.models.entity.Authority;
import com.chanochoca.app.models.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ReadUserDTO readUserDTOToUser(User user);

    default String mapAuthoritiesToString(Authority authority) {
        return authority.getName();
    }

}
