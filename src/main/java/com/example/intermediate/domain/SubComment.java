package com.example.intermediate.domain;



import com.example.intermediate.controller.request.SubCommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubComment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Column(nullable = false)
    private String content;

    @Column
    private int likes;

    public void update(SubCommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

    public boolean validateComment(Comment comment) {
        return !this.comment.equals(comment);
    }
    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }


    public void updatelikes(int num) {
        this.likes = (num);
    }

}
