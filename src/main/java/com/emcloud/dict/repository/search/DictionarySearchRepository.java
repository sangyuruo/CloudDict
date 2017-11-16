package com.emcloud.dict.repository.search;

import com.emcloud.dict.domain.Dictionary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Dictionary entity.
 */
public interface DictionarySearchRepository extends ElasticsearchRepository<Dictionary, Long> {
}
