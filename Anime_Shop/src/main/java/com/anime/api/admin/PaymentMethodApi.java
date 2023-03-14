package com.anime.api.admin;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

import com.anime.jpa.entity.PaymentMethod;
import com.anime.jpa.service.PaymentMethodService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/paymentmethods")
public class PaymentMethodApi {

	@Autowired
	private PaymentMethodService paymentMethodService;

	@GetMapping()
	public ResponseEntity<?> doGetAll(Pageable pageable) {
		Page<PaymentMethod> pagePaymentmethod = paymentMethodService.findByIsActive(pageable);
		return ResponseEntity.ok(pagePaymentmethod);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doFindById(@PathVariable("id") Long id) {
		PaymentMethod paymentMethod = paymentMethodService.findById(id);
		if (ObjectUtils.isEmpty(paymentMethod)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(paymentMethod);
	}

	@PostMapping()
	public ResponseEntity<?> doCreate(@RequestBody PaymentMethod paymentMethod,@RequestParam("file") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		paymentMethod.setIcon(fileName);
		return new ResponseEntity<>(paymentMethodService.create(paymentMethod), HttpStatus.CREATED);
	}

	@PostMapping("/upload")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {

		String fileName = file.getOriginalFilename();
		System.out.println("hinh: "+ fileName);
		try {
			file.transferTo(new File("D:\\vs\\" + fileName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok("File uploaded successfully.");
	}

	@PutMapping()
	public ResponseEntity<?> doUpdate(@RequestBody PaymentMethod paymentMethod) {
		return new ResponseEntity<>(paymentMethodService.update(paymentMethod), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void doDelete(@PathVariable("id") Long id) {
		paymentMethodService.deleteLogical(id);
	}

	@GetMapping("/search")
	public ResponseEntity<?> doSearch(@RequestParam(value = "keyword") String keyword) {
		List<PaymentMethod> keywords = paymentMethodService.findBykeyword(keyword);
		if (keywords.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(keywords);
	}
}
