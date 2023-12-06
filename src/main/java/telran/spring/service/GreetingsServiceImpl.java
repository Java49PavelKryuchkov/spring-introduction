package telran.spring.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.IDN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.*;
import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;
import telran.spring.Person;

@Service
@Slf4j
public class GreetingsServiceImpl implements GreetingsService {
	Map<Long, String> greetingsMap = new HashMap<>(Map.of(123l, "David", 124l, "Sara",125l, "Rivka"));
	Map<Long, Person> personMap = new HashMap<>();
	 @Value("${app.greeting.message:Hello}")
	    String greetingMessage;
	    @Value("${app.unknown.name:unknown guest}")
	    String unknownName;
	    @Value("${app.file.name:persons.data}")
	    String fileName;
	@Override
	public String getGreetings(long id) {
			
			Person person =  personMap.get(id);
			String name = "";
			if (person == null) {
				name = "Unknown guest";
				log.warn("person with id {} not found", id);
			} else {
				name = person.name();
				log.debug("person name is {}", name);
			}
			return "Hello, " + name;
	}
	
	@Override
	public Person getPerson(long id) {
		Person person = personMap.get(id);
		if(person == null) {
			log.warn("person with id {} not found", id);
		} else {
			log.debug("persons with id {} exists", id);
		}
		return person;
	}
	@Override
	public List<Person> getPersonsByCity(String city) {
		List<Person> persons = personMap.values().stream()
				.filter(p -> p.city().equals(city))
				.toList();
		
		return persons;
	}
	@Override
	public Person addPerson(Person person) {
		long id = person.id();
		if(personMap.containsKey(id)) {
			throw new IllegalStateException(String.format("person with id %d already exists", id));
		}
		personMap.put(id, person);
		log.debug("person with id {} has been saved", id);
		return person;
	}
	@Override
	public Person deletePerson(long id) {
		if(!personMap.containsKey(id)) {
			throw new IllegalStateException(String.format("person with id %d doesn't exist", id));
		}
		Person person = personMap.remove(id);
		log.debug("person with id {} has been removed", person.id());
		return person;
	}
	@Override
	public Person updatePerson(Person person) {
		long id = person.id();
		if (!personMap.containsKey(id) ){
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		personMap.put(id, person);
		log.debug("person with id {} has been update", person.id());
		return person;
	}

	@Override
	public void save(String fileName) {
		try(ObjectOutputStream output =
				new ObjectOutputStream(new FileOutputStream(fileName))) {
			output.writeObject(new ArrayList<Person>(personMap.values()));
			log.info("persons data have been saved");
		} catch (Exception e) {
			log.error("{}", e);
		}
	}

	@Override
	public void restore(String fileName) {
		Person person = null;
		try(ObjectInputStream input =
				new ObjectInputStream(new FileInputStream(fileName))){
			List<Person> personsList = (List<Person>) input.readObject();
			personsList.forEach(this::addPerson);log.info("restored from file");
		} catch (FileNotFoundException e) {
			log.warn("No file with data found");
		} catch (Exception e) {
			log.error("{}", e);
		}

	}
	
	@PostConstruct
	void restoreFromFile() throws Exception {
		restore(fileName);
		
	}
	@PreDestroy
	void saveToFile() {
		save(fileName);
	}

}
