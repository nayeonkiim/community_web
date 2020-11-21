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
	
	
	//전체 페이지 보여주기
	@RequestMapping(value="/board/{boardName}/{page}")
	public String toBoardViewpage(@PathVariable("boardName") String boardName,
			@PathVariable("page") int page, @RequestParam(value="type", required=false, defaultValue= "0") String type, 
			@RequestParam(value="searchValue", required=false, defaultValue= "0") String searchValue, Model model) {
		
		Map<String,Object> board = null;
		int[] block = null;
		if(type == "0" || type == null) {
			block = boardService.blockInfo(boardName, page, "null","null");
			if(block[1] > 0) {
				//글 정보에 대해
			board = boardService.ArticleInfo(boardName, page, "null", "null");
			model.addAttribute("type",null);
			model.addAttribute("searchValue",null);
		}
		}else {
			block = boardService.blockInfo(boardName, page, type,searchValue);
			if(block[1] > 0) {
				//글 정보에 대해
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
	
	//댓글과 새로운 글쓰기
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
		//조회수 증가
		boolean result = boardService.addCountFrequency(boardName,bNum);
		
		//BoardVO 객체 select
		BoardVO board = boardService.readArticleInfo(boardName, bNum);
		model.addAttribute("boardName", boardName);
		model.addAttribute("board", board);
		model.addAttribute("page", page);
		model.addAttribute("bNum", bNum);
		//답변 글 select
		
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
	
	//답변 등록하기 ajax
	@RequestMapping("/board/reply")
	@ResponseBody
	public String addComment(@RequestParam int bIndent, @RequestParam int bOrder,
			@RequestParam String boardName, @RequestParam int bNum, @RequestParam String bId,
			@RequestParam String bContent, HttpServletRequest request) {
		try {
			//부모의 bNum,bIndent,bOrder 가 들어가, 새 댓글 등록
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
		
		//댓글 항목들 select 해옴
		List<BoardVO> commentVO = boardService.replyBoardLists(boardName, bNum);
		
		//댓글 존재하면, json타입으로 반환해줘야 하니깐 hashmap 이용
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
		
		//dataType을 json형태로 지정했기 때문에 JSONArray형태로 데이터를 넘겨줌.
		//json들이 들어있는 Array = JSONArray
		JSONArray json = new JSONArray(hmlist);
		
		//ResponseEntity - 개발자가 직접 결과 데이터와 HTTP 상태 정보 보기 위해 사용, 이걸로 사용 추천
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
