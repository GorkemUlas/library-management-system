package com.lms.backend.library.mapper;

import com.lms.backend.library.dto.UserDto;
import com.lms.backend.library.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * User DTO ↔ User Entity dönüşümleri
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "email", ignore = true) // service setlesin
    User toEntity(UserDto dto);

    UserDto toDto(User entity);
}
