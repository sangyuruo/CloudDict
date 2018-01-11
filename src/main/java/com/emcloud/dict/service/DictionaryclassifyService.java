package com.emcloud.dict.service;

import com.emcloud.dict.domain.DictionaryClassify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing DictionaryClassify.
 */
public interface DictionaryclassifyService {

    /**
     * Save a dictionaryclassify.
     *
     * @param dictionaryclassify the entity to save
     * @return the persisted entity
     */
    DictionaryClassify save(DictionaryClassify dictionaryclassify);

    /**
     *  Get all the dictionaryclassifies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DictionaryClassify> findAll(Pageable pageable);

    /**
     *  Get the "id" dictionaryclassify.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DictionaryClassify findOne(Long id);

    /**
     *  Get all the DictionaryClassifys.
     *
     *  @param dictCode the pagination information
     *  @return the list of entities
     */
    List<DictionaryClassify> findByDictCode(String dictCode);

    /**
     *  Get all the DictionaryClassifys.
     *
     *  @param dictClassifyValue the pagination information
     *  @return the list of entities
     */
    List<DictionaryClassify> findByDictClassifyValue(String dictClassifyValue);

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
    Page<DictionaryClassify> search(String query, Pageable pageable);
}
