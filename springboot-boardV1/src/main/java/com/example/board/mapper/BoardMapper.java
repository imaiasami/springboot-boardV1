package com.example.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.model.Board;

@Mapper
public interface BoardMapper {
	
	public int writeBoard(Board board);

	public List<Board> findAllBoards();

	public Board findById(Long id);
	
	public int updateBoard(Board board);
	
	public int deleteById(Long id);
}
