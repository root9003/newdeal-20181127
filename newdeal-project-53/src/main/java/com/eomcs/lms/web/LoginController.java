package com.eomcs.lms.web;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

//spring IoC 컨테이너에게 이 클래스의 인스턴스를 자동 생성하도록 클래스에 표시해 둔다.
@Component("/auth/login")
public class LoginController implements PageController {
  BoardDao boardDao;
  MemberDao memberDao;
  
  public LoginController(BoardDao boardDao, MemberDao memberDao) {
    this.boardDao = boardDao;
    this.memberDao = memberDao;
  }
  
  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
      HashMap<String, Object> params = new HashMap<>();
      params.put("email", request.getParameter("email"));
      params.put("password", request.getParameter("password"));
      
      Member member = memberDao.findByEmailPassword(params);
      
      // 해당 클라이언트를 위한 HttpSession 객체가 있다면, 그 객체를 리턴한다. 없다면, 새로 만들어 리턴한다.
      HttpSession session = request.getSession();
      
      if(member != null) {
        session.setAttribute("loginUser", member);
        return "redirect:../board/list";
      }else {
        session.invalidate();
        return "redirect:form";
      }
  }
}
