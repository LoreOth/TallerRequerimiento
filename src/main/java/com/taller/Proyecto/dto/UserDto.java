package  com.taller.Proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
	public String getFirstName() {
		return this.firstName;
	}
	public Object getEmail() {
		return this.email;
	}
	public CharSequence getPassword() {
		return this.password;
	}
	public String getLastName() {
		return this.lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName=firstName;
		
	}
	public void setLastName(String lastName) {
		this.lastName=lastName;
		
	}
	public void setEmail(Object email) {
	    this.email = (String) email;
	}
}