package com.inv.op.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inv.op.backend.dto.SaleDto;
import com.inv.op.backend.error.product.ProductNotFoundError;
import com.inv.op.backend.error.sale.NewSaleSaveError;
import com.inv.op.backend.error.sale.SaleNotFoundError;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.model.Sale;
import com.inv.op.backend.repository.ProductRepository;
import com.inv.op.backend.repository.SaleRepository;

@Service
public class SaleModuleService {

    @Autowired  
    SaleRepository saleRepository;

    @Autowired 
    ProductRepository productRepository;

    @Autowired
	private ModelMapper modelMapper;


    public List<SaleDto> getSaleList() {
        return saleRepository.findAll()
            .stream()
            .map( sale -> modelMapper.map(sale, SaleDto.class))
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

        Optional<Product> product = productRepository.findById(requestBody.getProductId());

        if (!product.isPresent()){
            throw new ProductNotFoundError();
        }
        
        //TODO: Chequear Stock 

        Sale sale = modelMapper.map(requestBody, Sale.class);
        sale.setProduct(product.get());
        sale.setSaleDate(Date.from(Instant.now().minus(3, ChronoUnit.HOURS)));

        //TODO: Reducir stock del producto 

        try {
            saleRepository.save(sale);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }

        return modelMapper.map(saleToUpdate, SaleDto.class);
    }
}

