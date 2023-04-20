package com.minset.apptest.dao;

import com.minset.apptest.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonaDAO extends JpaRepository<Persona, Long> {
}
