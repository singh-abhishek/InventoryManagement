import static org.junit.Assert.*;
import junit.framework.Assert;

import org.eclipse.jetty.testing.HttpTester;
import org.eclipse.jetty.testing.ServletTester;
import org.junit.Test;


public class HelloWorldServletTest {

	@Test
	public void itReturns200OK() throws Exception {
		ServletTester servletTester = new ServletTester();
		servletTester.addServlet(HelloWorldServlet.class, "/Hello");
		servletTester.start();
		
		HttpTester request = new HttpTester();
		request.setMethod("GET");
		request.setURI("/Hello");
		request.setVersion("HTTP/1.0");
		
		HttpTester response = new HttpTester();
		response.parse(servletTester.getResponses(request.generate()));
		System.out.println(response.getStatus());
		Assert.assertEquals(200, response.getStatus());
	}
}
