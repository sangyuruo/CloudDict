package com.emcloud.dict.web.rest;

import com.emcloud.dict.EmCloudDictApp;

import com.emcloud.dict.config.SecurityBeanOverrideConfiguration;

import com.emcloud.dict.domain.Dictionary;
import com.emcloud.dict.repository.DictionaryRepository;
import com.emcloud.dict.service.DictionaryService;
import com.emcloud.dict.repository.search.DictionarySearchRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.emcloud.dict.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DictionaryResource REST controller.
 *
 * @see DictionaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudDictApp.class, SecurityBeanOverrideConfiguration.class})
public class DictionaryResourceIntTest {

    private static final String DEFAULT_DICT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DICT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DICT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DICT_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SEQ_NO = 1;
    private static final Integer UPDATED_SEQ_NO = 2;

    private static final String DEFAULT_ATTR_1 = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_2 = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_3 = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_4 = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_4 = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_5 = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_5 = "BBBBBBBBBB";

    private static final Integer DEFAULT_ATTR_6 = 1;
    private static final Integer UPDATED_ATTR_6 = 2;

    private static final Integer DEFAULT_ATTR_7 = 1;
    private static final Integer UPDATED_ATTR_7 = 2;

    private static final Integer DEFAULT_ATTR_8 = 1;
    private static final Integer UPDATED_ATTR_8 = 2;

    private static final Integer DEFAULT_ATTR_9 = 1;
    private static final Integer UPDATED_ATTR_9 = 2;

    private static final Integer DEFAULT_ATTR_10 = 1;
    private static final Integer UPDATED_ATTR_10 = 2;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private DictionarySearchRepository dictionarySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDictionaryMockMvc;

    private Dictionary dictionary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DictionaryResource dictionaryResource = new DictionaryResource(dictionaryService);
        this.restDictionaryMockMvc = MockMvcBuilders.standaloneSetup(dictionaryResource)
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
    public static Dictionary createEntity(EntityManager em) {
        Dictionary dictionary = new Dictionary()
            .dictName(DEFAULT_DICT_NAME)
            .dictCode(DEFAULT_DICT_CODE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .seqNo(DEFAULT_SEQ_NO)
            .attr1(DEFAULT_ATTR_1)
            .attr2(DEFAULT_ATTR_2)
            .attr3(DEFAULT_ATTR_3)
            .attr4(DEFAULT_ATTR_4)
            .attr5(DEFAULT_ATTR_5)
            .attr6(DEFAULT_ATTR_6)
            .attr7(DEFAULT_ATTR_7)
            .attr8(DEFAULT_ATTR_8)
            .attr9(DEFAULT_ATTR_9)
            .attr10(DEFAULT_ATTR_10);
        return dictionary;
    }

    @Before
    public void initTest() {
        dictionarySearchRepository.deleteAll();
        dictionary = createEntity(em);
    }

    @Test
    @Transactional
    public void createDictionary() throws Exception {
        int databaseSizeBeforeCreate = dictionaryRepository.findAll().size();

        // Create the Dictionary
        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isCreated());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeCreate + 1);
        Dictionary testDictionary = dictionaryList.get(dictionaryList.size() - 1);
        assertThat(testDictionary.getDictName()).isEqualTo(DEFAULT_DICT_NAME);
        assertThat(testDictionary.getDictCode()).isEqualTo(DEFAULT_DICT_CODE);
        assertThat(testDictionary.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testDictionary.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testDictionary.getSeqNo()).isEqualTo(DEFAULT_SEQ_NO);
        assertThat(testDictionary.getAttr1()).isEqualTo(DEFAULT_ATTR_1);
        assertThat(testDictionary.getAttr2()).isEqualTo(DEFAULT_ATTR_2);
        assertThat(testDictionary.getAttr3()).isEqualTo(DEFAULT_ATTR_3);
        assertThat(testDictionary.getAttr4()).isEqualTo(DEFAULT_ATTR_4);
        assertThat(testDictionary.getAttr5()).isEqualTo(DEFAULT_ATTR_5);
        assertThat(testDictionary.getAttr6()).isEqualTo(DEFAULT_ATTR_6);
        assertThat(testDictionary.getAttr7()).isEqualTo(DEFAULT_ATTR_7);
        assertThat(testDictionary.getAttr8()).isEqualTo(DEFAULT_ATTR_8);
        assertThat(testDictionary.getAttr9()).isEqualTo(DEFAULT_ATTR_9);
        assertThat(testDictionary.getAttr10()).isEqualTo(DEFAULT_ATTR_10);

