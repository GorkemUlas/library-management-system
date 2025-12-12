package com.lms.backend.library.mapper;

import com.lms.backend.library.dto.UserDto;
import com.lms.backend.library.entity.User;
import org.mapstruct.Mapper;

/**
 * User DTO ↔ User Entity dönüşümleri
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User entity);
}
