package com.benchring.contacts;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contact createContact(@RequestBody Contact contact) {
        OffsetDateTime now = OffsetDateTime.now();
        contact.setDateCreated(now);
        contact.setDateUpdated(now);
        return contactRepository.save(contact);
    }

    @GetMapping
    public List<Contact> getContacts(
            @RequestParam(name = "external_id", required = false) Integer externalId,
            @RequestParam(name = "phone_number", required = false) String phoneNumber,
            @RequestParam(name = "limit", defaultValue = "10000") int limit,
            @RequestParam(name = "offset", defaultValue = "0") int offset) {

        return contactRepository.findByFilters(externalId, phoneNumber, limit, offset);
    }
}
