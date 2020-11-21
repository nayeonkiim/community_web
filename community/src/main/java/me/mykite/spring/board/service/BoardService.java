package me.mykite.spring.board.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.mykite.spring.boardVO.BoardVO;
import me.mykite.spring.mapper.board.boardMapper;

@Service
public class BoardService {
	//�� �������� ��Ÿ�� �Խñ� ��
	final static int PER_PAGE_NUM = 10;
	//�� �������� ��Ÿ�� ��� ��
	final static int NUM_FOR_BLOCK = 10;
	
	@Autowired
	private boardMapper boardmapper;
	
	public int[] blockInfo(String boardName, int page, String type, String searchValue) {
		
		//��ü �Խñ� �� select
		int totalArticle = 0;
		if(type == "0") {
			totalArticle = boardmapper.selectTotalCount(boardName);
		}else {
			Map<String, Object> map = new HashMap<>();
			map.put("boardName",boardName);
			map.put("type", type);
			map.put("searchValue",searchValue);
			
			totalArticle = boardmapper.selectTotalCountWithKeyword(map);
		}
		int totalPage = totalArticle/PER_PAGE_NUM;
		
		if(totalArticle % PER_PAGE_NUM > 0) {
			totalPage += 1;
		}
		int startBlock = ((page-1)/NUM_FOR_BLOCK) * NUM_FOR_BLOCK + 1;
		int endBlock = startBlock + NUM_FOR_BLOCK - 1 > totalPage 
								? totalPage : startBlock+NUM_FOR_BLOCK-1;
		return new int[] { startBlock, endBlock, totalPage };
	}
	
	public Map<String,Object> ArticleInfo(String boardName, int page, String type, String searchValue) {
		Map<String, Object> map = new HashMap<>();
		ArrayList<BoardVO> boardInfo = null;
		
		map.put("boardName", boardName);
		map.put("from", (page-1) * PER_PAGE_NUM);
		map.put("PER_PAGE_NUM", PER_PAGE_NUM);
		
		if(type!= "null" && searchValue != "null") {
			map.put("type", type);
			map.put("searchValue", searchValue);
			
			boardInfo = boardmapper.selectArticlesWithKeyword(map);
		}else {
			boardInfo = boardmapper.selectArticles(map);
		}
		
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("NUM_FOR_BLOCK", NUM_FOR_BLOCK);
		returnMap.put("boardInfo",boardInfo);
		return returnMap;
	}
	
	public void writeArticle(String boardName, int bNum, BoardVO board) {
		Map<String, Object> map = new HashMap<>();
		//���ο� ��
		if(bNum == 0) {
			Integer nextBNum = boardmapper.selectMaxbNum(boardName);
			if(nextBNum == null)
				nextBNum = 0;
			
			map.put("boardName",boardName);
			//�긦 bParent�� �־���
			map.put("nextBNum", nextBNum+1);
			map.put("board", board);
			boardmapper.insertNewArticle(map);
		//���
		}
	}
	
	public void writeReplyArticle(String boardName, int bNum, int bOrder,int bIndent,
								String bId, String bContent) {
		
		Map<String, Object> map = new HashMap<>();
		
		//bParent, bIndent, bOrder ��� ������
		Map<String, Object> add = new HashMap<>();
		add.put("boardName", boardName);
		add.put("bNum", bNum);
		
		//�θ�� select
		BoardVO parent = boardmapper.selectOneArticle(add);
		//���� ��� ��ü �����
		BoardVO neboard = new BoardVO();
		neboard.setbId(bId);
		neboard.setbContent(bContent);
		
		neboard.setbParent(parent.getbParent());
		neboard.setbIndent(parent.getbIndent()+1);
		neboard.setbOrder(parent.getbOrder()+1);
			
		map.put("boardName",boardName);
		map.put("board", neboard);
		// bOrder +1�� ��� ���ֱ�
		boardmapper.updateChildren(map);
		boardmapper.insertReArticle(map);
	}
	
	
	//��ȸ�� ����
	public boolean addCountFrequency(String boardName, int bNum) {
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("boardName", boardName);
			map.put("bNum", bNum);
			
			boardmapper.updateFrequency(map);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	// ���б�
	public BoardVO readArticleInfo(String boardName, int bNum) {
		Map<String,Object> map = new HashMap<>();
		map.put("boardName", boardName);
		map.put("bNum", bNum);
		
		return boardmapper.selectOneArticle(map);
	}
	
	//�� ����
	public boolean modifyArticle(String boardName, BoardVO board, int bNum) {
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("boardName", boardName);
			map.put("board", board);
			map.put("bNum", bNum);
			boardmapper.updateArticleInfo(map);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//��� select
	public List<BoardVO> replyBoardLists(String boardName, int bNum){
		Map<String, Object> mapp = new HashMap<>();
		mapp.put("boardName", boardName);
		mapp.put("bNum", bNum);
		//���� ���� ��
		BoardVO board = boardmapper.selectOneArticle(mapp);
		
		Map<String, Object> map = new HashMap<>();
		map.put("boardName", boardName);
		map.put("board", board);
		
		return boardmapper.replySelect(map);
	}

	public boolean deleteArticle(String boardName, int bNum) {
		Map<String, Object> map = new HashMap<>();
		map.put("boardName", boardName);
		map.put("bNum", bNum);
		try {
			BoardVO boa = boardmapper.selectOneArticle(map);
			System.out.println(boa.getbParent());
			map.put("bParent", boa.getbParent());
			boardmapper.deleteNumArticle(map);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}