import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/Item")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemRepository repository;

	@Override
	public void init(ServletConfig config) throws ServletException {
		repository = (ItemRepository) config.getServletContext().getAttribute(
				"repository");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/ItemsView.jsp");
		List<Item> items = repository.findAll();
		Collections.sort(items);
		request.setAttribute("items", items);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("itemName");
		int quantity = Integer.parseInt(request.getParameter("itemQty"));
		Item item = new Item(name, quantity);
		Boolean isAdded = repository.add(item);
		response.setContentType("application/json");
		response.getWriter().append(isAdded.toString());
		response.flushBuffer();
	}
}
