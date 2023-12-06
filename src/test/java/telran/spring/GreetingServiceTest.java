package telran.spring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

import telran.exceptions.NotFoundException;
import telran.spring.service.GreetingsService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GreetingServiceTest {
@Autowired
GreetingsService greetingService;
Person personNormal = new Person(123, "Vasya", "Rehovot", "vasya@gmail.com",
		"054-1234567");
Person personNormalUpdated = new Person(123, "Vasya", "Lod", "vasya@gmail.com",
		"054-1234567");
Person personNotFound = new Person(500, "Vasya", "Rehovot", "vasya@gmail.com",
		"054-1234567");
@BeforeAll
static void deleteFile() throws IOException {
	Files.deleteIfExists(Path.of("test.data"));
}
@Test
@Order(1)
void loadApplicationContextTest() {
	assertNotNull(greetingService);
}
@Test
@Order(2)
void addPersonNormalTest() {
	assertEquals(personNormal, greetingService.addPerson(personNormal));
}
@Test
@Order(3)
void addPersonAlreadyExist() {
	assertThrowsExactly(IllegalStateException.class, () -> greetingService.addPerson(personNormal));
}
@Test
@Order(4)
void updatePersonNormalTest() {
	assertEquals(personNormalUpdated, greetingService.updatePerson(personNormalUpdated));
}
@Test
@Order(5)
void getPersonTest() {
	assertEquals(personNormalUpdated, greetingService.getPerson(123));
}
@Test
@Order(7)
void personNotFoundTest() {
	assertNull(greetingService.getPerson(124));
}
@Test
@Order(7)
void updateNotExistsTest() {
	assertThrowsExactly(NotFoundException.class,
			() -> greetingService.updatePerson(personNotFound));
}
@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
@Test
@Order(8)
void persistenceTest() {
	assertEquals(personNormalUpdated, greetingService.getPerson(123));
}
}
