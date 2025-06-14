package com.abbYs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblUser")
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String email;
	private String password;
	private String name;
	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "roleId") // FK in tblUser
	private RoleModel role;
	@Column(name = "bank_name")
	private String bankName;
	@Column(name = "bank_number")
	private String bankNumber;
	
	public UserModel() {
	}
	
	public UserModel(int userId, String email, String password, String name, RoleModel role, String bankName,
			String bankNumber) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
		this.bankName = bankName;
		this.bankNumber = bankNumber;
	}
	
	public UserModel(String email, String password, String name, RoleModel role, String bankName, String bankNumber) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
		this.bankName = bankName;
		this.bankNumber = bankNumber;
	}
	
	public UserModel(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoleModel getRoleId() {
		return role;
	}

	public void setRoleId(RoleModel role) {
		this.role = role;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", role=" + role + ", bankName=" + bankName + ", bankNumber=" + bankNumber + "]";
	}
}
