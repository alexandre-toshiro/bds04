package com.devsuperior.bds04.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	@Autowired
	private CityRepository cityRepository;

	@Transactional(readOnly = true)
	public Page<EventDTO> findAll(Pageable pageable) {
		Page<Event> events = repository.findAll(pageable);
		return events.map(event -> new EventDTO(event));
	}

	@Transactional
	public EventDTO insert(EventDTO dto) {
		Event entity = new Event();
		City cityEntity = cityRepository.getOne(dto.getCityId());
		entity.setName(dto.getName());
		entity.setCity(cityEntity);
		entity.setUrl(dto.getUrl());
		entity.setDate(dto.getDate());
		entity = repository.save(entity);
		return new EventDTO(entity);
	}

}
