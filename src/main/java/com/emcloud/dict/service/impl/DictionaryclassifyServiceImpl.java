package com.emcloud.dict.service.impl;

import com.emcloud.dict.domain.DictionaryClassify;
import com.emcloud.dict.service.DictionaryclassifyService;
import com.emcloud.dict.repository.DictionaryclassifyRepository;
import com.emcloud.dict.repository.search.DictionaryclassifySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DictionaryClassify.
 */
@Service
@Transactional
public class DictionaryclassifyServiceImpl implements DictionaryclassifyService{

    private final Logger log = LoggerFactory.getLogger(DictionaryclassifyServiceImpl.class);

    private final DictionaryclassifyRepository dictionaryclassifyRepository;

    private final DictionaryclassifySearchRepository dictionaryclassifySearchRepository;

    public DictionaryclassifyServiceImpl(DictionaryclassifyRepository dictionaryclassifyRepository, DictionaryclassifySearchRepository dictionaryclassifySearchRepository) {
        this.dictionaryclassifyRepository = dictionaryclassifyRepository;
        this.dictionaryclassifySearchRepository = dictionaryclassifySearchRepository;
    }

    /**
     * Save a dictionaryclassify.
     *
     * @param dictionaryclassify the entity to save
     * @return the persisted entity
     */
    @Override
    public DictionaryClassify save(DictionaryClassify dictionaryclassify) {
        log.debug("Request to save DictionaryClassify : {}", dictionaryclassify);
        DictionaryClassify result = dictionaryclassifyRepository.save(dictionaryclassify);
        dictionaryclassifySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the dictionaryclassifies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DictionaryClassify> findAll(Pageable pageable) {
        log.debug("Request to get all Dictionaryclassifies");
        return dictionaryclassifyRepository.findAll(pageable);
    }

    /**
     *  Get one dictionaryclassify by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DictionaryClassify findOne(Long id) {
        log.debug("Request to get DictionaryClassify : {}", id);
        return dictionaryclassifyRepository.findOne(id);
    }

    /**
     *  Delete the  dictionaryclassify by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DictionaryClassify : {}", id);
        dictionaryclassifyRepository.delete(id);
        dictionaryclassifySearchRepository.delete(id);
    }

    /**
     * Search for the dictionaryclassify corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DictionaryClassify> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dictionaryclassifies for query {}", query);
        Page<DictionaryClassify> result = dictionaryclassifySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
