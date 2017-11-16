package com.emcloud.dict.service;

import com.emcloud.dict.domain.Dictionary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Dictionary.
 */
public interface DictionaryService {

    /**
     * Save a dictionary.
     *
     * @param dictionary the entity to save
     * @return the persisted entity
     */
    Dictionary save(Dictionary dictionary);

    /**
     *  Get all the dictionaries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Dictionary> findAll(Pageable pageable);

    /**
     *  Get the "id" dictionary.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Dictionary findOne(Long id);

    /**
     *  Delete the "id" dictionary.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dictionary corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Dictionary> search(String query, Pageable pageable);
}
