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
 * session会话维持
 *	如果提交的表单不为空则验证密码：
 *			如果验证通过  
 *					1 将当前session添加到Map集合   
 *					2 将session id设置为Cookie value
 *					3 将Cookie发送为浏览器  
 *			 		3 转发到登陆成功页面
 *			如果验证不通过
 *					1转发到登陆页面
 *	如果提交的表单为空
 *			遍历所有Cookie
 *					如果找不到名为session的Cookie
 *						   转发到登陆页面
 *					如果找到  
 *						1  以Cookie的value值从Map集合中取出对应 session 覆盖当前session
 *						2 创建Cookie new Cookie("JSESSIONID" , session.getId())xczxczxzcxzc
 *										setPath()设为 默认Cookie.name=JESSIONID的Path
 *										设置存活时间自定义
 *										 覆盖浏览器默认的Cookie JESSIONID
 *										至此JESSIONID达成不随浏览器关闭的效果
 *						3  转发到登陆成功页面
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
		 System.out.println("Service 当前会话ID："+session.getId());
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
						 System.out.println("转发");
						 req.getRequestDispatcher(urlindex).forward(req, res);
				 }  
			 }
			 if(cookiename==false){
				 System.out.println("Service to Login 当前会话ID："+session.getId());
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
