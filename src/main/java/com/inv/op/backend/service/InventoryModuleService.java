package com.inv.op.backend.service;

import com.inv.op.backend.model.Product;
import com.inv.op.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class InventoryModuleService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getRestockProducts(){

        return productRepository.findRestockProducts();
    }

    public List<Product> getMissingProducts(){

        return productRepository.findMissingProducts();
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
                calculateOptimalBatch(product);
                calculateOrderPoint(product);
                calculateSafetyStock(product);
                break;
            case "Intervalo Fijo":
                calculateSafetyStock(product);
                break;
            case "CGI":
                calculateAndSetCgi(product);
                break;
            default:
                throw new IllegalArgumentException("Modelo de inventario no reconocido: " + product.getInventoryModel());
        }
    }

    private void calculateOptimalBatch(Product product) {
        double annualDemand = product.getAnnualDemand();
        double orderingCost = product.getOrderingCost();
        double holdingCost = product.getStorageCost();

        double optimalBatch = Math.sqrt((2 * annualDemand * orderingCost) / holdingCost);
        product.setOptimalBatch((int) Math.ceil(optimalBatch));
    }

    private void calculateOrderPoint(Product product) {
        int leadTime = product.getLeadTime();
        int d = product.getAnnualDemand()/250;

        int orderPoint = leadTime * d;
        product.setOrderLimit(orderPoint);
    }

    private void calculateSafetyStock(Product product) {

        double z = 1.64;
        double sigma = 2;
        double leadTime = product.getLeadTime();

        double safetyStock = z * sigma * Math.sqrt(leadTime);
        product.setSafeStock((int) Math.ceil(safetyStock));
    }

        private void calculateAndSetCgi(Product product) {

            double purchaseCost = product.getUnitCost()*product.getAnnualDemand();
            double storageCost = product.getStorageCost() * (product.getOptimalBatch() / 2);
            double orderCost =  product.getOrderingCost() * (product.getAnnualDemand() / product.getOptimalBatch());

            double cgi = purchaseCost + storageCost + orderCost;
            product.setCgi(cgi);
        }
}
