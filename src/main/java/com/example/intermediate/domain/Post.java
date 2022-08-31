package com.example.intermediate.domain;

import com.example.intermediate.controller.request.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(length = 1000)
  private String imgUrl;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @Column
  private int likes;

  @Column
  private String url;




  public void update(PostRequestDto postRequestDto, HttpServletRequest request) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
    this.url =  request.getHeader("url");
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }


  public void updatelikes(int num) {
    this.likes = (num);
  }

}
