package me.mykite.spring.mapper.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.mykite.spring.boardVO.BoardVO;
import me.mykite.spring.config.annotation.Mapper;

@Mapper
public interface boardMapper {

	public int selectTotalCount(String boardName);
	
	public int selectTotalCountWithKeyword(Map<String,Object> map);
	
	public Integer selectMaxbNum(String boardName);
	
	public ArrayList<BoardVO> selectArticles(Map<String,Object> map);
	
	public ArrayList<BoardVO> selectArticlesWithKeyword(Map<String, Object> map);
	
	public void insertNewArticle(Map<String, Object> map);

	public BoardVO selectOneArticle(Map<String, Object> add);
	
	public void updateChildren(Map<String, Object> map);

	public void insertReArticle(Map<String, Object> map);

	public void updateFrequency(Map<String, Object> map);

	public void updateArticleInfo(Map<String, Object> map);
	
	public List<BoardVO> replySelect(Map<String, Object> map);

	public BoardVO selectOneArticleRe(Map<String, Object> add);

	public void deleteNumArticle(Map<String, Object> map);
}
