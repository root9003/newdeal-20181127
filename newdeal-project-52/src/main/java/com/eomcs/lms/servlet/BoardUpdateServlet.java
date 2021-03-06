package com.eomcs.lms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

//spring IoC 컨테이너에게 이 클래스의 인스턴스를 자동 생성하도록 클래스에 표시해 둔다.
@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;
  BoardDao boardDao;
  
  public void init() {
    ApplicationContext iocContainer = (ApplicationContext)this.getServletContext().getAttribute("iocContainer");
    this.boardDao = iocContainer.getBean(BoardDao.class);
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    try {
      Board board = new Board();
      board.setNo(Integer.parseInt(request.getParameter("no")));
      board.setContents(request.getParameter("contents"));
      
      boardDao.update(board);
    
      // 데이터를 변경한 후 웹 브라우저에게 목록 URL을 다시 요청 하라고 응답한다.
      response.sendRedirect("list");
      
    } catch (Exception e){
      e.printStackTrace();
      throw new ServletException(e);
    }
  }
}
