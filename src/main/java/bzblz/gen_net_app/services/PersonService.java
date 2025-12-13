package bzblz.gen_net_app.services;

import bzblz.gen_net_app.model.Person;
import bzblz.gen_net_app.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public List<Person> findByProjectId(UUID id) {
        return personRepository.findByProjectId(id);
    }
    public List<Person> findAll() {
        return personRepository.findAll();
    }
    public Person findOne(UUID id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElse(null);
    }
    @Transactional
    public Person add(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public void update(UUID id, Person person) {
        person.setId(id);
        personRepository.save(person);
    }
    @Transactional
    public void delete(UUID id) {
        personRepository.deleteById(id);
    }

    public List<Person> findAll(Integer page, Integer items_per_page) {
        return personRepository.findAll(PageRequest.of(page, items_per_page)).getContent();
    }

    public List<Person> findAll(Integer page, Integer items_per_page, String sortByField) {
        return personRepository.findAll(PageRequest.of(page, items_per_page, Sort.by(sortByField))).getContent();
    }

    public List<Person> findAll(String sortByField) {
        return personRepository.findAll(Sort.by(sortByField));
    }
}
