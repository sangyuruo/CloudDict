package com.emcloud.dict.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.dict.domain.DictionaryClassify;
import com.emcloud.dict.service.DictionaryclassifyService;
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

/**
 * REST controller for managing DictionaryClassify.
 */
@RestController
@RequestMapping("/api")
public class DictionaryclassifyResource {

    private final Logger log = LoggerFactory.getLogger(DictionaryclassifyResource.class);

    private static final String ENTITY_NAME = "dictionaryclassify";

    private final DictionaryclassifyService dictionaryclassifyService;

    public DictionaryclassifyResource(DictionaryclassifyService dictionaryclassifyService) {
        this.dictionaryclassifyService = dictionaryclassifyService;
    }

    /**
     * POST  /dictionaryclassifies : Create a new dictionaryclassify.
     *
     * @param dictionaryclassify the dictionaryclassify to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dictionaryclassify, or with status 400 (Bad Request) if the dictionaryclassify has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dictionaryclassifies")
    @Timed
    public ResponseEntity<DictionaryClassify> createDictionaryclassify(@Valid @RequestBody DictionaryClassify dictionaryclassify) throws URISyntaxException {
        log.debug("REST request to save DictionaryClassify : {}", dictionaryclassify);
        if (dictionaryclassify.getId() != null) {
            throw new BadRequestAlertException("A new dictionaryclassify cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DictionaryClassify result = dictionaryclassifyService.save(dictionaryclassify);
        return ResponseEntity.created(new URI("/api/dictionaryclassifies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dictionaryclassifies : Updates an existing dictionaryclassify.
     *
     * @param dictionaryclassify the dictionaryclassify to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dictionaryclassify,
     * or with status 400 (Bad Request) if the dictionaryclassify is not valid,
     * or with status 500 (Internal Server Error) if the dictionaryclassify couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dictionaryclassifies")
    @Timed
    public ResponseEntity<DictionaryClassify> updateDictionaryclassify(@Valid @RequestBody DictionaryClassify dictionaryclassify) throws URISyntaxException {
        log.debug("REST request to update DictionaryClassify : {}", dictionaryclassify);
        if (dictionaryclassify.getId() == null) {
            return createDictionaryclassify(dictionaryclassify);
        }
        DictionaryClassify result = dictionaryclassifyService.save(dictionaryclassify);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dictionaryclassify.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dictionaryclassifies : get all the dictionaryclassifies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dictionaryclassifies in body
     */
    @GetMapping("/dictionaryclassifies")
    @Timed
    public ResponseEntity<List<DictionaryClassify>> getAllDictionaryclassifies(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Dictionaryclassifies");
        Page<DictionaryClassify> page = dictionaryclassifyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dictionaryclassifies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dictionaryclassifies/:id : get the "id" dictionaryclassify.
     *
     * @param id the id of the dictionaryclassify to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dictionaryclassify, or with status 404 (Not Found)
     */
    @GetMapping("/dictionaryclassifies/{id}")
    @Timed
    public ResponseEntity<DictionaryClassify> getDictionaryclassify(@PathVariable Long id) {
        log.debug("REST request to get DictionaryClassify : {}", id);
        DictionaryClassify dictionaryclassify = dictionaryclassifyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dictionaryclassify));
    }

    /**
     * DELETE  /dictionaryclassifies/:id : delete the "id" dictionaryclassify.
     *
     * @param id the id of the dictionaryclassify to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dictionaryclassifies/{id}")
    @Timed
    public ResponseEntity<Void> deleteDictionaryclassify(@PathVariable Long id) {
        log.debug("REST request to delete DictionaryClassify : {}", id);
        dictionaryclassifyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dictionaryclassifies?query=:query : search for the dictionaryclassify corresponding
     * to the query.
     *
     * @param query the query of the dictionaryclassify search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/dictionaryclassifies")
    @Timed
    public ResponseEntity<List<DictionaryClassify>> searchDictionaryclassifies(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Dictionaryclassifies for query {}", query);
        Page<DictionaryClassify> page = dictionaryclassifyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dictionaryclassifies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
