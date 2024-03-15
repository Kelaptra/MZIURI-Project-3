package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.DatabaseManager;
import services.addProduct.AddProductRequest;
import services.addProduct.AddProductResponse;
import services.getProducts.GetProductInfoResponse;
import services.purchase.PurchaseRequest;
import services.purchase.PurchaseResponse;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/store/product")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        GetProductInfoResponse prod_info = DatabaseManager.getDatabaseManager().findProduct(name);
        if(prod_info == null){
            resp.setStatus(405);
            return;
        }
        resp.getWriter().write(prod_info.toJson());
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        PurchaseRequest purchaseRequest = mapper.readValue(body, PurchaseRequest.class);
        PurchaseResponse purchaseResponse = DatabaseManager.getDatabaseManager().purchase(purchaseRequest);
        if(purchaseResponse == null){
            resp.setStatus(405);
            return;
        }
        resp.getWriter().write(purchaseResponse.toJson());
        resp.setStatus(200);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        AddProductRequest addProductRequest = mapper.readValue(body, AddProductRequest.class);
        Object object = DatabaseManager.getDatabaseManager().refillStock(addProductRequest);
        if(object instanceof Integer){
            if((Integer)object == -1){
                resp.setStatus(405);
            }
            else {
                resp.setStatus(403);
            }
            return;
        }
        AddProductResponse addProductResponse = (AddProductResponse)object;
        resp.getWriter().write(addProductResponse.toJson());
        resp.setStatus(200);
    }
}
