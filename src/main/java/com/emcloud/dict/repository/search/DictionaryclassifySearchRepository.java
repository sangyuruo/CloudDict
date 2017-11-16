package com.emcloud.dict.repository.search;

import com.emcloud.dict.domain.Dictionaryclassify;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Dictionaryclassify entity.
 */
public interface DictionaryclassifySearchRepository extends ElasticsearchRepository<Dictionaryclassify, Long> {
}
