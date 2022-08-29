package com.example.intermediate.util;

import com.example.intermediate.domain.Post;
import com.example.intermediate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class Scheduler {

    private final PostRepository postRepository;


    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 1 * * *")
    public void deletepost() {

        // 저장된 모든 관심상품을 조회합니다.
        List<Post> posts = postRepository.findAll();
        for(Post post:posts){
            if(post.getComments().size()==0){
                System.out.println("게시물 <"+post.getTitle()+">가 삭제되었습니다.");
                postRepository.deleteById(post.getId());
            }
        }

    }
}