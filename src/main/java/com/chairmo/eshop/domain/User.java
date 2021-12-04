package com.chairmo.eshop.domain;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chairmo.eshop.annotations.EmailValidator;

import lombok.Data;

@Entity
@Data
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class User {

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 40)
	private String firstName;

	@Size(max = 40)
	private String middleName;

	@Size(max = 40)
	private String lastName;

	@Size(min = 10, max = 13)
	private String phone;

	@OneToOne
	private Address address;

	@Column(nullable = false, unique = true)
	@EmailValidator
	private String email;

	@Column(nullable = false)
	@NotBlank
	private String password;

	@Column
	private Boolean emailVerified = false;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private Cart cart;

	@OneToMany(mappedBy = "user")
	private Set<Order> orders;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@Column(nullable = false, updatable = false)
	private OffsetDateTime dateCreated;

	@Column(nullable = false)
	private OffsetDateTime lastUpdated;

	@PrePersist
	public void prePersist() {
		dateCreated = OffsetDateTime.now();
		lastUpdated = dateCreated;
	}

	@PreUpdate
	public void preUpdate() {
		lastUpdated = OffsetDateTime.now();
	}

	public User() {
	}

}
