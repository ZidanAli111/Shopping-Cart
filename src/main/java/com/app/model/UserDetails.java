package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
public class UserDetails {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Email(regexp = "^(?!.*?[\\W_]{2})(?=^[a-zA-Z0-9])(?=.*[a-zA-Z0-9]$)[\\w!#$%&'*+\\-/=?^`\\{|]{2,64}+@([a-zA-Z0-9]{1,253}+\\.)+(com|org|net)$", message = "Invalid Email ID. Please adhere to the required constraints.")
	@Column(name = "username", unique = true)
	@NotNull
	private String username;

	@Size(min = 8, max = 20, message = "Password must have minimum of  8 characters")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*(\\s)).*$", message = "Password must have at least one uppercase and one lowercase characters, one digit and must not contain any spaces ")
	@NotNull
	@Column(name = "password")
	private String password;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
