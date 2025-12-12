package com.lms.backend.library.mapper;

import com.lms.backend.library.dto.BookRequestDto;
import com.lms.backend.library.dto.BookResponseDto;
import com.lms.backend.library.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @Mapper → MapStruct'a bu interface'in bir mapper olduğunu söyler.
 * componentModel = "spring" → Spring bu mapper'ı otomatik bean olarak yönetir.
 * Böylece service içinde constructor injection ile kullanabilirsin.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookRequestDto dto);

    BookResponseDto toResponseDto(Book book);

    List<BookResponseDto> toResponseDtoList(List<Book> books);
}