package com.emcloud.dict.repository;

import com.emcloud.dict.domain.DictionaryClassify;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DictionaryClassify entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DictionaryclassifyRepository extends JpaRepository<DictionaryClassify, Long> {

}
