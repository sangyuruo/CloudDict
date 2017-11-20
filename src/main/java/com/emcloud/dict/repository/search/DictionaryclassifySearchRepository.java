package com.emcloud.dict.repository.search;

import com.emcloud.dict.domain.DictionaryClassify;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DictionaryClassify entity.
 */
public interface DictionaryclassifySearchRepository extends ElasticsearchRepository<DictionaryClassify, Long> {
}
