package dlnu.sdl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlnu.sdl.domain.Users;
import dlnu.sdl.service.UsersService;

@SuppressWarnings("serial")
public class UserClServlet extends HttpServlet {

	/**
	 * �����û������servlet
	 */
	public UserClServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		if (request.getSession().getAttribute("admin") != null) {
			// ��ñ�ʶ��
			String flag = request.getParameter("flag");
			// ��һֱ����UsersService�ķ���
			UsersService usersService = new UsersService();
			// ��ʾ�����û�
			if (flag.equals("paging")) {
				// �õ��û�ϣ����ʾ��pageNow
				// String s_pageNow = request.getParameter("pageNow");

				ArrayList<?> userAl = usersService.getAllUsers();
				request.setAttribute("userAl", userAl);
				request.getRequestDispatcher("/WEB-INF/jspView/showAllUser.jsp")
						.forward(request, response);

			}
			// ɾ���û�
			else if (flag.equals("delete")) {
				String id = request.getParameter("id");
				int[] users = { Integer.parseInt(id) };
				if (usersService.deleteUser(users)) {
					// ɾ���ɹ� ���»�ȡҳ��
					ArrayList<?> userAl = usersService.getAllUsers();
					request.setAttribute("userAl", userAl);
					request.getRequestDispatcher(
							"/WEB-INF/jspView/showAllUser.jsp").forward(
							request, response);
				} else {
					// ɾ��ʧ��
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
					out.println("<HTML>");
					out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
					out.println("  <BODY>");
					out.print("<script>alert(\"delete user Fail\");window.location.href='UserClServlet?flag=paging';</script>");
					out.println("  </BODY>");
					out.println("</HTML>");
					out.flush();
					out.close();
				}
			}
			// ��ʾ����û�����
			else if (flag.equals("addUsershow")) {
				request.getRequestDispatcher(
						"/WEB-INF/jspView/adminadduser.jsp").forward(request,
						response);
			}
			// ����û�
			else if (flag.equals("addUser")) {
				if (request.getParameter("id") != null
						&& request.getParameter("password") != null) {
					// �������ȫ������
					Users adduser = new Users(Integer.parseInt(request
							.getParameter("id").toString()),
							request.getParameter("password"));
					adduser.setEmail(request.getParameter("email").toString());
					adduser.setGrade(Integer.parseInt(request.getParameter(
							"grade").toString()));
					adduser.setName(request.getParameter("name").toString());
					adduser.setTelephone(request.getParameter("telephone")
							.toString());
					// Ȼ���user�������ݿ�
					if (usersService.adduser(adduser)) {
						// ��ӳɹ�
						response.setContentType("text/html");
						PrintWriter out = response.getWriter();
						out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
						out.println("<HTML>");
						out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
						out.println("  <BODY>");
						out.print("<script>alert(\"add user success\");window.location.href='UserClServlet?flag=addUsershow';</script>");
						out.println("  </BODY>");
						out.println("</HTML>");
						out.flush();
						out.close();
					} else {
						// ���ʧ��
						response.setContentType("text/html");
						PrintWriter out = response.getWriter();
						out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
						out.println("<HTML>");
						out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
						out.println("  <BODY>");
						out.print("<script>alert(\"add user Fail\");window.location.href='UserClServlet?flag=addUsershow';</script>");
						out.println("  </BODY>");
						out.println("</HTML>");
						out.flush();
						out.close();
					}
				}
			}
			// �޸��û�������ʾ
			else if (flag.equals("updateUsershow")) {
				//��ȡID����ѯ�������ݣ���ʾ
				int id = 1;
				if(request.getParameter("id")!=null)
					id = Integer.parseInt(request.getParameter("id").toString());
				ArrayList<?> userAl = usersService.searchIDUser(id);//�������ʹ�ò�ѯ����
				request.setAttribute("userAl", userAl);
				request.getRequestDispatcher("/WEB-INF/jspView/updateUser.jsp")
						.forward(request, response);
			}
			
			//�޸��û�����
			else if (flag.equals("updateUser")) {
				//��ȡ���ݲ�ת�����ݿ��޸�
				Users user = new Users(Integer.parseInt(request.getParameter("id").toString()),request.getParameter("password").toString());
				user.setName(request.getParameter("name").toString());
				user.setEmail(request.getParameter("email").toString());
				user.setTelephone(request.getParameter("telephone").toString());
				user.setGrade(Integer.parseInt(request.getParameter("grade").toString()));
				if (usersService.updateUser(user)) {
					// �޸ĳɹ� ���»�ȡҳ��
					ArrayList<?> userAl = usersService.getAllUsers();
					request.setAttribute("userAl", userAl);
					request.getRequestDispatcher(
							"/WEB-INF/jspView/showAllUser.jsp").forward(
							request, response);
				} else {
					// �޸�ʧ��
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
					out.println("<HTML>");
					out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
					out.println("  <BODY>");
					out.print("<script>alert(\"update user Fail\");window.location.href='UserClServlet?flag=paging';</script>");
					out.println("  </BODY>");
					out.println("</HTML>");
					out.flush();
					out.close();
				}
			}
			// ��ѯ�û� ʹ���û������� ʹ��showalluser��ʾ
			else if (flag.equals("searchUser")) {
				if (request.getParameter("name") != null) {
					// ִ�в�ѯ���ص�showalluser
					String name = request.getParameter("name");
					/*
					 * ������ò�ѯ����������д��ѯ����
					 * */
					//searchUser(Users user)
					ArrayList<?> userAl = usersService.searchUser(name);
					request.setAttribute("userAl", userAl);
					request.getRequestDispatcher("/WEB-INF/jspView/showAllUser.jsp").forward(request, response);
				} else {
					// ��ʾ�����û�
					ArrayList<?> userAl = usersService.getAllUsers();
					request.setAttribute("userAl", userAl);
					request.getRequestDispatcher("/WEB-INF/jspView/showAllUser.jsp").forward(request, response);
				}

			}

			// ע���û�
			else if (flag.equals("cancleUser")) {
				/*
				 * ���session ��ת�������û���¼�Ľ���
				 * */
				request.getSession().removeAttribute("admin");
				request.getRequestDispatcher("/WEB-INF/jspView/Login.jsp").forward(request, response);
			}
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<script>alert(\"�����Ӳ�����ֱ�ӷ���\");</script>");
			out.flush();
			out.close();
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
