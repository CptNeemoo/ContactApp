package com.cptneemoo.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cptneemoo.entity.Contact;
import com.cptneemoo.repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	private ContactRepository repo;

	public List<Contact> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }
	
	public List<Contact> findByName(String name)
	{
		return repo.findByName(name);
	}

	public void save(@Valid Contact contact) {
		repo.save(contact);
	}

	public Contact findById(long id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + id));
	}

	public void delete(Contact contact) {
		repo.delete(contact);
		
	}
	
	
}
