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

import com.anime.jpa.entity.CategoryParent;
import com.anime.jpa.service.CategoryParentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categoryParents")
public class CategoryParentApi {

	@Autowired
	private CategoryParentService categoryParentService;

	@GetMapping()
	public ResponseEntity<?> doGetAll(Pageable pageable) {
		Page<CategoryParent> pageUnit = categoryParentService.findByIsActive(pageable);
		return ResponseEntity.ok(pageUnit);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doFindById(@PathVariable("id") Long id) {
		CategoryParent categoryParent = categoryParentService.findById(id);
		if (ObjectUtils.isEmpty(categoryParent)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(categoryParent);
	}

	@PostMapping()
	public ResponseEntity<?> doCreate(@RequestBody CategoryParent categoryParent) {
		return new ResponseEntity<>(categoryParentService.create(categoryParent), HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<?> doUpdate(@RequestBody CategoryParent categoryParent) {
		return new ResponseEntity<>(categoryParentService.update(categoryParent), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void doDelete(@PathVariable("id") Long id) {
		categoryParentService.deleteLogical(id);
	}

	@GetMapping("/search")
	public ResponseEntity<?> doSearch(@RequestParam(value = "keyword") String keyword) {
		List<CategoryParent> keywords = categoryParentService.findBykeyword(keyword);
		if (keywords.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(keywords);
	}
}
