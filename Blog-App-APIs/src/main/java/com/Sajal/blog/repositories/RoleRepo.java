package com.Sajal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sajal.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
