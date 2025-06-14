package com.abbYs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abbYs.model.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, Integer> {
	// This interface will handle CRUD operations for RoleModel entity
	// You can define custom query methods here if needed
	// For example, you can create methods to find roles by name or other attributes
	// JpaRepository provides built-in methods like save(), findById(), findAll(), deleteById(), etc.

}
