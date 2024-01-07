package com.nmdsoluecoes.twtodos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nmdsoluecoes.twtodos.models.Todo;

public interface TodoRepository  extends JpaRepository <Todo,Long>{
    
}
