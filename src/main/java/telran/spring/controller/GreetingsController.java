package telran.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import telran.spring.service.GreetingsService;

@RestController
@RequestMapping("greetings")
@RequiredArgsConstructor
public class GreetingsController {
final GreetingsService greetingService;
@GetMapping("{id}")
String getGreetings(@PathVariable long id) {
	return greetingService.getGreetings(id);
}
}
