package com.devsuperior.bds04.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;

	@Transactional(readOnly = true)
	public Page<EventDTO> findAll(Pageable pageable) {
		Page<Event> events = repository.findAll(pageable);
		return events.map(event -> new EventDTO(event));
	}

	@Transactional
	public EventDTO insert(EventDTO dto) {
		Event entity = new Event();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new EventDTO(entity);
	}

}
