package com.its.ex.entity;

import com.its.ex.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String commentWriter;

    @Column(length = 200)
    private String commentContents;

//    @Column
//    private LocalDateTime commentCreatedTime;
//
//    @Column
//    private LocalDateTime commentUpdateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static CommentEntity toSaveCommentEntity(CommentDTO commentDTO,
                                                    BoardEntity board,
                                                    MemberEntity member) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(board);
        commentEntity.setMemberEntity(member);
        return commentEntity;
    }

}
