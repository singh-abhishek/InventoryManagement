import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//todo inject logger instead of syso but dont put static logger reference here
//get user id and password from xml

@WebListener
public class IMSServletContextListener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("IMSServletContextListener.contextDestroyed()");
	}

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("IMSServletContextListener.contextInitialized()");
		ServletContext servletContext = sce.getServletContext();
		servletContext.setAttribute("repository", new JdbcItemRepository(
				getMyConnection()));
	}

	public Connection getMyConnection() {
		String connectionURL = "jdbc:mysql://localhost:3306/InventoryManagement";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connectionURL, "root", "root");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;
	}
}