package com.emcloud.dict.web.rest;

import com.emcloud.dict.EmCloudDictApp;

import com.emcloud.dict.config.SecurityBeanOverrideConfiguration;

import com.emcloud.dict.domain.DictionaryClassify;
import com.emcloud.dict.repository.DictionaryclassifyRepository;
import com.emcloud.dict.service.DictionaryclassifyService;
import com.emcloud.dict.repository.search.DictionaryclassifySearchRepository;
import com.emcloud.dict.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.emcloud.dict.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DictionaryclassifyResource REST controller.
 *
 * @see DictionaryclassifyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudDictApp.class, SecurityBeanOverrideConfiguration.class})
public class DictionaryclassifyResourceIntTest {

    private static final String DEFAULT_DICT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DICT_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DICT_CLASSIFY_CODE = 1;
    private static final Integer UPDATED_DICT_CLASSIFY_CODE = 2;

    private static final String DEFAULT_DICT_CLASSIFY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DICT_CLASSIFY_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARENT_CLASSIFY_CODE = 1;
    private static final Integer UPDATED_PARENT_CLASSIFY_CODE = 2;

    private static final Integer DEFAULT_SEQ_NO = 1;
    private static final Integer UPDATED_SEQ_NO = 2;

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private DictionaryclassifyRepository dictionaryclassifyRepository;

    @Autowired
    private DictionaryclassifyService dictionaryclassifyService;

    @Autowired
    private DictionaryclassifySearchRepository dictionaryclassifySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDictionaryclassifyMockMvc;

