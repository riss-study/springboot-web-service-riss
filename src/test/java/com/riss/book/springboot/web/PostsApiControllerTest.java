package com.riss.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riss.book.springboot.domain.posts.Posts;
import com.riss.book.springboot.domain.posts.PostsRepository;
import com.riss.book.springboot.web.dto.PostsSaveRequestDto;
import com.riss.book.springboot.web.dto.PostsUpdateRequestDto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @AfterEach
    public void tearDown () throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void registerPosts () throws Exception {
        //given
        String title="title";
        String content="content";
        PostsSaveRequestDto requestDto=PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url="http://localhost:"+port+"/api/v1/posts";

        //when
//        ResponseEntity<Long> responseEntity=restTemplate
//                .postForEntity(url, requestDto, Long.class);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        )
                .andExpect(status().isOk());

        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all=postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updatePosts() throws Exception {
        //given
        Posts savedPosts=postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId=savedPosts.getId();
        String expectedTitle="title2";
        String expectedContent="content2";

        System.out.println("기존 Posts");
        System.out.println(savedPosts.getId());
        System.out.println(savedPosts.getTitle());
        System.out.println(savedPosts.getContent());

        PostsUpdateRequestDto requestDto=PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url="http://localhost:"+port+"/api/v1/posts/"+updateId;
        HttpEntity<PostsUpdateRequestDto> requestEntity=new HttpEntity<>(requestDto);

        //when
//        ResponseEntity<Long> responseEntity=restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        )
                .andExpect(status().isOk());

        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all=postsRepository.findAll();
        System.out.println("바뀐 Posts");
        System.out.println(all.get(0).getId());
        System.out.println(all.get(0).getTitle());
        System.out.println(all.get(0).getContent());

        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}

/*
* @WebMvcTest는 JPA 기능이 작동하지 않음
* => Controller와 controllerAdvice 등 외부 연동과 관련된 부분만 활성화됨
* JPA 기능까지 한번에 테스트할 경우,
* @SpringBootTest, TestRestTemplate을 이용하면 됨
* */
