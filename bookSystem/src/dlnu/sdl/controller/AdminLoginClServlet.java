package dlnu.sdl.controller;

import java.io.IOException;
import java.io.PrintWriter;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlnu.sdl.domain.Users;
import dlnu.sdl.service.UsersService;;

@SuppressWarnings("serial")
public class AdminLoginClServlet extends HttpServlet {

	/**
	 * ������֤����Ա��½��servlet
	 */
	public AdminLoginClServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		//�ж��Ƿ��¼
		if(request.getSession().getAttribute("admin")!=null){
			request.getRequestDispatcher("/WEB-INF/jspView/adminmain.jsp").forward(request, response);
		}
		//��ȡ�û�����������ж�
		if(request.getParameter("id")!=null&&request.getParameter("password")!=null){
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			Users Admin = new Users(Integer.parseInt(id),password);
			UsersService usersService = new UsersService();
			if(usersService.checkadmin(Admin)){
				request.getSession().setAttribute("admin", Admin);
				//����Ա����������ֻ���Զ���Ʒ���û���ɾ�Ĳ�
				/**
				 * 
				 * ������ת����½��Ľ���
				 * 
				 * */
				request.getRequestDispatcher("/WEB-INF/jspView/adminmain.jsp").forward(request, response);
			}else {
				//�û����������벻��ȷ/bookSystem/WebRoot/WEB-INF/jspView/adminlogin.jsp
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out
						.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
				out.println("<HTML>");
				out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
				out.println("  <BODY>");
				out
						.print("<script>alert(\"�û��������벻��ȷ\");window.location.href='adminLogin.jsp';</script>");
				out.println("  </BODY>");
				out.println("</HTML>");
				out.flush();
				out.close();
			}
		}else{
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<script>alert(\"�û��������벻��Ϊ��\");</script>");
			//request.getRequestDispatcher("/WEB-INF/jspView/Login.jsp").forward(request, response);
			out.flush();
			out.close();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
