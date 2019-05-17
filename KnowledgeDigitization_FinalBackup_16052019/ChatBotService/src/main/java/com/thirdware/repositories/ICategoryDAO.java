package com.thirdware.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thirdware.entity.T_Category_009;

@Repository
public interface ICategoryDAO extends CrudRepository<T_Category_009, Long>{

}
