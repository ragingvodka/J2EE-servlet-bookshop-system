package dlnu.sdl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlnu.sdl.domain.Book;
import dlnu.sdl.service.BookService;

@SuppressWarnings("serial")
public class BookClServlet extends HttpServlet {

	/**
	 * ͼ������servlet
	 */
	public BookClServlet() {
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

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		if (request.getSession().getAttribute("admin") != null) {
			String flag = request.getParameter("flag");
			BookService bookService = new BookService();
			//��ʾ�����鼮
			if (flag.equals("paging")) {
				// ���ڿ�������ҳ��ʾ
				// String s_pageNow = request.getParameter("pageNow");
				ArrayList<?> bookal = bookService.getAllBook();
				request.setAttribute("bookAl", bookal);
				request.getRequestDispatcher("/WEB-INF/jspView/showAllBook.jsp")
				.forward(request, response);
			}
			// ɾ���鼮
			else if (flag.equals("delete")) {
				String id = request.getParameter("id");
				int[] bookid = { Integer.parseInt(id) };
				if (bookService.deleteBook(bookid)) {
					// ɾ���ɹ� ���»�ȡҳ��
					ArrayList<?> bookal = bookService.getAllBook();
					request.setAttribute("bookAl", bookal);
					request.getRequestDispatcher("/WEB-INF/jspView/showAllBook.jsp")
					.forward(request, response);
				} else {
					// ɾ��ʧ��
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
					out.println("<HTML>");
					out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
					out.println("  <BODY>");
					out.print("<script>alert(\"delete book Fail\");window.location.href='BookClServlet?flag=paging';</script>");
					out.println("  </BODY>");
					out.println("</HTML>");
					out.flush();
					out.close();
				}
			}
			// ��ʾ����鼮����
			else if (flag.equals("addBookshow")) {
				request.getRequestDispatcher(
						"/WEB-INF/jspView/addBook.jsp").forward(request,
						response);
			}
			// ����鼮
			else if (flag.equals("addBook")) {
				if (request.getParameter("id") != null) {
					Book addbook = new Book();
					addbook.setId(Integer.parseInt(request.getParameter("id").toString()));
					addbook.setName(request.getParameter("name").toString());
					addbook.setAuthor(request.getParameter("author").toString());
					addbook.setPublishHouse(request.getParameter("publishHouse").toString());
					addbook.setPrice(Integer.parseInt(request.getParameter("price").toString()));
					addbook.setNums(Integer.parseInt(request.getParameter("nums").toString()));
					// Ȼ���user�������ݿ�
					if (bookService.adduser(addbook)) {
						// ��ӳɹ�
						response.setContentType("text/html");
						PrintWriter out = response.getWriter();
						out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
						out.println("<HTML>");
						out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
						out.println("  <BODY>");
						out.print("<script>alert(\"add book success\");window.location.href='BookClServlet?flag=addBookshow';</script>");
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
						out.print("<script>alert(\"add book Fail\");window.location.href='BookClServlet?flag=addBookshow';</script>");
						out.println("  </BODY>");
						out.println("</HTML>");
						out.flush();
						out.close();
					}
				}
			}
			// �޸��鼮������ʾ
			else if (flag.equals("updateBookshow")) {
				//��ȡID����ѯ�������ݣ���ʾ
				int id = 1;
				if(request.getParameter("id")!=null)
					id = Integer.parseInt(request.getParameter("id").toString());
				ArrayList<?> book = bookService.searchIDBook(id);//�������ʹ�ò�ѯ����
				request.setAttribute("bookAl", book);
				request.getRequestDispatcher("/WEB-INF/jspView/updateBook.jsp")
						.forward(request, response);
			}
			
			//�޸��鼮����
			else if (flag.equals("updateBook")) {
				//��ȡ���ݲ�ת�����ݿ��޸�
				Book book = new Book();
				book.setId(Integer.parseInt(request.getParameter("id").toString()));
				book.setName(request.getParameter("name").toString());
				book.setAuthor(request.getParameter("author").toString());
				book.setPublishHouse(request.getParameter("publishHouse").toString());
				book.setPrice(Float.parseFloat(request.getParameter("price").toString()));
				book.setNums(Integer.parseInt(request.getParameter("nums").toString()));
				if (bookService.updateBook(book)) {
					// �޸ĳɹ� ���»�ȡҳ��
					ArrayList<?> bookal = bookService.getAllBook();
					request.setAttribute("bookAl", bookal);
					request.getRequestDispatcher("/WEB-INF/jspView/showAllBook.jsp")
					.forward(request, response);
				} else {
					// �޸�ʧ��
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
					out.println("<HTML>");
					out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
					out.println("  <BODY>");
					out.print("<script>alert(\"update user Fail\");window.location.href='BookClServlet?flag=paging';</script>");
					out.println("  </BODY>");
					out.println("</HTML>");
					out.flush();
					out.close();
				}
			}
			//��ѯ�鼮 ʹ��showallbook��ʾ
			else if (flag.equals("searchBook")) {
				if (request.getParameter("name") != null) {
					// ִ�в�ѯ���ص�showalluser
					String name = request.getParameter("name");
					ArrayList<?> bookAl = bookService.searchNameBook(name);
					request.setAttribute("bookAl", bookAl);
					request.getRequestDispatcher("/WEB-INF/jspView/showAllBook.jsp").forward(request, response);
				} else {
					// ��ʾ�����û�
					ArrayList<?> bookAl = bookService.getAllBook();
					request.setAttribute("bookAl", bookAl);
					request.getRequestDispatcher("/WEB-INF/jspView/showAllBook.jsp").forward(request, response);
				}

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
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
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
