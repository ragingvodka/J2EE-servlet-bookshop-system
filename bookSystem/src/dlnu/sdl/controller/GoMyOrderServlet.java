package dlnu.sdl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlnu.sdl.service.MyCart;
@SuppressWarnings("serial")
public class GoMyOrderServlet extends HttpServlet {

	/**
	 * 关于订单信息的操作
	 */
	public GoMyOrderServlet() {
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
	@SuppressWarnings({"unchecked" })
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("</HTML>");
		out.flush();
		out.close();
		*/
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		@SuppressWarnings("unused")
		PrintWriter out = response.getWriter();
		
		MyCart myCart = (MyCart) request.getSession().getAttribute("myCart");
		ArrayList al = myCart.showMyCart();
		float totalPrice = myCart.getTotalPrice();
		request.setAttribute("orderInfo", al);
		request.setAttribute("totalPrice", totalPrice);
		request.getRequestDispatcher("/WEB-INF/jspView/ShowMyOrder.jsp").forward(request, response);
		
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
