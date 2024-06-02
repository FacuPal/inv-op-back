package com.inv.op.backend;

import com.inv.op.backend.model.Product;
import com.inv.op.backend.model.ProductFamily;
import com.inv.op.backend.repository.HistoricDemandRepository;
import com.inv.op.backend.repository.ProductFamilyRepository;
import com.inv.op.backend.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	@Autowired
	ProductFamilyRepository productFamilyRepository;

	@Autowired
	ProductRepository productRepository;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner init() {
		return args -> {
			ProductFamily productFamily = new ProductFamily();
			productFamily.setProductFamilyName("Familia 1");
			productFamily.setIsDeleted(false);
			productFamily = productFamilyRepository.save(productFamily);

			Product product1 = new Product();
			product1.setProductName("Producto AB");
			product1.setIsDeleted(false);
			product1.setOptimalBatch(10);
			product1.setOrderLimit(10);
			product1.setProductFamily(productFamily);
			product1.setSafeStock(3);
			product1.setStock(5);
			Product product2 = new Product();
			product2.setProductName("Producto BC");
			product2.setIsDeleted(false);
			product2.setOptimalBatch(10);
			product2.setOrderLimit(10);
			product2.setProductFamily(productFamily);
			product2.setSafeStock(3);
			product2.setStock(5);
			Product product3 = new Product();
			product3.setProductName("Producto CD");
			product3.setIsDeleted(false);
			product3.setOptimalBatch(10);
			product3.setOrderLimit(10);
			product3.setProductFamily(productFamily);
			product3.setSafeStock(3);
			product3.setStock(5);
			Product product4 = new Product();
			product4.setProductName("Producto DE");
			product4.setIsDeleted(false);
			product4.setOptimalBatch(10);
			product4.setOrderLimit(10);
			product4.setProductFamily(productFamily);
			product4.setSafeStock(3);
			product4.setStock(5);

			product1 = productRepository.save(product1);
			product2 = productRepository.save(product2);
			product3 = productRepository.save(product3);
			product4 = productRepository.save(product4);





		};
	}

}

