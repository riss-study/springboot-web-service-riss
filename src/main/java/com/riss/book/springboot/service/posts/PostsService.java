package com.riss.book.springboot.service.posts;

import com.riss.book.springboot.domain.posts.Posts;
import com.riss.book.springboot.domain.posts.PostsRepository;
import com.riss.book.springboot.web.dto.PostsListResponseDto;
import com.riss.book.springboot.web.dto.PostsResponseDto;
import com.riss.book.springboot.web.dto.PostsSaveRequestDto;
import com.riss.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save (PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update (Long id, PostsUpdateRequestDto requestDto) {
        Posts posts=postsRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("There is no such post. id= "+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById (Long id) {
        Posts entity=postsRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("There is no such post. id= "+id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) //이렇게 하면, 조회 기능만 남겨두어 조회 속도가 개선됨, 조회 기능에서 추천
    public List<PostsListResponseDto> findAllDesc () {
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
        //.map(PostsListResponseDto::new) == .map(posts->new PostsListResponseDto(posts))
        //return: postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로 반환하는 메소드
    }

    @Transactional
    public void delete (Long id) {
        Posts posts=postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        postsRepository.delete(posts);  //JpaRepository에서 이미 delete 메소드를 지원하니 이를 활용
        //deleteById 메소드를 이용하면 id를 이용하여 삭제 가능
        // 여기서는 존재하는 posts 인지 확인을 위해 엔티티 조회 후 그대로 삭제함
    }
}
