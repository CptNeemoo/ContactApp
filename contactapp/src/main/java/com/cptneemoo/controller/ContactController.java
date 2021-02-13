package com.cptneemoo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cptneemoo.entity.Contact;
import com.cptneemoo.service.ContactService;

@Controller
public class ContactController {
	
	@Autowired
	ContactService service;
	
	@GetMapping("/addcontact")
    public String showSignUpForm(Contact contact) {
        return "add-contact";
    }
	
	@PostMapping("/addcontact")
    public String addContact(@Valid Contact contact, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-contact";
        }
        
        service.save(contact);
        return "redirect:/index";
    }
	
	@GetMapping("/index")
	public String showContactList(Model model, @Param("keyword") String keyword) {
	    model.addAttribute("contacts", service.listAll(keyword));
	    model.addAttribute("keyword", keyword);
	    return "index";
	}
	
	@GetMapping("/index/{name}")
	public String showContactList(@PathVariable("name") String name, Model model) {
		model.addAttribute("contacts", service.findByName(name));
	    return "index";
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Contact contact = service.findById(id);
	    
	    model.addAttribute("contact", contact);
	    return "update-contact";
	}
	
	@PostMapping("/update/{id}")
	public String updateContact(@PathVariable("id") long id, @Validated Contact contact, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	    	contact.setId(id);
	        return "update-contact";
	    }
	        
	    service.save(contact);
	    return "redirect:/index";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteContact(@PathVariable("id") long id, Model model) {
		Contact contact = service.findById(id);
		service.delete(contact);
	    return "redirect:/index";
	}
	
		
}
