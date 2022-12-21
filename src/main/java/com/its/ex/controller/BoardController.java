package com.its.ex.controller;

import com.its.ex.dto.BoardDTO;
import com.its.ex.dto.CommentDTO;
import com.its.ex.repository.BoardRepository;
import com.its.ex.service.BoardService;
import com.its.ex.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm() {
        return "boardPages/boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.save(boardDTO);
        return "redirect:/board";
    }

    @GetMapping("{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page = 1) Pageable pageable) {
//        조회수 1증가
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);

        List<CommentDTO> commentDTOList = commentService.findAll(id);
        if (commentDTOList.size() > 0) {
            model.addAttribute("commentList", commentDTOList);
        } else {
            model.addAttribute("commentList", "empty");
        }

        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        System.out.println("pageable = " + pageable.getPageNumber());
        System.out.println("pageable = " + pageable);
        return "boardPages/boardDetail";
    }


    @GetMapping
    public String paging(@PageableDefault(page = 1)Pageable pageable,
                         Model model) {
        System.out.println("pageable = " + pageable);
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





















