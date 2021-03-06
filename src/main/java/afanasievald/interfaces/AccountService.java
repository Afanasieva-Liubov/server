package afanasievald.interfaces;

import afanasievald.dataSets.SessionDataSet;
import afanasievald.dataSets.UsersDataSet;
import afanasievald.dbExecutor.DBException;

public interface AccountService {
  Long addNewUser(String login, String password) throws DBException;

  UsersDataSet getUserByLogin(String login) throws DBException;

  SessionDataSet getSessionBySessionId(String sessionId) throws DBException;

  void addSession(String sessionId, UsersDataSet userProfile) throws DBException;

  void deleteSession(String sessionId) throws DBException;

  void removeUser();

  int getUsersCount();

  int getUsersLimit();

  void setUserLimits(int usersLimit);
}
