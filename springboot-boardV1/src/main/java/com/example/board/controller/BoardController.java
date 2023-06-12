package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.mapper.BoardMapper;
import com.example.board.model.Board;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j
@Controller
public class BoardController {

	// 의존성 주입 방법
	// 1. 필드주입 : 사용하지 않는다.
	// 2. 생성자 주입 : 가장 유용하다
	// 3. setter 주입
	@Autowired
	private BoardMapper boardMapper;

	// 메인페이지 이동
	@GetMapping("/")
	public String main() {
		log.info("main 페이지 이동");
		return "index";
	}

	// 글쓰기 페이지 이동
	@GetMapping("write")
	public String write() {
		log.info("글쓰기 페이지 이동");
		return "write";
	}

	@PostMapping("write")
	public String writeBoard(@ModelAttribute Board board) {
		log.info("write board");
		log.info("board: {}", board);
		boardMapper.writeBoard(board);

		return "redirect:/";
	}

	// 게시판 리스트
	@GetMapping("list")
	public String list(Model model) {
		log.info("리스트 이동");
		List<Board> boards = boardMapper.findAllBoards();
		log.info("board: {}", boards);
		model.addAttribute("boards", boards);
		return "list";
	}

	// 게시글 읽기
	@GetMapping("read/{id}")
	public String readBoard(@PathVariable Long id, Model model) {
		log.info("id: {}", id);
		// 게시글 가져오기
		Board board = boardMapper.findById(id);
		// 조회수 증가
		board.setHit(board.getHit() + 1);
		// 조회수 업데이트
		boardMapper.updateBoard(board);
		// 업데이트 된 게시글 다시 가져오기(선택)
		board = boardMapper.findById(id);
		log.info("board", board);
		model.addAttribute("board", board);
		return "read";
	}

	// 게시글 수정 페이지 이동
	@GetMapping("update/{id}")
	public String update(@PathVariable Long id, Model model) {
		log.info("수정 페이지 이동");

		Board board = boardMapper.findById(id);
		model.addAttribute("board", board);

		return "update";
	}

	// 게시글 수정
	@PostMapping("update/{id}")
	public String updateBoard(@PathVariable Long id, @ModelAttribute Board board) {
		log.info("board: {}", board);
		Board findBoard = boardMapper.findById(id);
		if (findBoard != null && findBoard.getPassword().equals(board.getPassword())) {
			findBoard.setTitle(board.getTitle());
			findBoard.setContent(board.getContent());
			boardMapper.updateBoard(findBoard);
			log.info("게시글 수정 완료");
		}

		return "redirect:/list";

	}

	// 게시글 삭제
	@PostMapping("delete/{id}")
	public String deleteBoard(@PathVariable Long id, @RequestParam (defaultValue="0000") String password ){
		log.info("id: {}", id);
		log.info("password: {}", password);
		Board findboard = boardMapper.findById(id);
		if (findboard != null && findboard.getPassword().equals(password)) {
			boardMapper.deleteById(id);
			log.info("삭제 성공");
		}
		
		return "redirect:/list";
	}

}
