package com.devsuperior.workshopmongo.services;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;

	@Transactional(readOnly = true)
	public Mono<PostDTO> findById(String id) {
		return repository.findById(id).map(PostDTO::new)
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")));
	}
/*

	
	public List<PostDTO> findByTitle(String text) {
		List<PostDTO> result = repository.searchTitle(text).stream().map(x -> new PostDTO(x)).toList();
		return result;
	}
	
	public List<PostDTO> fullSearch(String text, Instant minDate, Instant maxDate) {
		maxDate = maxDate.plusSeconds(86400); // 24 * 60 * 60
		List<PostDTO> result = repository.fullSearch(text, minDate, maxDate).stream().map(x -> new PostDTO(x)).toList();
		return result;
	}

 */
}
