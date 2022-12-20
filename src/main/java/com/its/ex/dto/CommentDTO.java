package com.its.ex.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime commentCreatedDate;
    private LocalDateTime commentUpdateDate;

    private Long memberId;
    private Long boardId;

}
