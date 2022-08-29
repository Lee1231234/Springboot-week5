package com.example.intermediate.repository;

import com.example.intermediate.domain.likes.SubCommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCommentLikesRepository extends JpaRepository<SubCommentLikes, Long>{

        Optional<SubCommentLikes> findByRequestIdAndNickname(Long Id , String nickname);
        List<SubCommentLikes> findAllByRequestId(Long RequestID);


        List<SubCommentLikes> findAllByNickname(String nickname);
}
