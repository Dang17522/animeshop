package com.anime.api.admin;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anime.jpa.entity.MultipleImage;
import com.anime.jpa.entity.Product;
import com.anime.jpa.service.MultipleImageService;
import com.anime.jpa.service.ProductService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/products")
public class ProductApi {

	@Autowired
	private ProductService productService;

	@Autowired
	private MultipleImageService multipleImageService;

	@Autowired
	private ServletContext application;

	@GetMapping()
	public ResponseEntity<?> doGetAll(Pageable pageable) {
		Page<Product> pageProducts = productService.findByIsActive(pageable);
		return ResponseEntity.ok(pageProducts);
	}

	@GetMapping("/search")
	public ResponseEntity<?> doSearch(Pageable pageable, @RequestParam("keyword") String keyword) {
		Page<Product> pageProducts = productService.findByKeyword(keyword, pageable);
		if (pageProducts.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(pageProducts);
	}

	@PostMapping()
	public ResponseEntity<?> doCreate(@RequestBody Product product,
			@RequestParam("files") MultipartFile[] files) {
		try {
			productService.create(product);
			List<MultipleImage> images = new ArrayList<>();
			for (MultipartFile file : files) {
				String fileName = file.getOriginalFilename();

				MultipleImage image = null;
				if (product.getId() != null) {
					image = new MultipleImage(fileName, product);
				}
				images.add(image);
			}
			multipleImageService.saveImages(images);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<?> doUpdate(@RequestBody Product product) {
		return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doFindId(@PathVariable("id") Long id) {
		Product product = productService.findById(id);
		if (ObjectUtils.isEmpty(product)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void doDelete(@PathVariable("id") Long id) {
		productService.deleteLogical(id);
	}

	public String saveImage(MultipartFile file) {
		try {
			Path path = Paths.get(application.getRealPath("/assets/images" + file.getOriginalFilename()));
			file.transferTo(path);
			return file.getOriginalFilename();
		} catch (IOException ex) {
			return null;
		}
	}
}
