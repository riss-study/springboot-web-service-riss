package com.riss.book.springboot.web;

import com.riss.book.springboot.config.auth.LoginUser;
import com.riss.book.springboot.config.auth.dto.SessionUser;
import com.riss.book.springboot.service.posts.PostsService;
import com.riss.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index (Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());
        //서버 템플릿 엔진에서 사용할 수 있는 객체 저장 가능, 여기서는 postsService.findAllDesc()에서 가져온 결과를
        //posts라는 이름으로 index.mustache에 전달함

        if (null!=user) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userRole", user.getRole());
        }

        return "index";
        //mustache starter를 사용하므로, 컨트롤러에서 문자열을 반환할 때.
        // src/main/resources/templates/index.mustache 가
        // index 앞뒤로 자동으로 붙어서 View Resolver가 이를 처리 함
    }

    @GetMapping("/posts/save")
    public String postsSave () {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate (@PathVariable Long id, Model model) {
        PostsResponseDto dto=postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
