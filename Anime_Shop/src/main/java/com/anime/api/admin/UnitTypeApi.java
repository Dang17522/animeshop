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

import com.anime.jpa.entity.UnitType;
import com.anime.jpa.service.UnitTypeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/unittypes")
public class UnitTypeApi {

	@Autowired
	private UnitTypeService unitTypeService;

	@GetMapping()
	public ResponseEntity<?> doGetAll(Pageable pageable) {
		Page<UnitType> pageUnit = unitTypeService.findByIsActive(pageable);
		return ResponseEntity.ok(pageUnit);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doFindById(@PathVariable("id") Long id) {
		UnitType unitType = unitTypeService.findById(id);
		if (ObjectUtils.isEmpty(unitType)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(unitType);
	}

	@PostMapping()
	public ResponseEntity<?> doCreate(@RequestBody UnitType unitType) {
		return new ResponseEntity<>(unitTypeService.create(unitType), HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<?> doUpdate(@RequestBody UnitType unitType) {
		return new ResponseEntity<>(unitTypeService.update(unitType), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void doDelete(@PathVariable("id") Long id) {
		unitTypeService.deleteLogical(id);
	}

	@GetMapping("/search")
	public ResponseEntity<?> doSearch(@RequestParam(value = "keyword") String keyword) {
		List<UnitType> keywords = unitTypeService.findBykeyword(keyword);
		if (keywords.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(keywords);
	}
}
