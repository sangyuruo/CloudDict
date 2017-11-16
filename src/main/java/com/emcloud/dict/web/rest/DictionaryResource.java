package com.emcloud.dict.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.dict.domain.Dictionary;
import com.emcloud.dict.service.DictionaryService;
import com.emcloud.dict.web.rest.errors.BadRequestAlertException;
import com.emcloud.dict.web.rest.util.HeaderUtil;
import com.emcloud.dict.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Dictionary.
 */
@RestController
@RequestMapping("/api")
public class DictionaryResource {

    private final Logger log = LoggerFactory.getLogger(DictionaryResource.class);

    private static final String ENTITY_NAME = "dictionary";

    private final DictionaryService dictionaryService;

    public DictionaryResource(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    /**
     * POST  /dictionaries : Create a new dictionary.
     *
     * @param dictionary the dictionary to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dictionary, or with status 400 (Bad Request) if the dictionary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dictionaries")
    @Timed
    public ResponseEntity<Dictionary> createDictionary(@Valid @RequestBody Dictionary dictionary) throws URISyntaxException {
        log.debug("REST request to save Dictionary : {}", dictionary);
        if (dictionary.getId() != null) {
            throw new BadRequestAlertException("A new dictionary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dictionary result = dictionaryService.save(dictionary);
        return ResponseEntity.created(new URI("/api/dictionaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dictionaries : Updates an existing dictionary.
     *
     * @param dictionary the dictionary to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dictionary,
     * or with status 400 (Bad Request) if the dictionary is not valid,
     * or with status 500 (Internal Server Error) if the dictionary couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dictionaries")
    @Timed
    public ResponseEntity<Dictionary> updateDictionary(@Valid @RequestBody Dictionary dictionary) throws URISyntaxException {
        log.debug("REST request to update Dictionary : {}", dictionary);
        if (dictionary.getId() == null) {
            return createDictionary(dictionary);
        }
        Dictionary result = dictionaryService.save(dictionary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dictionary.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dictionaries : get all the dictionaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dictionaries in body
     */
    @GetMapping("/dictionaries")
    @Timed
    public ResponseEntity<List<Dictionary>> getAllDictionaries(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Dictionaries");
        Page<Dictionary> page = dictionaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dictionaries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dictionaries/:id : get the "id" dictionary.
     *
     * @param id the id of the dictionary to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dictionary, or with status 404 (Not Found)
     */
    @GetMapping("/dictionaries/{id}")
    @Timed
    public ResponseEntity<Dictionary> getDictionary(@PathVariable Long id) {
        log.debug("REST request to get Dictionary : {}", id);
        Dictionary dictionary = dictionaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dictionary));
    }

    /**
     * DELETE  /dictionaries/:id : delete the "id" dictionary.
     *
     * @param id the id of the dictionary to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dictionaries/{id}")
    @Timed
    public ResponseEntity<Void> deleteDictionary(@PathVariable Long id) {
        log.debug("REST request to delete Dictionary : {}", id);
        dictionaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dictionaries?query=:query : search for the dictionary corresponding
     * to the query.
     *
     * @param query the query of the dictionary search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/dictionaries")
    @Timed
    public ResponseEntity<List<Dictionary>> searchDictionaries(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Dictionaries for query {}", query);
        Page<Dictionary> page = dictionaryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dictionaries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
