package com.its.ex.service;


import com.its.ex.dto.CommentDTO;
import com.its.ex.entity.BoardEntity;
import com.its.ex.entity.CommentEntity;
import com.its.ex.entity.MemberEntity;
import com.its.ex.repository.BoardRepository;
import com.its.ex.repository.CommentRepository;
import com.its.ex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void save(CommentDTO commentDTO) {
        MemberEntity memberEntity = memberRepository.findById(commentDTO.getMemberId()).get();
        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).get();
        CommentEntity commentEntity = CommentEntity.toSaveCommentEntity(commentDTO, boardEntity, memberEntity);
        System.out.println("commentEntity = " + commentEntity);
        commentRepository.save(commentEntity);
    }

    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList =
                commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            commentDTOList.add(CommentDTO.toCommentDTO(commentEntity));
        }
        return commentDTOList;
    }
}
