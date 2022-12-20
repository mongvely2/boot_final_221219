package com.its.ex.service;

import com.its.ex.dto.BoardDTO;
import com.its.ex.entity.BoardEntity;
import com.its.ex.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
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
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime()
                )
        );
        return boardList;
    }
}
