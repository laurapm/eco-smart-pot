package com.rainforest.eco.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rainforest.eco.models.Product;
import com.rainforest.eco.repositories.ProductRepository;
import com.rainforest.eco.requests.PriceRequest;
import com.rainforest.eco.services.Log;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class ProductController 
{
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@RequestMapping(value="/products", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Product> createProduct(@RequestBody Product product)
	{
		String LogHeader = "[/product: createProduct] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Product> productData = productRepository.findByName(product.getName());
			
			if (productData.isPresent()) {
				Log.logger.info(LogHeader + "This product already exists");
				return new ResponseEntity<>(null, HttpStatus.FOUND);
			} else {
				Product _product = productRepository.save(
					new Product(
						product.getName(),
						product.getPrice(),
						product.getDescription()
					)
				);
				
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(_product, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/products", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Product>> getAllProducts() 
	{
		String LogHeader = "[/products: getAllProducts] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			List<Product> products = new ArrayList<Product>();
			
			productRepository.findAll().forEach(products::add);
			
			if (products.isEmpty()) {
				Log.logger.info(LogHeader + "No products found");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/products/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Product> getProductById(@PathVariable("id") String id)
	{
		String LogHeader = "[/products/id: getProductById] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Product> productData = productRepository.findById(id);
			
			if (productData.isPresent()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(productData.get(), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No product found with id: " + id);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/prices", method=RequestMethod.POST)
	@ResponseBody
	ResponseEntity<List<Product>> getProductBetweenPrices(@RequestBody PriceRequest priceRequest) 
	{
		String LogHeader = "[/products: getProductBetweenPrices] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			
			Query query = new Query();
			query.addCriteria(Criteria.where("price").lte(priceRequest.getMaxPrice()).gte(priceRequest.getMinPrice()));
			List<Product> products = mongoTemplate.find(query, Product.class);
			
			if (!products.isEmpty()) {
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(products, HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "No product found betweem the prices " + priceRequest.getMinPrice() + " and " + priceRequest.getMaxPrice());
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/products", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) 
	{
		String LogHeader = "[/products: updateProduct] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Product> productData = productRepository.findById(product.getId());
			
			if (productData.isPresent()) 
			{
				Product _product = productData.get();
				_product.setId         (product.getId());
				_product.setName       (product.getName());
				_product.setPrice      (product.getPrice());
				_product.setDescription(product.getDescription());
				
				Log.logger.info(LogHeader + "Successful");
				return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
			} else {
				Log.logger.info(LogHeader + "The product to update has not been found");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/products/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") String id)
	{
		String LogHeader = "[/products/id: deleteProduct] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			Optional<Product> product = productRepository.findById(id);
			
			if (product.isPresent()) {
				Log.logger.info(LogHeader + "The product \"" + product.get().getName() + "\" is going to be deleted");
				productRepository.deleteById(id);
			}
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/products", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteAllProducts()
	{
		String LogHeader = "[/products: deleteAllProducts] ";
		
		try {
			Log.logger.info(LogHeader + "Requested");
			productRepository.deleteAll();
			
			Log.logger.info(LogHeader + "Successful");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			Log.logger.error(LogHeader + "some error ocurred: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
