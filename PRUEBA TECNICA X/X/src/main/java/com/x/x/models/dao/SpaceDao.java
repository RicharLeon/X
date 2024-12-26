package com.x.x.models.dao;

import com.x.x.models.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceDao extends JpaRepository<Space, Long> {
}
