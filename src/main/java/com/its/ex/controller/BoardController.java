package com.its.ex.controller;

import com.its.ex.dto.BoardDTO;
import com.its.ex.repository.BoardRepository;
import com.its.ex.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boardSave")
    public String saveForm() {
        return "boardPages/boardSave";
    }

    @GetMapping
    public String paging(@PageableDefault(page = 1)Pageable pageable,
                         Model model) {
        Page<BoardDTO> boardDTOList = boardService.paging(pageable);
        model.addAttribute("boardList", boardDTOList);
//        start 페이지 end 페이지 계산방식 -> 삼항연산자 사용
//        int nowPage = boardDTOList.getNumber();
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double) pageable.getPageNumber() / blockLimit))) -1) * blockLimit+1;
        int endPage = ((startPage+blockLimit-1)<boardDTOList.getTotalPages()) ? startPage+blockLimit-1 : boardDTOList.getTotalPages();
//        삼항연산자 = if/else 를 간단하게 작성한 것, 아래는 풀이를 위한 코드임(기능구현 상관x)
        int test = 10;
        int num = (test > 5) ? test : 100;    // 아래 if문이랑 똑같음
        if (test > 5) {
            num = test;
        } else {
            num = 100;
        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardPages/paging";
    }

}





















