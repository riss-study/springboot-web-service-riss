package com.riss.book.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //controller를 Json을 반환하는 컨트롤러로 면들어줌 (spring에서의 ResponseBody를 각 메소드마다 선언했던 것을 여기서 한번에 해줌)
public class HelloController {

    @GetMapping("/hello")   //GET REQUEST를 받을 수 있는 API를 만들어 줌, 예전의 @RequestMapping(method=RequestMethod.GET)을 대체
    public String hello () {
        return "hello";
    }
}
