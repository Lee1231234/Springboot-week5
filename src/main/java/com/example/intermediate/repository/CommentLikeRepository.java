package com.example.intermediate.repository;

import com.example.intermediate.domain.likes.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikes, Long>{
        Optional<CommentLikes> findByRequestIdAndNickname(Long Id , String nickname);
        List<CommentLikes> findAllByRequestId(Long RequestID);




}
