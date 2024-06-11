package com.ageurdo.demo_user_auth_api.web.dto.mapper;

import com.ageurdo.demo_user_auth_api.entity.User;
import com.ageurdo.demo_user_auth_api.web.dto.UserCreateDto;
import com.ageurdo.demo_user_auth_api.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class  UserMapper {

    public static User toUser(UserCreateDto createDto) {
        return new ModelMapper().map(createDto, User.class);
    }

    public static UserResponseDto toDto(User user) {
//        String role = user.getRole().name().substring("ROLE_".length());
//        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
//            @Override
//            protected void configure() {
//                map().setRole(role);
//            }
//        };
//        ModelMapper mapper = new ModelMapper();
//        mapper.addMappings(props);
//        return mapper.map(user, UserResponseDto.class);

//        return new ModelMapper().map(user, UserResponseDto.class);
        String role = user.getRole().name().substring("ROLE_".length());
        UserResponseDto dto = new UserResponseDto();
        dto.setRole(role);


        return dto;
    }

    public static List<UserResponseDto> toListDto(List<User> users) {
        return users.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }


}
