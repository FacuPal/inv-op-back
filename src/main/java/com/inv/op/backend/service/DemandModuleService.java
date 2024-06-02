package com.inv.op.backend.service;

import com.inv.op.backend.dto.DTODemandaHistoricaAnual;
import com.inv.op.backend.dto.DTODemandaHistoricaMensual;
import com.inv.op.backend.dto.DTODemandaHistoricaProducto;
import com.inv.op.backend.model.HistoricDemand;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.repository.HistoricDemandRepository;
import com.inv.op.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;

@Service
public class DemandModuleService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HistoricDemandRepository historicDemandRepository;

    public Collection<DTODemandaHistoricaProducto> getProducts(String search) {
        Collection<DTODemandaHistoricaProducto> ret = new ArrayList<>();
        for (Product product : productRepository.findByProductNameContainingIgnoreCase(search)) {
             ret.add(DTODemandaHistoricaProducto.builder()
                    .id(product.getProductId())
                    .nombre(product.getProductName())
                    .build());
        }
        return ret;
    }

    public Collection<DTODemandaHistoricaAnual> getHistoricDemand(Long articulo, Integer desde, Integer hasta) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(articulo);
        if(optionalProduct.isEmpty()) throw new Exception("No se encontró el artículo");
        Product product = optionalProduct.get();

        Collection<DTODemandaHistoricaAnual> ret = new ArrayList<>();

        for (HistoricDemand historicDemand : historicDemandRepository.findByProductAndYearGreaterThanEqualAndYearLessThanEqual(product, desde, hasta, Sort.by("year", "month"))) {
            DTODemandaHistoricaMensual dtoMes = DTODemandaHistoricaMensual.builder()
                    .mes(historicDemand.getMonth())
                    .cantidad(historicDemand.getQuantity())
                    .build();

            boolean found = false;
            for (DTODemandaHistoricaAnual dtoDemandaHistoricaAnual : ret) {
                if(Objects.equals(dtoDemandaHistoricaAnual.getAno(), historicDemand.getYear())) {
                    found = true;
                    for (DTODemandaHistoricaMensual dtoMesTmp : dtoDemandaHistoricaAnual.getMeses()) {
                        if(Objects.equals(dtoMesTmp.getMes(), historicDemand.getMonth())) {
                            throw new Exception("Se encontraron dos entradas para el histórico de ventas para el mes " + historicDemand.getMonth() + " del año " + historicDemand.getYear());
                        }
                    }
                    dtoDemandaHistoricaAnual.getMeses().add(dtoMes);
                    break;
                }
            }
            if (!found) {
                DTODemandaHistoricaAnual dtoAno = DTODemandaHistoricaAnual.builder()
                        .ano(historicDemand.getYear())
                        .build();

                dtoAno.getMeses().add(dtoMes);

                ret.add(dtoAno);
            }
        }
        for (Integer i = desde; i <= hasta; i++) {
            boolean found = false;
            for (DTODemandaHistoricaAnual dtoDemandaHistoricaAnual : ret)
                if (Objects.equals(dtoDemandaHistoricaAnual.getAno(), i)) {
                    found = true;
                    break;
                }
            if (!found)
                ret.add(DTODemandaHistoricaAnual.builder()
                    .ano(i)
                    .build());
        }
        for (DTODemandaHistoricaAnual dtoDemandaHistoricaAnual : ret) {
            for (Integer i = 1; i <= 12; i++) {
                boolean found = false;
                for (DTODemandaHistoricaMensual dtoMes : dtoDemandaHistoricaAnual.getMeses())
                    if(Objects.equals(dtoMes.getMes(), i)) {
                        found = true;
                        break;
                    }
                if(!found)
                    dtoDemandaHistoricaAnual.getMeses().add(DTODemandaHistoricaMensual.builder()
                            .mes(i)
                            .cantidad(0)
                            .build());
            }
        }
        return ret;
    }

    public Collection<DTODemandaHistoricaAnual> postHistoricDemand(Long articulo, Collection<DTODemandaHistoricaAnual> dtos) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(articulo);
        if(optionalProduct.isEmpty()) throw new Exception("No se encontró el artículo");
        Product product = optionalProduct.get();

        Integer minYear = Year.now().getValue();
        Integer maxYear = Year.now().getValue();
        for (DTODemandaHistoricaAnual ano : dtos) {
            for (DTODemandaHistoricaMensual mes : ano.getMeses()) {
                Optional<HistoricDemand> optionalHistoricDemand = historicDemandRepository.findByProductAndYearAndMonth(product, ano.getAno(), mes.getMes());
                if (optionalHistoricDemand.isPresent()) {
                    HistoricDemand hd = optionalHistoricDemand.get();
                    hd.setQuantity(mes.getCantidad());
                    historicDemandRepository.save(hd);
                } else {
                    if(mes.getCantidad() != 0) {
                        HistoricDemand hd = new HistoricDemand();
                        hd.setYear(ano.getAno());
                        hd.setMonth(mes.getMes());
                        hd.setProduct(product);
                        hd.setQuantity(mes.getCantidad());
                        historicDemandRepository.save(hd);
                    }
                }
            }
            if (ano.getAno() < minYear) minYear = ano.getAno();
            if (ano.getAno() > maxYear) maxYear = ano.getAno();
        }

        return getHistoricDemand(articulo, minYear, maxYear);

    }
}
