package com.Grace;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * session�Ựά��
 *	����ύ�ı���Ϊ������֤���룺
 *			�����֤ͨ��  
 *					1 ����ǰsession��ӵ�Map����   
 *					2 ��session id����ΪCookie value
 *					3 ��Cookie����Ϊ�����  
 *			 		3 ת������½�ɹ�ҳ��
 *			�����֤��ͨ��
 *					1ת������½ҳ��
 *	����ύ�ı�Ϊ��
 *			��������Cookie
 *					����Ҳ�����Ϊsession��Cookie
 *						   ת������½ҳ��
 *					����ҵ�  
 *						1  ��Cookie��valueֵ��Map������ȡ����Ӧ session ���ǵ�ǰsession
 *						2 ����Cookie new Cookie("JSESSIONID" , session.getId())xczxczxzcxzc
 *										setPath()��Ϊ Ĭ��Cookie.name=JESSIONID��Path
 *										���ô��ʱ���Զ���
 *										 ���������Ĭ�ϵ�Cookie JESSIONID
 *										����JESSIONID��ɲ���������رյ�Ч��
 *						3  ת������½�ɹ�ҳ��
 *
 */
 
@WebServlet("/SessionLogin")
public class SessionLogin extends HttpServlet {
 
	private static final long serialVersionUID = -2476086633688596839L;
	private HashMap<String, HttpSession> sessions=new HashMap<String, HttpSession>();
	private boolean cookiename;
	private String username ;
	private String password ;
	
	 
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		 req.setCharacterEncoding("utf-8");
		 res.setContentType("text/html;charset=utf-8");
		 PrintWriter o = res.getWriter();
		 o.append("This' Service!");
		 HttpSession session = req.getSession();
		 System.out.println("Service ��ǰ�ỰID��"+session.getId());
		 username = req.getParameter("username");
		 password = req.getParameter("password");
		 Cookie[] cookies=req.getCookies();
		 cookies=cookies==null?new Cookie[0]:cookies;
		 String urlindex=res.encodeURL("index.jsp");
		 String urllogin=res.encodeRedirectURL("Login.jsp");
		 
		 if(username==null&&password==null||username==null||password==null){
			 System.out.println("Submission is empty");
			 for(Cookie cookie:cookies){
				 if(cookie.getName().equals("session")){
					 System.out.println("Submission is empty1");
					 HttpSession sessionold= sessions.get(cookie.getValue());
					 session=sessionold==null?session:sessionold;
						 cookiename=true;
						 Cookie c = new Cookie("JSESSIONID" , session.getId());
						 c.setPath("/SessionLogin/");
						 c.setMaxAge(60);
						 res.addCookie(c);
						 System.out.println("ת��");
						 req.getRequestDispatcher(urlindex).forward(req, res);
				 }  
			 }
			 if(cookiename==false){
				 System.out.println("Service to Login ��ǰ�ỰID��"+session.getId());
				 req.getRequestDispatcher(urllogin).forward(req, res);
			 }
		 }else{
			 System.out.println("Login verification");
			 if(username.equals("root")&&password.equals("root")){
			      System.out.println("Verify success!");
				  sessions.put(session.getId(), session);
			      Cookie cookie = new Cookie("session" , session.getId());
				  cookie.setMaxAge(60);
				  res.addCookie(cookie);
			      req.getRequestDispatcher(urlindex).forward(req, res);
			 }else{
				 System.out.println("Verify failed!");
				 req.getRequestDispatcher(urllogin).forward(req, res);  
			 }
		 }
	}
}
