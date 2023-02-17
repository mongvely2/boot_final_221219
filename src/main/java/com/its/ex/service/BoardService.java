package com.its.ex.service;

import com.its.ex.dto.BoardDTO;
import com.its.ex.entity.BoardEntity;
import com.its.ex.entity.BoardFileEntity;
import com.its.ex.entity.MemberEntity;
import com.its.ex.repository.BoardFileRepository;
import com.its.ex.repository.BoardRepository;
import com.its.ex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardFileRepository boardFileRepository;

    public Page<BoardDTO> paging(Pageable pageable) {
//        page: (하단에 표시되는) 해당 page(배열처럼 0번이 1번임) / pageLimit: 보여줄 한 페이지에서의 게시글 수
        int page = pageable.getPageNumber()-1;
        final int pageLimit = 5;
//        Page<> : 스프링에서 제공하는 인터페이스 / List<> 랑 헷갈리면 안 됨
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC,"id")));
//                boardEntities에 담긴 boardEntity 객체를 board에 담아서
//                boardDTO 객체로 하나씩 옮겨 담는 과정
        Page<BoardDTO> boardList = boardEntities.map(
                board -> new BoardDTO(
                        board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime()
                )
        );
        return boardList;
    }

    public void save(BoardDTO boardDTO) throws IOException {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(boardDTO.getBoardWriter()).get();
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            // 첨부 파일 없음
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, memberEntity);
            boardRepository.save(boardEntity);
        } else {
            // 첨부 파일 있음
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO, memberEntity);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity entity = boardRepository.findById(savedId).get();
//            파일이 담긴 list를 반복문으로 접근하여 하나씩 이름 가져오고, 저장용 이름 만들고
//            로컬 경로에 저장하고 board_file_table에 저장
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
//          MultipartFile boardFile = boardDTO.getBoardFile();
//          ->  단수일 때 한번만 했던것을 위처럼 반복문으로 진행(지금은 다중업로드)
                String originalFilename = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                String filePath = "D:\\boot_final_file\\" + storedFileName;
                boardFile.transferTo(new File(filePath));
                BoardFileEntity boardFileEntity =
                        BoardFileEntity.toSaveBoardFileEntity(entity, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
        }
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            MemberEntity memberEntity = boardEntity.getMemberEntity();
            BoardDTO boardDTO = BoardDTO.toDTO(boardEntity, memberEntity);
            return boardDTO;
        } else {
            return null;
        }
    }
}


























