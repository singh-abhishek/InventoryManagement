import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.eclipse.jetty.testing.HttpTester;
import org.eclipse.jetty.testing.ServletTester;
import org.junit.BeforeClass;
import org.junit.Test;

public class ItemControllerSpecs {

	private static JdbcItemRepository repository = mock(JdbcItemRepository.class);
	private static ServletTester server;

	@BeforeClass
	public static void setUp() throws Exception {
		server = startServer();
	}

	public static ServletTester startServer() throws Exception {
		ServletTester server = new ServletTester();
		server.setContextPath("/");
		server.addServlet(ItemController.class, "/Item");
		server.setAttribute("repository", repository);
		server.start();
		return server;
	}

	@Test
	public void getRequestOnItemsIsSuccessful() throws Exception {
		// Given
		HttpTester request = new HttpTester();
		request.setMethod("GET");
		request.setURI("/Item");
		request.setVersion("HTTP/1.0");

		// When
		String generate = request.generate();
		HttpTester response = new HttpTester();
		response.parse(server.getResponses(generate));

		// Then
		verify(repository, times(1)).findAll();
		assertEquals(200, response.getStatus());
	}

	@Test
	public void postToItemsIsSuccessful() throws Exception {
		// Given
		Item pen = new Item("pen", 10);
		HttpTester request = new HttpTester();
		request.setMethod("POST");
		request.setURI("/Item");
		request.setVersion("HTTP/1.0");
		request.setHeader("Host", "tester");
		request.setHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		request.setContent("itemName=" + pen.getName() + "&itemQty="
				+ pen.getQuantity());

		// When
		String generate = request.generate();
		HttpTester response = new HttpTester();
		response.parse(server.getResponses(generate));

		// Then
		verify(repository, times(1)).add(pen);
		assertEquals(200, response.getStatus());
	}
}