    private DictionaryClassify dictionaryclassify;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DictionaryclassifyResource dictionaryclassifyResource = new DictionaryclassifyResource(dictionaryclassifyService);
        this.restDictionaryclassifyMockMvc = MockMvcBuilders.standaloneSetup(dictionaryclassifyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DictionaryClassify createEntity(EntityManager em) {
        DictionaryClassify dictionaryclassify = new DictionaryClassify()
            .dictCode(DEFAULT_DICT_CODE)
            .dictClassifyCode(DEFAULT_DICT_CLASSIFY_CODE)
            .dictClassifyValue(DEFAULT_DICT_CLASSIFY_VALUE)
            .parentId(DEFAULT_PARENT_CLASSIFY_CODE)
            .seqNo(DEFAULT_SEQ_NO)
            .enable(DEFAULT_ENABLE)
            .remark(DEFAULT_REMARK);
        return dictionaryclassify;
    }

    @Before
    public void initTest() {
        dictionaryclassifySearchRepository.deleteAll();
        dictionaryclassify = createEntity(em);
    }

    @Test
    @Transactional
    public void createDictionaryclassify() throws Exception {
        int databaseSizeBeforeCreate = dictionaryclassifyRepository.findAll().size();

        // Create the DictionaryClassify
        restDictionaryclassifyMockMvc.perform(post("/api/dictionaryclassifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryclassify)))
            .andExpect(status().isCreated());

        // Validate the DictionaryClassify in the database
        List<DictionaryClassify> dictionaryclassifyList = dictionaryclassifyRepository.findAll();
        assertThat(dictionaryclassifyList).hasSize(databaseSizeBeforeCreate + 1);
        DictionaryClassify testDictionaryclassify = dictionaryclassifyList.get(dictionaryclassifyList.size() - 1);
        assertThat(testDictionaryclassify.getDictCode()).isEqualTo(DEFAULT_DICT_CODE);
        assertThat(testDictionaryclassify.getDictClassifyCode()).isEqualTo(DEFAULT_DICT_CLASSIFY_CODE);
        assertThat(testDictionaryclassify.getDictClassifyValue()).isEqualTo(DEFAULT_DICT_CLASSIFY_VALUE);
        assertThat(testDictionaryclassify.getParentId()).isEqualTo(DEFAULT_PARENT_CLASSIFY_CODE);
        assertThat(testDictionaryclassify.getSeqNo()).isEqualTo(DEFAULT_SEQ_NO);
        assertThat(testDictionaryclassify.isEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testDictionaryclassify.getRemark()).isEqualTo(DEFAULT_REMARK);

        // Validate the DictionaryClassify in Elasticsearch
        DictionaryClassify dictionaryclassifyEs = dictionaryclassifySearchRepository.findOne(testDictionaryclassify.getId());
        assertThat(dictionaryclassifyEs).isEqualToComparingFieldByField(testDictionaryclassify);
    }

    @Test
    @Transactional
    public void createDictionaryclassifyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dictionaryclassifyRepository.findAll().size();

        // Create the DictionaryClassify with an existing ID
        dictionaryclassify.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDictionaryclassifyMockMvc.perform(post("/api/dictionaryclassifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryclassify)))
            .andExpect(status().isBadRequest());

        // Validate the DictionaryClassify in the database
        List<DictionaryClassify> dictionaryclassifyList = dictionaryclassifyRepository.findAll();
        assertThat(dictionaryclassifyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDictClassifyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryclassifyRepository.findAll().size();
        // set the field null
        dictionaryclassify.setDictClassifyCode(null);

        // Create the DictionaryClassify, which fails.

        restDictionaryclassifyMockMvc.perform(post("/api/dictionaryclassifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryclassify)))
            .andExpect(status().isBadRequest());

        List<DictionaryClassify> dictionaryclassifyList = dictionaryclassifyRepository.findAll();
        assertThat(dictionaryclassifyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDictClassifyValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryclassifyRepository.findAll().size();
        // set the field null
        dictionaryclassify.setDictClassifyValue(null);

        // Create the DictionaryClassify, which fails.

        restDictionaryclassifyMockMvc.perform(post("/api/dictionaryclassifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryclassify)))
            .andExpect(status().isBadRequest());

        List<DictionaryClassify> dictionaryclassifyList = dictionaryclassifyRepository.findAll();
        assertThat(dictionaryclassifyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSeqNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryclassifyRepository.findAll().size();
        // set the field null
        dictionaryclassify.setSeqNo(null);

        // Create the DictionaryClassify, which fails.

        restDictionaryclassifyMockMvc.perform(post("/api/dictionaryclassifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryclassify)))
            .andExpect(status().isBadRequest());

        List<DictionaryClassify> dictionaryclassifyList = dictionaryclassifyRepository.findAll();
        assertThat(dictionaryclassifyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryclassifyRepository.findAll().size();
        // set the field null
        dictionaryclassify.setEnable(null);

        // Create the DictionaryClassify, which fails.

        restDictionaryclassifyMockMvc.perform(post("/api/dictionaryclassifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryclassify)))
            .andExpect(status().isBadRequest());

        List<DictionaryClassify> dictionaryclassifyList = dictionaryclassifyRepository.findAll();
        assertThat(dictionaryclassifyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDictionaryclassifies() throws Exception {
        // Initialize the database
        dictionaryclassifyRepository.saveAndFlush(dictionaryclassify);

        // Get all the dictionaryclassifyList
        restDictionaryclassifyMockMvc.perform(get("/api/dictionaryclassifies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictionaryclassify.getId().intValue())))
            .andExpect(jsonPath("$.[*].dictCode").value(hasItem(DEFAULT_DICT_CODE.toString())))
            .andExpect(jsonPath("$.[*].dictClassifyCode").value(hasItem(DEFAULT_DICT_CLASSIFY_CODE)))
            .andExpect(jsonPath("$.[*].dictClassifyValue").value(hasItem(DEFAULT_DICT_CLASSIFY_VALUE.toString())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_CLASSIFY_CODE)))
            .andExpect(jsonPath("$.[*].seqNo").value(hasItem(DEFAULT_SEQ_NO)))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getDictionaryclassify() throws Exception {
        // Initialize the database
        dictionaryclassifyRepository.saveAndFlush(dictionaryclassify);

        // Get the dictionaryclassify
        restDictionaryclassifyMockMvc.perform(get("/api/dictionaryclassifies/{id}", dictionaryclassify.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dictionaryclassify.getId().intValue()))
            .andExpect(jsonPath("$.dictCode").value(DEFAULT_DICT_CODE.toString()))
            .andExpect(jsonPath("$.dictClassifyCode").value(DEFAULT_DICT_CLASSIFY_CODE))
            .andExpect(jsonPath("$.dictClassifyValue").value(DEFAULT_DICT_CLASSIFY_VALUE.toString()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_CLASSIFY_CODE))
            .andExpect(jsonPath("$.seqNo").value(DEFAULT_SEQ_NO))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDictionaryclassify() throws Exception {
        // Get the dictionaryclassify
        restDictionaryclassifyMockMvc.perform(get("/api/dictionaryclassifies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDictionaryclassify() throws Exception {
        // Initialize the database
        dictionaryclassifyService.save(dictionaryclassify);

        int databaseSizeBeforeUpdate = dictionaryclassifyRepository.findAll().size();

        // Update the dictionaryclassify
        DictionaryClassify updatedDictionaryclassify = dictionaryclassifyRepository.findOne(dictionaryclassify.getId());
        updatedDictionaryclassify
            .dictCode(UPDATED_DICT_CODE)
            .dictClassifyCode(UPDATED_DICT_CLASSIFY_CODE)
            .dictClassifyValue(UPDATED_DICT_CLASSIFY_VALUE)
            .parentId(UPDATED_PARENT_CLASSIFY_CODE)
            .seqNo(UPDATED_SEQ_NO)
            .enable(UPDATED_ENABLE)
            .remark(UPDATED_REMARK);

        restDictionaryclassifyMockMvc.perform(put("/api/dictionaryclassifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDictionaryclassify)))
            .andExpect(status().isOk());

        // Validate the DictionaryClassify in the database
        List<DictionaryClassify> dictionaryclassifyList = dictionaryclassifyRepository.findAll();
        assertThat(dictionaryclassifyList).hasSize(databaseSizeBeforeUpdate);
        DictionaryClassify testDictionaryclassify = dictionaryclassifyList.get(dictionaryclassifyList.size() - 1);
        assertThat(testDictionaryclassify.getDictCode()).isEqualTo(UPDATED_DICT_CODE);
        assertThat(testDictionaryclassify.getDictClassifyCode()).isEqualTo(UPDATED_DICT_CLASSIFY_CODE);
        assertThat(testDictionaryclassify.getDictClassifyValue()).isEqualTo(UPDATED_DICT_CLASSIFY_VALUE);
        assertThat(testDictionaryclassify.getParentId()).isEqualTo(UPDATED_PARENT_CLASSIFY_CODE);
        assertThat(testDictionaryclassify.getSeqNo()).isEqualTo(UPDATED_SEQ_NO);
        assertThat(testDictionaryclassify.isEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testDictionaryclassify.getRemark()).isEqualTo(UPDATED_REMARK);

        // Validate the DictionaryClassify in Elasticsearch
        DictionaryClassify dictionaryclassifyEs = dictionaryclassifySearchRepository.findOne(testDictionaryclassify.getId());
        assertThat(dictionaryclassifyEs).isEqualToComparingFieldByField(testDictionaryclassify);
    }

    @Test
    @Transactional
    public void updateNonExistingDictionaryclassify() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryclassifyRepository.findAll().size();

        // Create the DictionaryClassify

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDictionaryclassifyMockMvc.perform(put("/api/dictionaryclassifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryclassify)))
            .andExpect(status().isCreated());

        // Validate the DictionaryClassify in the database
        List<DictionaryClassify> dictionaryclassifyList = dictionaryclassifyRepository.findAll();
        assertThat(dictionaryclassifyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDictionaryclassify() throws Exception {
        // Initialize the database
        dictionaryclassifyService.save(dictionaryclassify);

        int databaseSizeBeforeDelete = dictionaryclassifyRepository.findAll().size();

        // Get the dictionaryclassify
        restDictionaryclassifyMockMvc.perform(delete("/api/dictionaryclassifies/{id}", dictionaryclassify.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dictionaryclassifyExistsInEs = dictionaryclassifySearchRepository.exists(dictionaryclassify.getId());
        assertThat(dictionaryclassifyExistsInEs).isFalse();

        // Validate the database is empty
        List<DictionaryClassify> dictionaryclassifyList = dictionaryclassifyRepository.findAll();
        assertThat(dictionaryclassifyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDictionaryclassify() throws Exception {
        // Initialize the database
        dictionaryclassifyService.save(dictionaryclassify);

        // Search the dictionaryclassify
        restDictionaryclassifyMockMvc.perform(get("/api/_search/dictionaryclassifies?query=id:" + dictionaryclassify.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictionaryclassify.getId().intValue())))
            .andExpect(jsonPath("$.[*].dictCode").value(hasItem(DEFAULT_DICT_CODE.toString())))
            .andExpect(jsonPath("$.[*].dictClassifyCode").value(hasItem(DEFAULT_DICT_CLASSIFY_CODE)))
            .andExpect(jsonPath("$.[*].dictClassifyValue").value(hasItem(DEFAULT_DICT_CLASSIFY_VALUE.toString())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_CLASSIFY_CODE)))
            .andExpect(jsonPath("$.[*].seqNo").value(hasItem(DEFAULT_SEQ_NO)))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DictionaryClassify.class);
        DictionaryClassify dictionaryclassify1 = new DictionaryClassify();
        dictionaryclassify1.setId(1L);
        DictionaryClassify dictionaryclassify2 = new DictionaryClassify();
        dictionaryclassify2.setId(dictionaryclassify1.getId());
        assertThat(dictionaryclassify1).isEqualTo(dictionaryclassify2);
        dictionaryclassify2.setId(2L);
        assertThat(dictionaryclassify1).isNotEqualTo(dictionaryclassify2);
        dictionaryclassify1.setId(null);
        assertThat(dictionaryclassify1).isNotEqualTo(dictionaryclassify2);
    }
}
