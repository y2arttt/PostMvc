package com.PostMvc.PostMvc.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data // Lombok의 @Data 어노테이션을 사용하면 getter, setter, toString, equals, hashCode 메서드를 자동으로 생성해줍니다.
public class PostVo {
    private int id;
    private String title;
    private String content;
    private String username;
    private String createdBy;
    private LocalDateTime createdAt; // createdAt 속성 추가
    private LocalDateTime updatedAt; // updatedAt 속성 추가
}
