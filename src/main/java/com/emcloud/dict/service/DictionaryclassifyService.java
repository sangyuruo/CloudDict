package com.emcloud.dict.service;

import com.emcloud.dict.domain.Dictionaryclassify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Dictionaryclassify.
 */
public interface DictionaryclassifyService {

    /**
     * Save a dictionaryclassify.
     *
     * @param dictionaryclassify the entity to save
     * @return the persisted entity
     */
    Dictionaryclassify save(Dictionaryclassify dictionaryclassify);

    /**
     *  Get all the dictionaryclassifies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Dictionaryclassify> findAll(Pageable pageable);

    /**
     *  Get the "id" dictionaryclassify.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Dictionaryclassify findOne(Long id);

    /**
     *  Delete the "id" dictionaryclassify.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dictionaryclassify corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Dictionaryclassify> search(String query, Pageable pageable);
}
