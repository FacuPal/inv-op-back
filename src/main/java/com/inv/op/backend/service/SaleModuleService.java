package com.inv.op.backend.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.swing.text.html.Option;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inv.op.backend.dto.SaleDto;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.error.product.ProductNotFoundError;
import com.inv.op.backend.error.product.ProductStockNotEnough;
import com.inv.op.backend.error.purchaseOrder.PurchaseOrderSaveError;
import com.inv.op.backend.error.sale.NewSaleSaveError;
import com.inv.op.backend.error.sale.SaleNotFoundError;
import com.inv.op.backend.error.sale.SaleSaveError;
import com.inv.op.backend.model.HistoricDemand;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.model.PurchaseOrder;
import com.inv.op.backend.model.Sale;
import com.inv.op.backend.repository.HistoricDemandRepository;
import com.inv.op.backend.repository.ProductFamilyRepository;
import com.inv.op.backend.repository.ProductRepository;
import com.inv.op.backend.repository.PurchaseOrderRepository;
import com.inv.op.backend.repository.SaleRepository;

@Service
public class SaleModuleService {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductFamilyRepository productFamilyRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    HistoricDemandRepository historicDemandRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Collection<SaleDto> getSaleList() {
        return saleRepository.findAll()
                .stream()
                .map(sale -> modelMapper.map(sale, SaleDto.class))
                .toList();
    }

    public SaleDto getSale(Long id) {

        Optional<Sale> sale = saleRepository.findById(id);

        if (!sale.isPresent()) {
            throw new SaleNotFoundError();
        }

        return modelMapper.map(sale.get(), SaleDto.class);
    }

    public SaleDto saveNewSale(SaleDto requestBody) {
        if (requestBody.getSaleId() != null) {
            throw new NewSaleSaveError();
        }

        Optional<Product> optProduct;

        try {
            optProduct = productRepository.findById(requestBody.getProductId());
        } catch (Exception e) {
            throw new ProductNotFoundError();
        }

        if (!optProduct.isPresent() || optProduct.get().getIsDeleted()) {
            throw new ProductNotFoundError();
        }

        Product product = optProduct.get();

        if (!product.existStock(requestBody.getQuantity())) {
            throw new ProductStockNotEnough();
        }

        updateProductAndDemand(requestBody.getQuantity(), product);


        Sale sale = modelMapper.map(requestBody, Sale.class);
        sale.setProduct(product);
        sale.setSaleDate(Date.from(Instant.now().minus(3, ChronoUnit.HOURS)));

        try {
            saleRepository.save(sale);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //Si el stock bajó del punto de pedido y es lote fijo, creamos el pedido
        if (product.lessThanOrderLimit() && product.getInventoryModel().toLowerCase().trim().equals("lote fijo")) {
            createNewPurchaseOrder(product);
        }

        return modelMapper.map(sale, SaleDto.class);
    }

    public SaleDto updateSale(Long id, SaleDto requestBody) {
        Optional<Sale> sale = saleRepository.findById(id);

        if (!sale.isPresent()) {
            throw new SaleNotFoundError();
        }

        Sale saleToUpdate = sale.get().updateValues(requestBody);

        try {
            saleRepository.save(saleToUpdate);
        } catch (Exception e) {
            throw new SaleSaveError();
        }

        return modelMapper.map(saleToUpdate, SaleDto.class);
    }

    private void createNewPurchaseOrder(Product product) {

        Collection<PurchaseOrder> openPurchaseOrder = purchaseOrderRepository.findByPurchaseOrderStatusAndProductProductId(PurchaseOrderStatusEnum.OPEN, product.getProductId());

        if (!openPurchaseOrder.isEmpty()){
            //Ya hay una orden abierta para el producto.
            return;
        }

        //Se crea una nueva orden para el proveedor de la familia, en estado abierto
        //Con la fecha de hoy y la cantidad fijada por el lote óptimo
        PurchaseOrder newPurchaseOrder = new PurchaseOrder();
        newPurchaseOrder.setProduct(product);
        newPurchaseOrder.setSupplier(product.getProductFamily().getSupplier());
        newPurchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatusEnum.OPEN);
        newPurchaseOrder.setPurchaseOrderDate(Date.from(Instant.now().minus(3, ChronoUnit.HOURS)));
        newPurchaseOrder.setOrderQuantity(product.getOptimalBatch());

        try {
            purchaseOrderRepository.save(newPurchaseOrder);
        } catch (Exception e) {
            throw new PurchaseOrderSaveError();
        }
        

    }

    private void updateProductAndDemand(Integer quantity, Product product){

        // Se reduce stock
        product.reduceStock(quantity);
        productRepository.save(product);
        

        LocalDate currentDate = LocalDate.now();
        Integer year = currentDate.getYear();
        Integer month = currentDate.getMonthValue();

        Optional<HistoricDemand> optHistoricDemand = historicDemandRepository.findByProductAndYearAndMonth(product, year, month);

        HistoricDemand historicDemand;

        if (!optHistoricDemand.isPresent()) {
            historicDemand = new HistoricDemand();
            historicDemand.setYear(year);
            historicDemand.setMonth(month);
            historicDemand.setProduct(product);
            historicDemand.setQuantity(quantity);
        } else {
            historicDemand = optHistoricDemand.get().addDemand(quantity);
        }

        historicDemandRepository.save(historicDemand);

    }

}
