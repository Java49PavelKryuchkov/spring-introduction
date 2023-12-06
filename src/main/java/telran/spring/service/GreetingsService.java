package telran.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import telran.spring.Person;

public interface GreetingsService {
String getGreetings(long id);
Person getPerson(long id);
List<Person> getPersonsByCity(String city);
Person addPerson(Person person);
Person deletePerson(long id);
Person updatePerson(Person person);
void save(String fileName);
void restore(String fileName);
}
