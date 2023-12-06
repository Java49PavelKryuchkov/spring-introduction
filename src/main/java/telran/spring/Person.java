package telran.spring;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record Person(@NotNull long id, @Pattern(regexp = "[A-Z][a-z]{2,}",
message="Wrong name structure") String name,
		@NotEmpty String city, @Email @NotEmpty String email,
		@Pattern(regexp = "(\\+972-?|0)5\\d-?\\d{7}",
		message="not Israel mobile phone") @NotEmpty String phone) implements Serializable{
   
}
