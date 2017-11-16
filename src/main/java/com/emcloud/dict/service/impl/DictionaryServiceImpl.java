package com.emcloud.dict.service.impl;

import com.emcloud.dict.service.DictionaryService;
import com.emcloud.dict.domain.Dictionary;
import com.emcloud.dict.repository.DictionaryRepository;
import com.emcloud.dict.repository.search.DictionarySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Dictionary.
 */
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService{

    private final Logger log = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    private final DictionaryRepository dictionaryRepository;

    private final DictionarySearchRepository dictionarySearchRepository;

    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository, DictionarySearchRepository dictionarySearchRepository) {
        this.dictionaryRepository = dictionaryRepository;
        this.dictionarySearchRepository = dictionarySearchRepository;
    }

    /**
     * Save a dictionary.
     *
     * @param dictionary the entity to save
     * @return the persisted entity
     */
    @Override
    public Dictionary save(Dictionary dictionary) {
        log.debug("Request to save Dictionary : {}", dictionary);
        Dictionary result = dictionaryRepository.save(dictionary);
        dictionarySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the dictionaries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Dictionary> findAll(Pageable pageable) {
        log.debug("Request to get all Dictionaries");
        return dictionaryRepository.findAll(pageable);
    }

    /**
     *  Get one dictionary by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Dictionary findOne(Long id) {
        log.debug("Request to get Dictionary : {}", id);
        return dictionaryRepository.findOne(id);
    }

    /**
     *  Delete the  dictionary by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dictionary : {}", id);
        dictionaryRepository.delete(id);
        dictionarySearchRepository.delete(id);
    }

    /**
     * Search for the dictionary corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Dictionary> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dictionaries for query {}", query);
        Page<Dictionary> result = dictionarySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
