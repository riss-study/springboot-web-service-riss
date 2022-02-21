package com.riss.book.springboot.service.posts;

import com.riss.book.springboot.domain.posts.Posts;
import com.riss.book.springboot.domain.posts.PostsRepository;
import com.riss.book.springboot.web.dto.PostsResponseDto;
import com.riss.book.springboot.web.dto.PostsSaveRequestDto;
import com.riss.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
