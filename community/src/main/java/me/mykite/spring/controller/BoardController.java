package me.mykite.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import me.mykite.spring.board.service.BoardService;
import me.mykite.spring.boardVO.BoardVO;

@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	
	//��ü ������ �����ֱ�
	@RequestMapping(value="/board/{boardName}/{page}")
	public String toBoardViewpage(@PathVariable("boardName") String boardName,
			@PathVariable("page") int page, @RequestParam(value="type", required=false, defaultValue= "0") String type, 
			@RequestParam(value="searchValue", required=false, defaultValue= "0") String searchValue, Model model) {
		
		Map<String,Object> board = null;
		int[] block = null;
		if(type == "0" || type == null) {
			block = boardService.blockInfo(boardName, page, "null","null");
			if(block[1] > 0) {
				//�� ������ ����
			board = boardService.ArticleInfo(boardName, page, "null", "null");
			model.addAttribute("type",null);
			model.addAttribute("searchValue",null);
		}
		}else {
			block = boardService.blockInfo(boardName, page, type,searchValue);
			if(block[1] > 0) {
				//�� ������ ����
				board = boardService.ArticleInfo(boardName, page, type, searchValue);
			}
			model.addAttribute("type",type);
			model.addAttribute("searchValue",searchValue);
		}
		
		model.addAttribute("nowPage",page);
		model.addAttribute("block", block);
		model.addAttribute("board", board);
		model.addAttribute("boardName",boardName);
		return "/board/boardView";
	}
	
	//��۰� ���ο� �۾���
	@RequestMapping(value="/board/{boardName}/{bNum}/0")
	public String toWriteArticle(@PathVariable("boardName") String boardName, 
			@PathVariable("bNum") int bNum, Model model, HttpServletRequest request) {
		model.addAttribute("boardName", boardName);
		model.addAttribute("bNum", bNum);
		return "/board/write";
	}
	
	@RequestMapping("/board/{boardName}/{bNum}/askWriteArticle")
	public String asktoWriteArticle(@PathVariable("boardName") String boardName,
			@PathVariable("bNum") int bNum, BoardVO board, RedirectAttributes rttr) {
		logger.info("writeArticle");
		
		boardService.writeArticle(boardName, bNum, board);
		rttr.addFlashAttribute("result", "writeArticleOk");
		return "redirect:/board/{boardName}/1";
	}
	
	@RequestMapping("/board/{boardName}/readArticle/{bNum}/{page}")
	public String readArticle(@PathVariable("boardName") String boardName, 
			@PathVariable("bNum") int bNum, @PathVariable("page") int page, Model model) {
		//��ȸ�� ����
		boolean result = boardService.addCountFrequency(boardName,bNum);
		
		//BoardVO ��ü select
		BoardVO board = boardService.readArticleInfo(boardName, bNum);
		model.addAttribute("boardName", boardName);
		model.addAttribute("board", board);
		model.addAttribute("page", page);
		model.addAttribute("bNum", bNum);
		//�亯 �� select
		
		return "/board/read";
	}
	
	@RequestMapping("/board/{boardName}/modifyArticle/{bNum}")
	public String modifyArticle(@PathVariable("boardName") String boardName,
			@PathVariable("bNum") int bNum, Model model) {
		
		BoardVO board = boardService.readArticleInfo(boardName, bNum);
		model.addAttribute("boardName", boardName);
		model.addAttribute("board",board);
		model.addAttribute("bNum", bNum);
		
		return "/board/modify";
	}
	
	@RequestMapping("/{boardName}/askModify/{bNum}")
	public String modifyFinal(@PathVariable("boardName") String boardName,
			@PathVariable("bNum") int bNum, BoardVO board, RedirectAttributes rttr) {
		
		logger.info("modifyArticle");
		
		boolean result = boardService.modifyArticle(boardName, board, bNum);
		if(result) {
			rttr.addFlashAttribute("result","modifyOk");
			return "redirect:/board/{boardName}/1";
		}else {
			rttr.addFlashAttribute("result","modifyFail");
			return "redirect:/board/{boardName}/modifyArticle/{bNum}";
		}
	}
	
	//�亯 ����ϱ� ajax
	@RequestMapping("/board/reply")
	@ResponseBody
	public String addComment(@RequestParam int bIndent, @RequestParam int bOrder,
			@RequestParam String boardName, @RequestParam int bNum, @RequestParam String bId,
			@RequestParam String bContent, HttpServletRequest request) {
		try {
			//�θ��� bNum,bIndent,bOrder �� ��, �� ��� ���
			boardService.writeReplyArticle(boardName,bNum,bOrder,bIndent,bId,bContent);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
	@RequestMapping(value="/board/commentList", produces="application/json; charset=utf8")
	@ResponseBody
	public ResponseEntity commentList(@RequestParam int bNum, HttpServletRequest request,
			@RequestParam String boardName) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		ArrayList<HashMap> hmlist = new ArrayList<>();
		
		//��� �׸�� select �ؿ�
		List<BoardVO> commentVO = boardService.replyBoardLists(boardName, bNum);
		
		//��� �����ϸ�, jsonŸ������ ��ȯ����� �ϴϱ� hashmap �̿�
		if(commentVO.size() > 0) {
			for(int i=0; i < commentVO.size(); i++) {
				HashMap<String, Object> hm = new HashMap<>();
				hm.put("bId",commentVO.get(i).getbId());
				hm.put("bContent",commentVO.get(i).getbContent());
				hm.put("bIndent", commentVO.get(i).getbIndent());
				hm.put("bOrder", commentVO.get(i).getbOrder());
				
				hmlist.add(hm);
			}
		}
		
		//dataType�� json���·� �����߱� ������ JSONArray���·� �����͸� �Ѱ���.
		//json���� ����ִ� Array = JSONArray
		JSONArray json = new JSONArray(hmlist);
		
		//ResponseEntity - �����ڰ� ���� ��� �����Ϳ� HTTP ���� ���� ���� ���� ���, �̰ɷ� ��� ��õ
		//return json.toString();
		return new ResponseEntity(json.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	
	@RequestMapping("/board/{boardName}/deleteArticle/{bNum}")
	public String deleteArticles(@PathVariable String boardName, @PathVariable int bNum, 
			RedirectAttributes rttr) {
		logger.info("remove");
		boolean result = boardService.deleteArticle(boardName, bNum);
		if(result)
			rttr.addFlashAttribute("result","removeOK");
		else
			rttr.addFlashAttribute("result","removeNotOK");
	
		return "redirect:/board/{boardName}/1";
	}
}
