package com.inv.op.backend.service;

import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.repository.ProductRepository;
import com.inv.op.backend.repository.PurchaseOrderRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class InventoryModuleService {

    @Autowired
    ProductRepository productRepository;

    @Autowired 
    PurchaseOrderRepository purchaseOrderRepository;

    public List<Product> getRestockProducts(){

        List<Product> returnProduct= new ArrayList<Product>();

        for (Product product : productRepository.findAll()) {
            if (product.getStock() <= product.calculateOrderLimit() 
                && !product.getIsDeleted() 
                && purchaseOrderRepository.findByPurchaseOrderStatusAndProductProductId(PurchaseOrderStatusEnum.OPEN, product.getProductId()).isEmpty() ) {
                returnProduct.add(product);
            }
        };
        // return productRepository.findRestockProducts();
        return returnProduct;
    }

    public List<Product> getMissingProducts(){

        // return productRepository.findMissingProducts();
        List<Product> returnProduct= new ArrayList<Product>();

        for (Product product : productRepository.findAll()) {
            if (product.getStock() <= product.calculateSafetyStock() && !product.getIsDeleted() ) {
                returnProduct.add(product);
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

    // private void calculateOptimalBatch(Product product) {
    //     double annualDemand = product.getAnnualDemand();
    //     double orderingCost = product.getOrderingCost();
    //     double holdingCost = product.getStorageCost();

    //     double optimalBatch = Math.sqrt((2 * annualDemand * orderingCost) / holdingCost);
    //     product.setOptimalBatch((int) Math.ceil(optimalBatch));
    // }

    // private void calculateOrderPoint(Product product) {
    //     int leadTime = product.getLeadTime();
    //     int d = product.getAnnualDemand()/250;

    //     int orderPoint = leadTime * d;
    //     product.setOrderLimit(orderPoint);
    // }

    // private void calculateSafetyStock(Product product) {

    //     double z = 1.64;
    //     double sigma = 2;
    //     double leadTime = product.getLeadTime();

    //     double safetyStock = z * sigma * Math.sqrt(leadTime);
    //     product.setSafeStock((int) Math.ceil(safetyStock));
    // }

    // private void calculateAndSetCgi(Product product) {

    //     double purchaseCost = product.getUnitCost()*product.getAnnualDemand();
    //     double storageCost = product.getStorageCost() * (product.getOptimalBatch() / 2);
    //     double orderCost =  product.getOrderingCost() * (product.getAnnualDemand() / product.getOptimalBatch());

    //     double cgi = purchaseCost + storageCost + orderCost;
    //     product.setCgi(cgi);
    // }
}
