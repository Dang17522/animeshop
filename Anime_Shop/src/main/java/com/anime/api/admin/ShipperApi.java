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

import com.anime.jpa.entity.Shipper;
import com.anime.jpa.service.ShipperService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/shippers")
public class ShipperApi {

	@Autowired
	private ShipperService shipperService;

	@GetMapping()
	public ResponseEntity<?> doGetAll(Pageable pageable) {
		Page<Shipper> pageShipper = shipperService.findAll(pageable);
		return ResponseEntity.ok(pageShipper);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doFindById(@PathVariable("id") Long id) {
		Shipper shipper = shipperService.findById(id);
		if (ObjectUtils.isEmpty(shipper)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(shipper);
	}

	@PostMapping()
	public ResponseEntity<?> doCreate(@RequestBody Shipper shipper) {
		return new ResponseEntity<>(shipperService.create(shipper), HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<?> doUpdate(@RequestBody Shipper shipper) {
		return new ResponseEntity<>(shipperService.update(shipper), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void doDelete(@PathVariable("id") Long id) {
		shipperService.delete(id);
	}

	@GetMapping("/search")
	public ResponseEntity<?> doSearch(@RequestParam(value = "keyword") String keyword) {
		List<Shipper> keywords = shipperService.findBykeyword(keyword);
		if (keywords.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(keywords);
	}
}
