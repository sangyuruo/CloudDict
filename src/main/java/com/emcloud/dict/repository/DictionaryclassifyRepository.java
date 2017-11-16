package com.emcloud.dict.repository;

import com.emcloud.dict.domain.Dictionaryclassify;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Dictionaryclassify entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictionaryclassifyRepository extends JpaRepository<Dictionaryclassify, Long> {

}
