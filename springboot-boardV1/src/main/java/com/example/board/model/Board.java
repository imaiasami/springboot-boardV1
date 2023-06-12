package com.example.board.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Board {
	
	private Long id;					// 일련번호
	private String title;				// 제목
	private String content;				// 내용
	private Long hit;					// 조회수
	private LocalDateTime input_date;	// 등록일
	private String name;				// 작성자
	private String password; 			// 패스워드
	
	public Board(String title, String content, String name, String password) {
		this.title = title;
		this.content = content;
		this.name = name;
		this.password = password;
	}
}
