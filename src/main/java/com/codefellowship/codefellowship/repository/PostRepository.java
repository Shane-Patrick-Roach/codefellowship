package com.codefellowship.codefellowship.repository;

import com.codefellowship.codefellowship.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
