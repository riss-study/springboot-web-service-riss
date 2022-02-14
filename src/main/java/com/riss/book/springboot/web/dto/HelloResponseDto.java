package com.riss.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

    private final String name;
    private final int amount;
}

//@Getter: 선언된 모든 필드의 get method를 생성해줌
//@RequiredArgsConstructor: 선언된 모든 final field가 포함된 생성자를 생성해줌
//              final이 없는 field는 생성자에 포함되지 않음
