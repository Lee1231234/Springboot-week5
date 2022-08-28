package com.example.intermediate.repository;

import com.example.intermediate.domain.likes.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public  interface LikesRepository extends JpaRepository<PostLikes, Long> {
    Optional<PostLikes> findByRequestIdAndNickname(Long Id , String nickname);
    List<PostLikes> findAllByRequestId(Long RequestID);


}
