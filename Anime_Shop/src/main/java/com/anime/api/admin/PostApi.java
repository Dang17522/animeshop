package com.anime.api.admin;

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

import com.anime.jpa.entity.Post;
import com.anime.jpa.service.PostService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/posts")
public class PostApi {

	@Autowired
	private PostService postService;

	@GetMapping()
	public ResponseEntity<?> doGetAll(Pageable pageable) {
		Page<Post> pagePost = postService.findByIsActive(pageable);
		return ResponseEntity.ok(pagePost);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> doFindById(@PathVariable("id") Long id) {
		Post post= postService.findById(id);
		if (ObjectUtils.isEmpty(post)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(post);
	}

	@PostMapping()
	public ResponseEntity<?> doCreate(@RequestBody Post post) {
		return new ResponseEntity<>(postService.create(post), HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<?> doUpdate(@RequestBody Post post) {
		return new ResponseEntity<>(postService.update(post), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void doDelete(@PathVariable("id") Long id) {
		postService.deleteLogical(id);
	}

	@GetMapping("/search")
	public ResponseEntity<?> doSearch(Pageable pageable, @RequestParam("keyword") String keyword) {
		Page<Post> pagePost = postService.findByKeyword(keyword, pageable);
		if (pagePost.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(pagePost);
	}
}
