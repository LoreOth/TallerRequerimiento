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

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    private String province;

    private String address;
    
    private String rol;
 
    public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}



	private String phone;
    
    public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    
    
    
	public String getFirstName() {
		return this.firstName;
	}
	public String getEmail() {
		return this.email;
	}

	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setEmail(String email) {
	    this.email = email;
	}

	public String getPassword() {
	    return this.password;
	}

}