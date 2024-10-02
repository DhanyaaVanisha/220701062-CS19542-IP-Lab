import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ProductController")
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/product_management";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Falalala06??"; // Update with your MySQL password

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("view".equals(action)) {
            viewProducts(request, response);
        } else if ("search".equals(action)) {
            searchProducts(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else if ("delete".equals(action)) {
            deleteProduct(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addProduct(request, response);
        } else if ("update".equals(action)) {
            updateProduct(request, response);
        }
    }

    private void viewProducts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sql = "SELECT * FROM products";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>View Products</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Category</th><th>Price</th><th>Stock</th><th>Actions</th></tr>");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + category + "</td>");
                out.println("<td>" + price + "</td>");
                out.println("<td>" + stock + "</td>");
                out.println("<td><a href='ProductController?action=edit&id=" + id + "'>Edit</a> <a href='ProductController?action=delete&id=" + id + "'>Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    private void searchProducts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String query = request.getParameter("query");
        String sql = "SELECT * FROM products WHERE name LIKE ? OR category LIKE ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + query + "%");
            statement.setString(2, "%" + query + "%");
            ResultSet resultSet = statement.executeQuery();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>Search Results</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Category</th><th>Price</th><th>Stock</th><th>Actions</th></tr>");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + category + "</td>");
                out.println("<td>" + price + "</td>");
                out.println("<td>" + stock + "</td>");
                out.println("<td><a href='ProductController?action=edit&id=" + id + "'>Edit</a> <a href='ProductController?action=delete&id=" + id + "'>Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");

                out.println("<html><body>");
                out.println("<h1>Edit Product</h1>");
                out.println("<form action='ProductController' method='post'>");
                out.println("<input type='hidden' name='action' value='update'>");
                out.println("<input type='hidden' name='id' value='" + id + "'>");
                out.println("<label for='name'>Product Name:</label>");
                out.println("<input type='text' id='name' name='name' value='" + name + "' required><br><br>");
                out.println("<label for='category'>Category:</label>");
                out.println("<input type='text' id='category' name='category' value='" + category + "' required><br><br>");
                out.println("<label for='price'>Price:</label>");
                out.println("<input type='number' id='price' name='price' step='0.01' value='" + price + "' required><br><br>");
                out.println("<label for='stock'>Stock Quantity:</label>");
                out.println("<input type='number' id='stock' name='stock' value='" + stock + "' required><br><br>");
                out.println("<input type='submit' value='Update Product'>");
                out.println("</form>");
                out.println("</body></html>");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }

        // Redirect after successful delete to avoid response being committed
        response.sendRedirect(request.getContextPath() + "/ProductController?action=view");
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String price = request.getParameter("price");
        String stock = request.getParameter("stock");

        String sql = "INSERT INTO products (name, category, price, stock) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setBigDecimal(3, new BigDecimal(price));
            statement.setInt(4, Integer.parseInt(stock));
            statement.executeUpdate();
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            return; // Ensure no further processing
        }

        // Redirect after successful add to avoid response being committed
        response.sendRedirect(request.getContextPath() + "/ProductController?action=view");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String price = request.getParameter("price");
        String stock = request.getParameter("stock");

        String sql = "UPDATE products SET name = ?, category = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setBigDecimal(3, new BigDecimal(price));
            statement.setInt(4, Integer.parseInt(stock));
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            return; // Ensure no further processing
        }

        // Redirect after successful update to avoid response being committed
        response.sendRedirect(request.getContextPath() + "/ProductController?action=view");
    }
}
