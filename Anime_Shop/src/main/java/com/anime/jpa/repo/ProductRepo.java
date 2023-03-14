package com.anime.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

	List<Product> findByIsActive(Boolean isActive);

	@Query("SELECT p FROM Product p WHERE p.isActive = ?1 ORDER BY p.view DESC ")
	Page<Product> findByIsActive(Boolean isActive, Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Product p SET p.isActive = ?1 WHERE p.id = ?2")
	void deleteLogical(Boolean isActive, Long id);

	@Query(value = "SELECT  TOP 10 * FROM products WHERE isActive = ?1 ORDER BY createDate DESC", nativeQuery = true)
	List<Product> findTop10NewProd(Boolean isActive);

	Product findBySlug(String slug);

	@Query("SELECT p FROM Product p WHERE p.category.name LIKE %?1%")
	Page<Product> findByCategoryName(String name, Pageable pageable);

	@Query(value = "SELECT  TOP 12 * FROM products WHERE isActive = ?1 ORDER BY [view] DESC", nativeQuery = true)
	List<Product> findTop10ViewsProd(Boolean isActive);

	@Query(value = "SELECT DISTINCT size FROM products WHERE size IS NOT NULL AND isActive = ?1", nativeQuery = true)
	List<String> findAllSize(Boolean isActive);

	@Query("SELECT p FROM Product p WHERE p.isActive = ?1 AND p.size = ?2")
	Page<Product> findBySize(Boolean isActive, String size, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.isActive = ?1 AND CONCAT(p.name, p.slug, p.nameMain, p.material, p.series, p.status, p.size, p.description, p.category.name, p.unitType.name) LIKE %?2%")
	Page<Product> findByKeyword(Boolean isActive, String keyword, Pageable pageable);

	@Query("SELECT p FROM Product p where p.price BETWEEN ?1 AND ?2 AND isActive =?3")
	Page<Product> findByPrice(Double min, Double max, Boolean isAcitve, Pageable pageable) ;
	
	@Query("SELECT p FROM Product p WHERE p.isActive = ?1 order by p.price desc")
	Page<Product> sortCaotoThap(Boolean isAcitve, Pageable pageable) ;
	
	@Query("SELECT p FROM Product p WHERE p.isActive = ?1 order by p.price asc")
	Page<Product> sortThaptoCao(Boolean isAcitve, Pageable pageable) ;
	
	@Query("SELECT p FROM Product p WHERE p.isActive = ?1 order by p.discount desc")
	Page<Product> sortDiscount(Boolean isAcitve, Pageable pageable) ;
	
	@Query("SELECT p FROM Product p WHERE p.isActive = ?1 order by p.view desc")
	Page<Product> sortView(Boolean isAcitve, Pageable pageable) ;

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Product p SET p.quantity = ?1, p.selled = ?2 WHERE p.id = ?3")
	void updateQuantityAndSelled(Integer quantity, Integer selled, Long id);
}
