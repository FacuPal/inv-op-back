package com.inv.op.backend.service;

import com.inv.op.backend.dto.DTOMissingProduct;
import com.inv.op.backend.dto.DTOModeloInventario;
import com.inv.op.backend.dto.DTORestockProduct;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.error.product.ProductNotFoundError;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.repository.ProductRepository;
import com.inv.op.backend.repository.PurchaseOrderRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryModuleService {

    @Autowired
    ProductRepository productRepository;

    @Autowired 
    PurchaseOrderRepository purchaseOrderRepository;

    public List<DTORestockProduct> getRestockProducts(){

        List<DTORestockProduct> returnProduct= new ArrayList<DTORestockProduct>();

        for (Product product : productRepository.findAll()) {
            if (product.getStock() <= product.calculateOrderLimit() 
                && !product.getIsDeleted() 
                && purchaseOrderRepository.findByPurchaseOrderStatusAndProductProductId(PurchaseOrderStatusEnum.OPEN, product.getProductId()).isEmpty() ) {
                returnProduct.add(DTORestockProduct.builder()
                        .idRestockProduct(product.getProductId())
                        .nameRestockProduct(product.getProductName())
                        .optimalBatch(product.calculateOptimalBatch()).build());
            }
        };
        // return productRepository.findRestockProducts();
        return returnProduct;
    }

    public List<DTOMissingProduct> getMissingProducts(){

        // return productRepository.findMissingProducts();
        List<DTOMissingProduct> returnProduct= new ArrayList<DTOMissingProduct>();

        for (Product product : productRepository.findAll()) {
            if (product.getStock() <= product.calculateSafetyStock() && !product.getIsDeleted() ) {
                returnProduct.add(DTOMissingProduct.builder()
                        .id(product.getProductId())
                        .name(product.getProductName())
                        .stock(product.getStock())
                        .missingAmount((int)(product.calculateSafetyStock()-product.getStock())).build());
            }
        };

        return returnProduct;

    }

    public Product calculateInventoryForProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        calculateInventory(product);

        return product;
    }

    public void calculateInventory(Product product) {
        switch (product.getInventoryModel()) {
            case "Lote Fijo":
                // calculateOptimalBatch(product);
                product.calculateOptimalBatch();
                // calculateOrderPoint(product);
                product.calculateOrderLimit();
                // calculateSafetyStock(product);
                product.calculateSafetyStock();
                break;
            case "Intervalo Fijo":
                // calculateSafetyStock(product);
                product.calculateSafetyStock();
                break;
            case "CGI":
                // calculateAndSetCgi(product);
                product.calculateCGI();
                break;
            default:
                throw new IllegalArgumentException("Modelo de inventario no reconocido: " + product.getInventoryModel());
        }
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }






    public String getInventoryModelForProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        return product.getInventoryModel();
    }

    public double calculateCGIForProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        return product.calculateCGI();
    }

    public Map<String, Double> calculateFixedLotForProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Map<String, Double> calculations = new HashMap<>();
        calculations.put("optimalBatch", Double.valueOf(product.calculateOptimalBatch()));
        calculations.put("orderPoint", Double.valueOf(product.calculateOrderLimit()));
        calculations.put("safetyStock", Double.valueOf(product.calculateSafetyStock()));

        return calculations;
    }

    public double calculateFixedIntervalForProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        return product.calculateSafetyStock();
    }

    public double calculateCGI(Long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    if(optionalProduct.isEmpty()){
        throw new ProductNotFoundError();
    }
    return optionalProduct.get().calculateCGI();
    }

    public DTOModeloInventario getProductData(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) throw new ProductNotFoundError();
        Product product = optionalProduct.get();
        return DTOModeloInventario.builder()
                .id(product.getProductId())
                .nombre(product.getProductName())
                .familia(product.getProductFamily().getProductFamilyName())
                .optimalBatch(product.calculateOptimalBatch())
                .orderLimit(product.calculateOrderLimit())
                .safetyStock(product.calculateSafetyStock())
                .cgi(calculateNewCGI(product))
                .modeloInventario(product.getProductFamily().getInventoryModel().getInventoryModelName())
                .build();
    }
    private double calculateNewCGI(Product product) {
        double optimalBatch = product.getProductFamily().getInventoryModel().getInventoryModelName().equals("Intervalo Fijo") ? product.calculateOptimalBatch() + product.getProductDemand() : product.calculateOptimalBatch();
        double purchaseCost = product.getUnitCost() * optimalBatch;// .getUnitCost()*product.getAnnualDemand();
        double storageCost = product.getStorageCost() * (optimalBatch / 2) ;//product.getStorageCost() * (product.getOptimalBatch() / 2);
        double orderCost =  product.getOrderCost() * ( (double) product.getProductDemand() / optimalBatch);//product.getOrderingCost() * (product.getAnnualDemand() / product.getOptimalBatch());

        return purchaseCost + storageCost + orderCost;
    }
}
