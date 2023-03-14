package com.anime.api.admin;

import java.util.List;

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

import com.anime.jpa.entity.Category;
import com.anime.jpa.service.CategoryService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categorys")
public class CategoryApi {

	@Autowired
	private CategoryService categoryService;

	@GetMapping()
	public ResponseEntity<?> doGetAll(Pageable pageable) {
		Page<Category> pageCategory = categoryService.findByIsActive(pageable);
		return ResponseEntity.ok(pageCategory);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doFindById(@PathVariable("id") Long id) {
		Category category = categoryService.findById(id);
		if (ObjectUtils.isEmpty(category)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(category);
	}

	@PostMapping()
	public ResponseEntity<?> doCreate(@RequestBody Category category) {
		return new ResponseEntity<>(categoryService.create(category), HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<?> doUpdate(@RequestBody Category category) {
		return new ResponseEntity<>(categoryService.update(category), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void doDelete(@PathVariable("id") Long id) {
		categoryService.deleteLogical(id);
	}

	@GetMapping("/search")
	public ResponseEntity<?> doSearch(@RequestParam(value = "keyword") String keyword) {
		List<Category> keywords = categoryService.findBykeyword(keyword);
		if (keywords.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(keywords);
	}
}
