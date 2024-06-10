package com.inv.op.backend.service;

import com.inv.op.backend.demandPrediction.DPSFactory;
import com.inv.op.backend.demandPrediction.DemandPredictionStrategy;
import com.inv.op.backend.dto.*;
import com.inv.op.backend.model.*;
import com.inv.op.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

@Service
public class DemandModuleService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HistoricDemandRepository historicDemandRepository;

    @Autowired
    private ProductFamilyRepository productFamilyRepository;

    @Autowired
    private DemandPredictionModelRepository demandPredictionModelRepository;

    @Autowired
    private DemandPredictionModelTypeRepository demandPredictionModelTypeRepository;


    @Autowired
    private DPSFactory dpsFactory;

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






    public Collection<DTOProductOrFamily> getProductsAndFamilies(String search) {
        Collection<DTOProductOrFamily> ret = new ArrayList<>();
        for (ProductFamily family : productFamilyRepository.findByProductFamilyNameContainingIgnoreCase(search)) {
            ret.add(DTOProductOrFamily.builder()
                            .id(family.getProductFamilyId())
                            .family(true)
                            .name(family.getProductFamilyName())
                    .build());
        }

        for (Product product : productRepository.findByProductNameContainingIgnoreCase(search)) {
            ret.add(DTOProductOrFamily.builder()
                            .id(product.getProductId())
                            .family(false)
                            .name(product.getProductName())
                    .build());
        }

        return ret;
    }



    public Collection<DTODemandPredictionModel> getModels(Long id, Boolean family) throws Exception {
        Collection<DTODemandPredictionModel> ret = new ArrayList<>();
        HashMap<String, Integer> nums = new HashMap<>();
        for (DemandPredictionModel demandPredictionModel : demandPredictionModelRepository.buscarPorProductoOFamilia(id, family)) {
            String type = demandPredictionModel.getDemandPredictionModelType().getDemandPredictionModelTypeName();
            if(!nums.containsKey(type)) {
                nums.put(type, 1);
            }
            Integer currentNum = nums.get(type);
            DTODemandPredictionModel dto = DTODemandPredictionModel.builder()
                    .id(demandPredictionModel.getDemandPredictionModelId())
                    .type(demandPredictionModel.getDemandPredictionModelType().getDemandPredictionModelTypeName())
                    .color(demandPredictionModel.getDemandPredictionModelColor())
                    .num(currentNum)
                    .build();

            nums.replace(type, currentNum + 1);

            switch (type) {
                case "PMP":
                    dto.setPonderations(((PMPDemandPredictionModel)demandPredictionModel).getRawPonderations());
                    break;
                case "PMSE":
                    dto.setAlpha(((PMSEDemandPredictionModel)demandPredictionModel).getAlpha());
                    dto.setRoot(((PMSEDemandPredictionModel)demandPredictionModel).getRoot());
                    break;
                case "RL":
                    dto.setIgnorePeriods(((RLDemandPredicitionModel)demandPredictionModel).getIgnorePeriods());
                    break;
                case "Ix":
                    dto.setLength(((IxDemandPredictionModel)demandPredictionModel).getLength());
                    dto.setExpectedDemand(((IxDemandPredictionModel)demandPredictionModel).getExpectedDemand());
                    break;
                default:
                    throw new Exception("Tipo de modelo no soportado");
            }
            ret.add(dto);
        }
        return ret;
    }

    public Long putModel(DTODemandPredictionModel dto, Long id, Boolean family) throws Exception {
        Long ret = null;
        Optional<DemandPredictionModelType> optDPMT = demandPredictionModelTypeRepository.findByDemandPredictionModelTypeName(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }
        ProductFamily productFamily = null;
        Product product = null;
        if(family) {
            Optional<ProductFamily> opt = productFamilyRepository.findById(id);
            if (opt.isEmpty()) throw new Exception("No se encontró la familia");
            productFamily = opt.get();
        } else {
            Optional<Product> opt = productRepository.findById(id);
            if (opt.isEmpty()) throw new Exception("No se encontró el producto");
            product = opt.get();
        }
        DemandPredictionStrategy dps = dpsFactory.getStrategy(dto.getType());
        if(dto.getId() == null)
            return dps.create(dto, productFamily, product);
        dps.update(dto);
        return dto.getId();
    }

    public void deleteModel(Long id) throws Exception {
        Optional<DemandPredictionModel> optDPM = demandPredictionModelRepository.findById(id);
        if(optDPM.isEmpty()) {
            throw new Exception("No se encontró el modelo");
        }
        DemandPredictionModel demandPredictionModel = optDPM.get();
        demandPredictionModel.setIsDeleted(true);
        demandPredictionModelRepository.save(demandPredictionModel);
    }


    public DTODemandResults predict(Long id, Boolean family, Date desde, Boolean predecirMesActual) throws Exception {
        Integer cantPeriodosAPredecir = 3;
        String metodoError = "MAD";
        DTODemandResults ret = DTODemandResults.builder().build();

        Collection<DTODemandPredictionModel> models = getModels(id, family);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        Integer currMonth = calendar.get(Calendar.MONTH);
        Integer currYear = calendar.get(Calendar.YEAR);

        calendar.add(Calendar.MONTH, cantPeriodosAPredecir);
        if(predecirMesActual) calendar.add(Calendar.MONTH, -1);
        Date limite = calendar.getTime();
        calendar.setTime(desde);

        while (calendar.getTime().before(limite)) {
            Integer month = calendar.get(Calendar.MONTH);
            Integer year = calendar.get(Calendar.YEAR);
            Integer demand;
            if(predecirMesActual && currYear.equals(year) && currMonth.equals(month)) {
                demand = null;
            } else {
                demand = historicDemandRepository.getPeriod(id, family, month + 1, year);
            }

            ret.getPeriods().add(DTODemandRealPeriod.builder()
                            .month(month + 1)
                            .year(year)
                            .value(demand)
                    .build());

            calendar.add(Calendar.MONTH, 1);
        }

        for (DTODemandPredictionModel model : models) {
            DemandPredictionStrategy dps = dpsFactory.getStrategy(model.getType());
            DTODemandPrediction prediction = DTODemandPrediction.builder()
                    .id(model.getId())
                    .type(model.getType())
                    .num(model.getNum())
                    .color(model.getColor())
                    .periods(dps.predict(ret, model))
                    .build();

            ret.getPredictions().add(prediction);
        }
        return ret;
    }
}
