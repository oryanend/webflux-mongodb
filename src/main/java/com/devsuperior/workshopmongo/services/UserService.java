package com.devsuperior.workshopmongo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.entities.User;
import com.devsuperior.workshopmongo.repositories.UserRepository;
import com.devsuperior.workshopmongo.services.exceptions.ResourceNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public Flux<UserDTO> findAll() {
        return repository.findAll().map(UserDTO::new);
	}

    public Mono<UserDTO> findById(String id) {
        return repository.findById(id).map(UserDTO::new)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")));
    }

    public Mono<UserDTO> insert(UserDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        return repository.save(entity).map(UserDTO::new);
    }

    @Transactional
    public Mono<UserDTO> update(String id, UserDTO dto) {
        return repository.findById(id).flatMap(existingUser -> {
            copyDtoToEntity(dto, existingUser);
            return repository.save(existingUser);
        }).map(UserDTO::new).switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")));
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado")))
				.flatMap(existingUser -> repository.delete(existingUser));
    }

	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
    }
/*


	@Transactional(readOnly = true)
	public List<PostDTO> findPosts(String id) {
		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		List<PostDTO> result = user.getPosts().stream().map(x -> new PostDTO(x)).toList();
		return result;
	}



 */
}
