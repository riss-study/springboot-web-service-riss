package com.riss.book.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers=HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;    //스프링 MVC 테스트의 시작점. 웹 API를 테스트할 때 사용.
        //이 클래스를 통해 HTTP GET, POST 등에 대한 API test 가능
        //Controller와 ControllerAdvice 등 외부 활동과 관련된 부분만 활성화
        //JPA 작동 안함

    @Test
    public void return_hello () throws Exception {
        String hello="hello";

        mvc.perform(get("/hello"))      //MockMvc를 통해 /hello url로 HTTP GET 요청을 함, 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언 가능
                .andExpect(status().isOk())         //mvc.perform의 결과를 검증, HTTP Header의 Status를 검증(흔히 아는 200, 404, 500 등의 상태를 검증), 여기선 Ok 즉, 200인지에 대한 검증
                .andExpect(content().string(hello));    //mvc.perform 결과 검증, 응답 본문의 내용을 검증=> 이 값인 "hello"와 Controller에서 리턴하는 값인 "hello"가 맞는지 검증

    }

    @Test
    public void return_helloDto () throws Exception {
        String name="hello";
        int amount=1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}

//ExtenWith: RunWith의 개선된 어노테이션.
//      테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴.
//      여기서는 SpringExtension이라는 스프링 실행자를 사용
//      즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 함 (xUnit: 테스트 코드 작성을 도와주는 프레임워크)
//
//@WebMvcTest: 스프링 테스트 어노테이션 중 Web(Spring MVC)에 집중할 수 있는 어노테이션
//      @Controller, @ControllerAdvice 등을 사용 가능
//      @Service, @Component, @Repository 등은 사용 불가능
//      why? 여기서는 컨트롤러만 사용하기 때문에 이를 선언
//
//@Autowired: 스프링이 관리하는 빈(Bean)을 주입받음