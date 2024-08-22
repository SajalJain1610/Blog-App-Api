package com.Sajal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sajal.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