        // Validate the Dictionary in Elasticsearch
        Dictionary dictionaryEs = dictionarySearchRepository.findOne(testDictionary.getId());
        assertThat(dictionaryEs).isEqualToComparingFieldByField(testDictionary);
    }

    @Test
    @Transactional
    public void createDictionaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dictionaryRepository.findAll().size();

        // Create the Dictionary with an existing ID
        dictionary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isBadRequest());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDictNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryRepository.findAll().size();
        // set the field null
        dictionary.setDictName(null);

        // Create the Dictionary, which fails.

        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isBadRequest());

        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDictCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryRepository.findAll().size();
        // set the field null
        dictionary.setDictCode(null);

        // Create the Dictionary, which fails.

        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isBadRequest());

        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSeqNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dictionaryRepository.findAll().size();
        // set the field null
        dictionary.setSeqNo(null);

        // Create the Dictionary, which fails.

        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isBadRequest());

        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDictionaries() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        // Get all the dictionaryList
        restDictionaryMockMvc.perform(get("/api/dictionaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].dictName").value(hasItem(DEFAULT_DICT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dictCode").value(hasItem(DEFAULT_DICT_CODE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].seqNo").value(hasItem(DEFAULT_SEQ_NO)))
            .andExpect(jsonPath("$.[*].attr1").value(hasItem(DEFAULT_ATTR_1.toString())))
            .andExpect(jsonPath("$.[*].attr2").value(hasItem(DEFAULT_ATTR_2.toString())))
            .andExpect(jsonPath("$.[*].attr3").value(hasItem(DEFAULT_ATTR_3.toString())))
            .andExpect(jsonPath("$.[*].attr4").value(hasItem(DEFAULT_ATTR_4.toString())))
            .andExpect(jsonPath("$.[*].attr5").value(hasItem(DEFAULT_ATTR_5.toString())))
            .andExpect(jsonPath("$.[*].attr6").value(hasItem(DEFAULT_ATTR_6)))
            .andExpect(jsonPath("$.[*].attr7").value(hasItem(DEFAULT_ATTR_7)))
            .andExpect(jsonPath("$.[*].attr8").value(hasItem(DEFAULT_ATTR_8)))
            .andExpect(jsonPath("$.[*].attr9").value(hasItem(DEFAULT_ATTR_9)))
            .andExpect(jsonPath("$.[*].attr10").value(hasItem(DEFAULT_ATTR_10)));
    }

    @Test
    @Transactional
    public void getDictionary() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        // Get the dictionary
        restDictionaryMockMvc.perform(get("/api/dictionaries/{id}", dictionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dictionary.getId().intValue()))
            .andExpect(jsonPath("$.dictName").value(DEFAULT_DICT_NAME.toString()))
            .andExpect(jsonPath("$.dictCode").value(DEFAULT_DICT_CODE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.seqNo").value(DEFAULT_SEQ_NO))
            .andExpect(jsonPath("$.attr1").value(DEFAULT_ATTR_1.toString()))
            .andExpect(jsonPath("$.attr2").value(DEFAULT_ATTR_2.toString()))
            .andExpect(jsonPath("$.attr3").value(DEFAULT_ATTR_3.toString()))
            .andExpect(jsonPath("$.attr4").value(DEFAULT_ATTR_4.toString()))
            .andExpect(jsonPath("$.attr5").value(DEFAULT_ATTR_5.toString()))
            .andExpect(jsonPath("$.attr6").value(DEFAULT_ATTR_6))
            .andExpect(jsonPath("$.attr7").value(DEFAULT_ATTR_7))
            .andExpect(jsonPath("$.attr8").value(DEFAULT_ATTR_8))
            .andExpect(jsonPath("$.attr9").value(DEFAULT_ATTR_9))
            .andExpect(jsonPath("$.attr10").value(DEFAULT_ATTR_10));
    }

    @Test
    @Transactional
    public void getNonExistingDictionary() throws Exception {
        // Get the dictionary
        restDictionaryMockMvc.perform(get("/api/dictionaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDictionary() throws Exception {
        // Initialize the database
        dictionaryService.save(dictionary);

        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();

        // Update the dictionary
        Dictionary updatedDictionary = dictionaryRepository.findOne(dictionary.getId());
        updatedDictionary
            .dictName(UPDATED_DICT_NAME)
            .dictCode(UPDATED_DICT_CODE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .seqNo(UPDATED_SEQ_NO)
            .attr1(UPDATED_ATTR_1)
            .attr2(UPDATED_ATTR_2)
            .attr3(UPDATED_ATTR_3)
            .attr4(UPDATED_ATTR_4)
            .attr5(UPDATED_ATTR_5)
            .attr6(UPDATED_ATTR_6)
            .attr7(UPDATED_ATTR_7)
            .attr8(UPDATED_ATTR_8)
            .attr9(UPDATED_ATTR_9)
            .attr10(UPDATED_ATTR_10);

        restDictionaryMockMvc.perform(put("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDictionary)))
            .andExpect(status().isOk());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
        Dictionary testDictionary = dictionaryList.get(dictionaryList.size() - 1);
        assertThat(testDictionary.getDictName()).isEqualTo(UPDATED_DICT_NAME);
        assertThat(testDictionary.getDictCode()).isEqualTo(UPDATED_DICT_CODE);
        assertThat(testDictionary.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testDictionary.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testDictionary.getSeqNo()).isEqualTo(UPDATED_SEQ_NO);
        assertThat(testDictionary.getAttr1()).isEqualTo(UPDATED_ATTR_1);
        assertThat(testDictionary.getAttr2()).isEqualTo(UPDATED_ATTR_2);
        assertThat(testDictionary.getAttr3()).isEqualTo(UPDATED_ATTR_3);
        assertThat(testDictionary.getAttr4()).isEqualTo(UPDATED_ATTR_4);
        assertThat(testDictionary.getAttr5()).isEqualTo(UPDATED_ATTR_5);
        assertThat(testDictionary.getAttr6()).isEqualTo(UPDATED_ATTR_6);
        assertThat(testDictionary.getAttr7()).isEqualTo(UPDATED_ATTR_7);
        assertThat(testDictionary.getAttr8()).isEqualTo(UPDATED_ATTR_8);
        assertThat(testDictionary.getAttr9()).isEqualTo(UPDATED_ATTR_9);
        assertThat(testDictionary.getAttr10()).isEqualTo(UPDATED_ATTR_10);

        // Validate the Dictionary in Elasticsearch
        Dictionary dictionaryEs = dictionarySearchRepository.findOne(testDictionary.getId());
        assertThat(dictionaryEs).isEqualToComparingFieldByField(testDictionary);
    }

    @Test
    @Transactional
    public void updateNonExistingDictionary() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();

        // Create the Dictionary

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDictionaryMockMvc.perform(put("/api/dictionaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictionary)))
            .andExpect(status().isCreated());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDictionary() throws Exception {
        // Initialize the database
        dictionaryService.save(dictionary);

        int databaseSizeBeforeDelete = dictionaryRepository.findAll().size();

        // Get the dictionary
        restDictionaryMockMvc.perform(delete("/api/dictionaries/{id}", dictionary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dictionaryExistsInEs = dictionarySearchRepository.exists(dictionary.getId());
        assertThat(dictionaryExistsInEs).isFalse();

        // Validate the database is empty
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDictionary() throws Exception {
        // Initialize the database
        dictionaryService.save(dictionary);

        // Search the dictionary
        restDictionaryMockMvc.perform(get("/api/_search/dictionaries?query=id:" + dictionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].dictName").value(hasItem(DEFAULT_DICT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dictCode").value(hasItem(DEFAULT_DICT_CODE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].seqNo").value(hasItem(DEFAULT_SEQ_NO)))
            .andExpect(jsonPath("$.[*].attr1").value(hasItem(DEFAULT_ATTR_1.toString())))
            .andExpect(jsonPath("$.[*].attr2").value(hasItem(DEFAULT_ATTR_2.toString())))
            .andExpect(jsonPath("$.[*].attr3").value(hasItem(DEFAULT_ATTR_3.toString())))
            .andExpect(jsonPath("$.[*].attr4").value(hasItem(DEFAULT_ATTR_4.toString())))
            .andExpect(jsonPath("$.[*].attr5").value(hasItem(DEFAULT_ATTR_5.toString())))
            .andExpect(jsonPath("$.[*].attr6").value(hasItem(DEFAULT_ATTR_6)))
            .andExpect(jsonPath("$.[*].attr7").value(hasItem(DEFAULT_ATTR_7)))
            .andExpect(jsonPath("$.[*].attr8").value(hasItem(DEFAULT_ATTR_8)))
            .andExpect(jsonPath("$.[*].attr9").value(hasItem(DEFAULT_ATTR_9)))
            .andExpect(jsonPath("$.[*].attr10").value(hasItem(DEFAULT_ATTR_10)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dictionary.class);
        Dictionary dictionary1 = new Dictionary();
        dictionary1.setId(1L);
        Dictionary dictionary2 = new Dictionary();
        dictionary2.setId(dictionary1.getId());
        assertThat(dictionary1).isEqualTo(dictionary2);
        dictionary2.setId(2L);
        assertThat(dictionary1).isNotEqualTo(dictionary2);
        dictionary1.setId(null);
        assertThat(dictionary1).isNotEqualTo(dictionary2);
    }
}
