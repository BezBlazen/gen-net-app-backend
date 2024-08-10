package bzblz.gen_net_app.services;

import bzblz.gen_net_app.model.Person;
import bzblz.gen_net_app.repositories.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonsService {
    private final PersonsRepository personsRepository;

    @Autowired
    public PersonsService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }
    public List<Person> findByProjectId(Integer id) {
        return personsRepository.findByProjectId(id);
    }
    public List<Person> findAll() {
        return personsRepository.findAll();
    }
    public Person findOne(int id) {
        Optional<Person> person = personsRepository.findById(id);
        return person.orElse(null);
    }
    @Transactional
    public void add(Person person) {
        personsRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        personsRepository.save(person);
    }
    @Transactional
    public void delete(int id) {
        personsRepository.deleteById(id);
    }

    public List<Person> findAll(Integer page, Integer items_per_page) {
        return personsRepository.findAll(PageRequest.of(page, items_per_page)).getContent();
    }

    public List<Person> findAll(Integer page, Integer items_per_page, String sortByField) {
        return personsRepository.findAll(PageRequest.of(page, items_per_page, Sort.by(sortByField))).getContent();
    }

    public List<Person> findAll(String sortByField) {
        return personsRepository.findAll(Sort.by(sortByField));
    }
}
