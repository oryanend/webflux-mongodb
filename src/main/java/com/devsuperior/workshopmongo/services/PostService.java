package com.devsuperior.workshopmongo.services;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;

	@Transactional(readOnly = true)
	public Mono<PostDTO> findById(String id) {
		return repository.findById(id).map(PostDTO::new)
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")));
	}

	public Mono<List<PostDTO>> findByTitle(String text) {
		return repository.searchTitle(text)
				.map(PostDTO::new)  // converte Post → PostDTO
				.collectList();     // junta todos os DTOs em uma List
	}

/*

	

	
	public List<PostDTO> fullSearch(String text, Instant minDate, Instant maxDate) {
		maxDate = maxDate.plusSeconds(86400); // 24 * 60 * 60
		List<PostDTO> result = repository.fullSearch(text, minDate, maxDate).stream().map(x -> new PostDTO(x)).toList();
		return result;
	}

 */
}
