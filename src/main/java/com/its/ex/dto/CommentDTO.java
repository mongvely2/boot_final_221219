package com.its.ex.dto;

import com.its.ex.entity.CommentEntity;
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

    public static CommentDTO toCommentDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedDate(commentEntity.getCreatedTime());
        commentDTO.setCommentUpdateDate(commentEntity.getUpdatedTime());
        commentDTO.setMemberId(commentEntity.getMemberEntity().getId());
        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());
        return commentDTO;
    }

}
