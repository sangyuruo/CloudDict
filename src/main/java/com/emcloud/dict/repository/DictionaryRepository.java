package com.emcloud.dict.repository;

import com.emcloud.dict.domain.Dictionary;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dictionary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

}
