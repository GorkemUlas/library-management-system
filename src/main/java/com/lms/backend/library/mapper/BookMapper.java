package com.lms.backend.library.mapper;

import com.lms.backend.library.dto.BookRequestDto;
import com.lms.backend.library.dto.BookResponseDto;
import com.lms.backend.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @Mapper → MapStruct'a bu interface'in bir mapper olduğunu söyler.
 * componentModel = "spring" → Spring bu mapper'ı otomatik bean olarak yönetir.
 * Böylece service içinde constructor injection ile kullanabilirsin.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {



    @Mapping(target = "bookId", ignore = true)
    @Mapping(target = "availableCopies", ignore = true)
//    @Mapping(target = "status", ignore = true)
    @Mapping(target = "status", expression = "java(book.getStatus() == null ? null : book.getStatus().name())", ignore = true)

    Book toEntity(BookRequestDto dto);

    BookResponseDto toResponseDto(Book book);

    List<BookResponseDto> toResponseDtoList(List<Book> books);

//    default String map(Book.BookStatus status) {
//        return status == null ? null : status.name();




//        @Mapping(target = "bookId", ignore = true) // create için id DB tarafından üretilir
//        @Mapping(target = "availableCopies", ignore = true) // service setlesin
//          @Mapping(target = "status", expression = "java(book.getStatus() == null ? null : book.getStatus().name())", ignore = true)
//        @Mapping(target = "isbn", source = "isbn")
//        @Mapping(target = "title", source = "title")
//        @Mapping(target = "author", source = "author")
//        @Mapping(target = "totalCopies", source = "totalCopies")
//        @Mapping(target = "imageUrl", source = "imageUrl")
//        Book toEntity(BookRequestDto dto);
//        BookResponseDto toResponseDto(Book book);
//        List<BookResponseDto> toResponseDtoList(List<Book> books);
//



    }

