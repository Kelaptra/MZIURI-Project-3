package services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import model.StorageConfig;
import model.Product;
import services.addProduct.AddProductRequest;
import services.addProduct.AddProductResponse;
import services.getProducts.GetProductInfoResponse;
import services.purchase.PurchaseRequest;
import services.purchase.PurchaseResponse;

import java.util.List;

public class DatabaseManager {
    private static DatabaseManager databaseManager;
    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final CriteriaBuilder cb;


    private DatabaseManager(){
        emf = Persistence.createEntityManagerFactory("CandyShop-unit");
        em = emf.createEntityManager();
        cb = em.getCriteriaBuilder();
    }

    public static DatabaseManager getDatabaseManager() {
        if(databaseManager == null){
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }

    public boolean checkIfDBIsEmpty(){
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root);
        TypedQuery<Product> typedQuery = em.createQuery(cq);
        List<Product> list = typedQuery.getResultList();
        return list.isEmpty();
    }

    public void addStartingProducts(StorageConfig storageConfig){
        if(!checkIfDBIsEmpty()) return;
        em.getTransaction().begin();
        for(Product product : storageConfig.getProducts()){
            System.out.println(product);
        }
        for(Product product : storageConfig.getProducts()){
            Product attachedProduct = em.merge(product);
            em.persist(attachedProduct);
        }
        em.getTransaction().commit();
    }

    public String[] selectNames(){
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root.get("prod_name"));
        TypedQuery<String> typedQuery = em.createQuery(cq);
        List<String> list = typedQuery.getResultList();
        return list.toArray(new String[0]);
    }

    public GetProductInfoResponse findProduct(String name){
        StorageReader.getStorageReader();

        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root).where(cb.equal(root.get("prod_name"), name));
        TypedQuery<Product> typedQuery = em.createQuery(cq);
        List<Product> list = typedQuery.getResultList();
        System.out.println(list);
        if(list.isEmpty()) return null;
        Product product = list.get(0);
        return new GetProductInfoResponse(product);
    }

    public PurchaseResponse purchase(PurchaseRequest purchaseRequest){
        StorageReader.getStorageReader();

        String name = purchaseRequest.getName();
        Integer amount = purchaseRequest.getAmount();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        Predicate predicate1 = cb.equal(root.get("prod_name"), name);
        Predicate predicate2 = cb.greaterThanOrEqualTo(root.get("prod_amount"), amount);
        cq.select(root).where(cb.and(predicate1, predicate2));
        TypedQuery<Product> typedQuery = em.createQuery(cq);
        List<Product> list = typedQuery.getResultList();
        if(list.isEmpty()) return null;
        Product product = list.get(0);
        product.changeAmountBy(-amount);
        updateDatabaseProductAmount(product);
        return new PurchaseResponse(product);
    }

    public Object refillStock(AddProductRequest addProductRequest){
        String password = StorageReader.getStorageReader().readStorage().getPassword();
        if(!addProductRequest.getPassword().equals(password)){
            return 0;
        }
        if(findProduct(addProductRequest.getName()) == null){
            return -1;
        }
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        String name = addProductRequest.getName();
        cq.select(root).where(cb.equal(root.get("prod_name"), name));
        TypedQuery<Product> typedQuery = em.createQuery(cq);
        Product product = typedQuery.getResultList().get(0);
        product.changeAmountBy(addProductRequest.getAmount());
        updateDatabaseProductAmount(product);
        return new AddProductResponse(product);
    }

    public void updateDatabaseProductAmount(Product product){
        try {
            em.getTransaction().begin();

            CriteriaUpdate<Product> uq = cb.createCriteriaUpdate(Product.class);
            Root<Product> root = uq.from(Product.class);
            Predicate predicate = cb.equal(root.get("prod_name"), product.getProd_name());
            uq.set(root.get("prod_amount"), product.getProd_amount()).where(predicate);
            em.createQuery(uq).executeUpdate();

            em.getTransaction().commit();
        }
        catch(Exception e) {
            System.out.println("________error while updating amount________");
            em.getTransaction().rollback();
        }

    }

    public void closer(){
        em.close();
        emf.close();
    }
}
