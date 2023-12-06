package telran.spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.Person;
import telran.spring.service.GreetingsService;
import telran.spring.service.IdName;

@RestController
@RequestMapping("greetings")
@RequiredArgsConstructor
@Slf4j
public class GreetingsController {
final GreetingsService greetingService;

@PutMapping
Person updatePerson(@RequestBody Person person) {
	log.debug("method: updatePerson, received {}", person);
	return greetingService.updatePerson(person);
}
@PostMapping
Person addPerson(@RequestBody Person person) {
	log.debug("method: addPerson, received {}", person);
	return greetingService.addPerson(person);
}
@DeleteMapping("{id}")
Person deletePerson(@PathVariable long id) {
	log.debug("method: deletePerson, received id {}", id);
	return greetingService.deletePerson(id);
}
@GetMapping("id/{id}")
Person getPerson(@PathVariable long id) {
	log.debug("method: getPerson, received id {}", id);
	return greetingService.getPerson(id);
}
@GetMapping("city/{city}")
List<Person> getPersonsByCity(@PathVariable String city){
	List<Person> result = greetingService.getPersonsByCity(city);
	if(result.isEmpty()) {
		log.warn("received empty list for city: {}", city);
	} else {
		log.trace("result is {}", result);
	}
	return result;
}
}
