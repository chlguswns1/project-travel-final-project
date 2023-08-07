package com.springproj.spot.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.springproj.biz.user.service.UserService;
import com.springproj.spot.bookmark.repository.BookMarkVO;
import com.springproj.spot.bookmark.service.BookMakrService;
import com.springproj.spot.likes.repository.LikesVO;
import com.springproj.spot.likes.service.LikesService;
import com.springproj.spot.reply.repository.ReplyVO;
import com.springproj.spot.reply.service.ReplyService;
import com.springproj.spot.repository.TravelSpotVO;
import com.springproj.spot.service.TravelSpotService;
import com.springproj.util.PagingVO;

@Controller
@SessionAttributes("travel")
public class TravelSpotController {
	@Autowired
	TravelSpotService travelSpotService;
	@Autowired
	ReplyService replyService;
	@Autowired
	BookMakrService bookMarkService;
	@Autowired
	UserService userService;
	@Autowired
	LikesService likesService;

	// 검색 조건 목록 설정
	@ModelAttribute("conditionMap") // key
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<>();

		conditionMap.put("관광지", "PLACE");
		conditionMap.put("주소", "ADDRESS");

		return conditionMap; // value
	}

	// 비회원 목록보여주기
	@GetMapping(value = "/getTravelList.do")
	public String getTravelList(PagingVO vo, Model model,
			@RequestParam(value = "searchCondition", required = false) String searchCondition,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(value = "nowPage", required = false) String nowPage,
			@RequestParam(value = "cntPerPage", required = false) String cntPerPage) {
		if (vo.getSearchCondition() == null) {
			vo.setSearchCondition("PLACE");
		} else {
		}
		if (vo.getSearchKeyword() == null) {
			vo.setSearchKeyword("");
		}

		int total = travelSpotService.countBoard(vo);
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "8";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) {
			cntPerPage = "8";
		}

		vo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage), searchCondition,
				searchKeyword);
		System.out.println(vo);
		model.addAttribute("paging", vo);
		model.addAttribute("travelList", travelSpotService.getTravelSpotList(vo));
		// List<TravelSpotVO> list = travelSpotService.getTravelSpotList(vo);
		// model.addAttribute("travelList", list);
		model.addAttribute("top3", travelSpotService.getTopThree());
		return "getTravelList";
	}

	// 명소 목록 관리자 보기
	@RequestMapping(value = "/getTravelSpotList.do")
	public String getTravelSpotList(PagingVO vo, Model model,
			@RequestParam(value = "searchCondition", required = false) String searchCondition,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(value = "nowPage", required = false) String nowPage,
			@RequestParam(value = "cntPerPage", required = false) String cntPerPage) {
		if (vo.getSearchCondition() == null) {
			vo.setSearchCondition("PLACE");
		}
		if (vo.getSearchKeyword() == null) {
			vo.setSearchKeyword("");
		}
		int total = travelSpotService.countBoard(vo);
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "8";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) {
			cntPerPage = "8";
		}
		System.out.println(nowPage + cntPerPage);
		vo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage), searchCondition,
				searchKeyword);
		System.out.println(vo);
		model.addAttribute("paging", vo);
		model.addAttribute("travelList", travelSpotService.getTravelSpotList(vo));
		model.addAttribute("top3", travelSpotService.getTopThree());

		return "getTravelSpotList";
	}

	// 명소 목록 회원
	@RequestMapping(value = "/getTravelSpotListuser.do")
	public String getTravelSpotListuser(PagingVO vo, Model model, HttpServletRequest request, BookMarkVO bookMarkVO,
			@RequestParam(value = "searchCondition", required = false) String searchCondition,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(value = "nowPage", required = false) String nowPage,
			@RequestParam(value = "cntPerPage", required = false) String cntPerPage) {
		if (vo.getSearchCondition() == null) {
			vo.setSearchCondition("PLACE");
		}
		if (vo.getSearchKeyword() == null) {
			vo.setSearchKeyword("");
		}

		int total = travelSpotService.countBoard(vo);
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "8";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) {
			cntPerPage = "8";
		}
		System.out.println(nowPage + cntPerPage); // 18에서 28이 뜸.
		vo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage), searchCondition,
				searchKeyword);
		System.out.println(vo);
		model.addAttribute("paging", vo);
		model.addAttribute("travelList", travelSpotService.getTravelSpotList(vo));
		model.addAttribute("top3", travelSpotService.getTopThree());
		
		model.addAttribute("checkMark", request.getParameter("checkMark")); 
		model.addAttribute("checksseq", request.getParameter("seq_travel"));
		model.addAttribute("checkseq", request.getParameter("checkseq"));
		

		return "getTravelSpotListuser";
	}

	// 명소 상세 조회(비회원)
	@RequestMapping(value = "/getTravelSpotNonUser.do")
	public String getTravelSpotNonUsers(TravelSpotVO vo, ReplyVO replyVO, LikesVO likes, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sessionId = session.getId();

		Integer viewCnt = (Integer) session.getAttribute("viewCnt_" + vo.getSeq());

		if (!sessionId.equals(session.getAttribute("viewSessionId_" + vo.getSeq()))) {
			travelSpotService.incrementViewCount(vo.getSeq());
			session.setAttribute("viewSessionId_" + vo.getSeq(), sessionId);
			viewCnt = (viewCnt == null) ? 1 : viewCnt + 1;
		} else {
			viewCnt = (viewCnt == null) ? 0 : viewCnt;
		}

		session.setAttribute("viewCnt_" + vo.getSeq(), viewCnt);
		
		// +) 댓글 조회 호출!!
		List<ReplyVO> list = replyService.getReplyList(replyVO);
		model.addAttribute("replyList", list);

		TravelSpotVO travel = travelSpotService.getTravelSpot(vo.getSeq());
		model.addAttribute("travel", travel);
		
		// +) 좋아요 기능(게시물번호마다 하트 셋팅)
		if ((String) session.getAttribute("id") != null) {
			likes.setMEMBER_ID((String) session.getAttribute("id"));

			int result = likesService.checkLikeSelect(likes);
			session.setAttribute("result", result);
		}

		return "getTravelSpotNonUser";
	}
	
	// 명소 상세 조회(관리자)
	@RequestMapping(value = "/getTravelSpot.do")
	public String getTravelSpot(TravelSpotVO vo, ReplyVO replyVO, LikesVO likes, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sessionId = session.getId();

		Integer viewCnt = (Integer) session.getAttribute("viewCnt_" + vo.getSeq());

		if (!sessionId.equals(session.getAttribute("viewSessionId_" + vo.getSeq()))) {
			travelSpotService.incrementViewCount(vo.getSeq());
			session.setAttribute("viewSessionId_" + vo.getSeq(), sessionId);
			viewCnt = (viewCnt == null) ? 1 : viewCnt + 1;
		} else {
			viewCnt = (viewCnt == null) ? 0 : viewCnt;
		}

		session.setAttribute("viewCnt_" + vo.getSeq(), viewCnt);
		
		// +) 댓글 조회 호출!!
		List<ReplyVO> list = replyService.getReplyList(replyVO);
		model.addAttribute("replyList", list);

		TravelSpotVO travel = travelSpotService.getTravelSpot(vo.getSeq());
		model.addAttribute("travel", travel);
		
		// +) 좋아요 기능(게시물번호마다 하트 셋팅)
		if ((String) session.getAttribute("id") != null) {
			likes.setMEMBER_ID((String) session.getAttribute("id"));

			int result = likesService.checkLikeSelect(likes);
			session.setAttribute("result", result);
		}
		
		
		return "getTravelSpot";
	}

	// 명소 상세 조회(회원)
	@RequestMapping(value = "/getTravelSpotuser.do")
	public String getTravelSpotuser(TravelSpotVO vo, ReplyVO replyVO, LikesVO likes, Model model,
			HttpServletRequest request) {
		// travelSpotService.updateCnt(vo.getSeq());
		HttpSession session = request.getSession();
		String sessionId = session.getId();

		Integer viewCnt = (Integer) session.getAttribute("viewCnt_" + vo.getSeq());

		if (!sessionId.equals(session.getAttribute("viewSessionId_" + vo.getSeq()))) {
			travelSpotService.incrementViewCount(vo.getSeq());
			session.setAttribute("viewSessionId_" + vo.getSeq(), sessionId);
			viewCnt = (viewCnt == null) ? 1 : viewCnt + 1;
		} else {
			viewCnt = (viewCnt == null) ? 0 : viewCnt;
		}

		session.setAttribute("viewCnt_" + vo.getSeq(), viewCnt);

		// +) 댓글 조회 호출!!
		List<ReplyVO> list = replyService.getReplyList(replyVO);
		model.addAttribute("replyList", list);

		TravelSpotVO travel = travelSpotService.getTravelSpot(vo.getSeq());
		model.addAttribute("travel", travel);
		
		// +) 좋아요 기능(게시물번호마다 하트 셋팅)
		if ((String) session.getAttribute("id") != null) {
			likes.setMEMBER_ID((String) session.getAttribute("id"));

			int result = likesService.checkLikeSelect(likes);
			session.setAttribute("result", result);
		}

		return "getTravelSpotuser";
	}

	// 명소 등록 화면
	@RequestMapping(value = "/insertTravelSpot.do", method = RequestMethod.GET)
	public String insertTravelSpotForm() {
		return "insertTravelSpotForm";
	}

	// 명소 등록 처리
	@RequestMapping(value = "/insertTravelSpot.do", method = RequestMethod.POST)
	public String insertTravelSpotProc(TravelSpotVO vo, HttpSession session) {

		// 파일 업로드 처리
		String fileSaveFolder = session.getServletContext().getRealPath("/boardUpload/admin/");

		MultipartFile uploadFile1 = vo.getUploadFile1();
		MultipartFile uploadFile2 = vo.getUploadFile2();
		MultipartFile uploadFile3 = vo.getUploadFile3();
		MultipartFile uploadFile4 = vo.getUploadFile4();

		vo.setImageFile1("");
		vo.setImageFile2("");
		vo.setImageFile3("");
		vo.setImageFile4("");

		String fileName1 = uploadFile1.getOriginalFilename();
		System.out.println(fileName1);
		String fileName2 = uploadFile2.getOriginalFilename();
		System.out.println(fileName2);
		String fileName3 = uploadFile3.getOriginalFilename();
		System.out.println(fileName3);
		String fileName4 = uploadFile4.getOriginalFilename();
		System.out.println(fileName4);

		try {
			if (!uploadFile1.isEmpty() || !uploadFile2.isEmpty() || !uploadFile3.isEmpty() || !uploadFile4.isEmpty()) {

				uploadFile1.transferTo(new File(fileSaveFolder + fileName1));
				vo.setImageFile1(fileName1);
				uploadFile2.transferTo(new File(fileSaveFolder + fileName2));
				vo.setImageFile2(fileName2);
				uploadFile3.transferTo(new File(fileSaveFolder + fileName3));
				vo.setImageFile3(fileName3);
				uploadFile4.transferTo(new File(fileSaveFolder + fileName4));
				vo.setImageFile4(fileName4);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		travelSpotService.insertTravelSpot(vo);

		return "redirect:getTravelSpotList.do";
	}

	// 명소 수정 화면
	@RequestMapping(value = "/updateTravelSpot.do", method = RequestMethod.GET)
	public String updateTravelSpotForm(TravelSpotVO vo, Model model) {

		TravelSpotVO travel = travelSpotService.getTravelSpot(vo.getSeq());
		model.addAttribute("travel", travel);

		return "updateTravelSpotForm";
	}

	// 명소 수정 처리
	   @RequestMapping(value = "/updateTravelSpot.do", method = RequestMethod.POST)
	   public String updateTravelSpotProc(@ModelAttribute("travel") TravelSpotVO vo, HttpSession session) {

	      // 파일 업로드 처리
	      String fileSaveFolder = session.getServletContext().getRealPath("/boardUpload/admin/");

	      MultipartFile uploadFile1 = vo.getUploadFile1();
	      MultipartFile uploadFile2 = vo.getUploadFile2();
	      MultipartFile uploadFile3 = vo.getUploadFile3();
	      MultipartFile uploadFile4 = vo.getUploadFile4();

	      //원본 파일명 저장
	      TravelSpotVO origin = travelSpotService.getTravelSpot(vo.getSeq());
	      String fileName1 = origin.getImageFile1();
	      String fileName2 = origin.getImageFile2();
	      String fileName3 = origin.getImageFile3();
	      String fileName4 = origin.getImageFile4();
	      
	      //수정한 파일만 이름 초기화
	      if(uploadFile1.getSize()>0) {
	         vo.setImageFile1("");
	         fileName1 = uploadFile1.getOriginalFilename();
	         System.out.println(fileName1);
	      }
	      
	      if(uploadFile2.getSize()>0) {
	         vo.setImageFile2("");
	         fileName2 = uploadFile2.getOriginalFilename();
	         System.out.println(fileName2);
	      }
	      
	      if(uploadFile3.getSize()>0) {
	         vo.setImageFile3("");
	         fileName3 = uploadFile3.getOriginalFilename();
	         System.out.println(fileName3);
	      }
	      
	      if(uploadFile4.getSize()>0) {
	         vo.setImageFile4("");
	         fileName4 = uploadFile4.getOriginalFilename();
	         System.out.println(fileName4);
	      }

	      
	      try {
	         if (!uploadFile1.isEmpty() || !uploadFile2.isEmpty() || !uploadFile3.isEmpty() || !uploadFile4.isEmpty()) {

	            uploadFile1.transferTo(new File(fileSaveFolder + fileName1));
	            vo.setImageFile1(fileName1);
	            uploadFile2.transferTo(new File(fileSaveFolder + fileName2));
	            vo.setImageFile2(fileName2);
	            uploadFile3.transferTo(new File(fileSaveFolder + fileName3));
	            vo.setImageFile3(fileName3);
	            uploadFile4.transferTo(new File(fileSaveFolder + fileName4));
	            vo.setImageFile4(fileName4);
	         }

	      } catch (IOException e) {
	         e.printStackTrace();
	      }

	      travelSpotService.updateTravelSpot(vo);

	      return "redirect:getTravelSpotList.do";
	   }
	// 명소 삭제 처리
	@RequestMapping(value = "/deleteTravelSpot.do")
	public String deleteTravelSpot(int seq) {
		travelSpotService.deleteTravelSpot(seq);
		bookMarkService.deleteBookmark(seq);
		return "redirect:getTravelSpotList.do";
	}

	

}
