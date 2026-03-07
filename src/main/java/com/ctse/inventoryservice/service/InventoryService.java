package com.ctse.inventoryservice.service;

import com.ctse.inventoryservice.model.Product;
import com.ctse.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    
    private final InventoryRepository inventoryRepository;
    
    // Create
    public Product createProduct(Product product) {
        return inventoryRepository.save(product);
    }
    
    // Read - Get all products
    public List<Product> getAllProducts() {
        return inventoryRepository.findAll();
    }
    
    // Read - Get product by ID
    public Optional<Product> getProductById(String id) {
        return inventoryRepository.findById(id);
    }
    
    // Read - Get product by SKU
    public Optional<Product> getProductBySku(String sku) {
        return inventoryRepository.findBySku(sku);
    }
    
    // Read - Get products by category
    public List<Product> getProductsByCategory(String category) {
        return inventoryRepository.findByCategory(category);
    }
    
    // Read - Search products by name
    public List<Product> searchProductsByName(String name) {
        return inventoryRepository.findByNameContainingIgnoreCase(name);
    }
    
    // Update
    public Product updateProduct(String id, Product product) {
        return inventoryRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName() + " Updated");
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setQuantity(product.getQuantity());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setSku(product.getSku());
                    return inventoryRepository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    
    // Delete
    public void deleteProduct(String id) {
        inventoryRepository.deleteById(id);
    }
    
    // Check if product exists
    public boolean productExists(String id) {
        return inventoryRepository.existsById(id);
    }
    
    // Update stock quantity
    public Product updateStock(String id, Integer quantity) {
        return inventoryRepository.findById(id)
                .map(product -> {
                    product.setQuantity(quantity);
                    return inventoryRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
}
