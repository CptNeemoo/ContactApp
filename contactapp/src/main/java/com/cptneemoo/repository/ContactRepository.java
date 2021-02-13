package com.cptneemoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cptneemoo.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{
	
	@Query("SELECT c FROM Contact c WHERE c.name LIKE %?1%")
	public List<Contact> search(String keyword);
	
	List<Contact> findByName(String name);

}
