package com.its.ex.dto;

import com.its.ex.entity.BoardEntity;
import com.its.ex.entity.BoardFileEntity;
import com.its.ex.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedDate;
    private LocalDateTime boardUpdateDate;

    private List<MultipartFile> boardFile;
    private List<String> originalFileName;
    private List<String> storedFileName;
    private String fileAttached;

    private Long memberId;

    public BoardDTO(Long id, String boardTitle, String boardWriter, int boardHits, LocalDateTime boardCreatedDate) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardHits = boardHits;
        this.boardCreatedDate = boardCreatedDate;
    }


    public static BoardDTO toDTO(BoardEntity boardEntity, MemberEntity memberEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedDate(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdateDate(boardEntity.getUpdatedTime());
        boardDTO.setMemberId(memberEntity.getId());

//        첨부파일 있는 경우
        if (boardEntity.getFileAttached().equals("Y")) {
            boardDTO.setFileAttached(boardEntity.getFileAttached());
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
//            첨부파일 이름 가져오기
//              BoardEntity에서 boardFileEntityList 선언해줘서 사용가능함
            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()) {
//                BoardDTO의 originalFileName이 List이기 때문에 add()를 이용하여
//                boardFileEntity에 있는 originalFileName을 옮겨 담음.
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);
        } else {
//            첨부파일 없는 경우 -> fileAttached=N
            boardDTO.setFileAttached(boardEntity.getFileAttached());
        }
        return boardDTO;
    }
}














