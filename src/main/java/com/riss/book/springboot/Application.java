package com.riss.book.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //springboot의 자동 설정, spring bean 일기, 생성 모두 자동 설정하게 해줌 => 항상 프로젝트의 최상단에 위치해야하는 어노테이션
public class Application {  //main class of this project
    public static void main (String[] args) {
        //내장 WAS(Web Application Server) execute code
        SpringApplication.run(Application.class, args);
    }
}
