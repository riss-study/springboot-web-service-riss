package com.riss.book.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//JUnit4: RunWith(SpringRunner.class), JUnit5: ExtendWidth(SpringExtension.class) 어노테이션 사용
@ExtendWith(SpringExtension.class)
@SpringBootTest //별 다른 설정 없이 사용할 경우, h2 database를 자동으로 실행
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    //JUnit4: After, JUnit5: AfterEach, AfterAll 등 어노테이션 사용 (After 지원 안함
    @AfterEach
    public void cleanup () {
        postsRepository.deleteAll();
    }

    @Test
    public void loadPostsSaved () {
        //given
        String title="테스트 게시글";
        String content="테스트 본문";

        postsRepository.save(
                Posts.builder()
                        .title(title)
                        .content(content)
                        .author("dlrudghks651@gmail.com")
                        .build()
        );

        //when
        List<Posts> postsList=postsRepository.findAll();
        //findAll(): 해당 table(여기서는 posts)에 있는 모든 data 를 조회해서 가져오는 메소드

        //then
        Posts posts=postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void registerBaseTimeEntity () {

        //given
        LocalDateTime now=LocalDateTime.of(2022, 02, 21, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList=postsRepository.findAll();

        //then
        Posts posts=postsList.get(0);

        System.out.println(">>>>>>>> createDate: "+posts.getCreatedDate()+
                ", modifiedDate: "+posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
