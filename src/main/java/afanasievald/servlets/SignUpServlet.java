package afanasievald.servlets;

import afanasievald.interfaces.AccountService;
import afanasievald.dbExecutor.DBException;
import afanasievald.dataSets.*;
import org.apache.logging.log4j.Logger;
import afanasievald.services.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//При получении POST запроса на signup сервлет SignUpServlet должн запомнить логин и пароль в AccountService.
   //     После этого польователь с таким логином считается зарегистрированным.
public class SignUpServlet extends HttpServlet {
  private final AccountService accountService;
  private Logger logger;

  public SignUpServlet(AccountService accountService) {
    logger = LogService.getLogger(SignUpServlet.class.getName());
    this.accountService = accountService;
  }

  public void doPost(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException {
    try {
      logger.info("SignUpServlet doPost");
      String login = request.getParameter("login");
      String password = request.getParameter("password");

      if (login == null || password == null) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return;
      }

      //Может быть не стоит возвращать id? Если ошибка. вылетит исключение, иначе ok
      Long id = accountService.addNewUser(login, password);

      UsersDataSet usersDataSet = accountService.getUserByLogin(login);
      if (usersDataSet != null) {
        accountService.addSession(request.getSession().getId(), usersDataSet);
      }
      SessionDataSet sessionDataSet = accountService.getSessionBySessionId(request.getSession().getId());
      accountService.deleteSession(request.getSession().getId());

      response.setContentType("text/html;charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);
    } catch (DBException e) {
      logger.error(e.getMessage());
      throw new ServletException(e);
    }
  }
}
