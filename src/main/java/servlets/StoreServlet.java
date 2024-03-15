package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.DatabaseManager;
import services.getProducts.GetProductsResponse;

import java.io.IOException;

@WebServlet("/store")
public class StoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GetProductsResponse getProductsResponse = new GetProductsResponse();
        getProductsResponse.setProductNames(DatabaseManager.getDatabaseManager().selectNames());
        resp.getWriter().write(getProductsResponse.toJson());
    }
}
