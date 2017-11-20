package com.emcloud.dict.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 字典表
 * @author Capejor
 */
@ApiModel(description = "字典表 @author Capejor")
@Entity
@Table(name = "dictionary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dictionary")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 字典名称
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "字典名称", required = true)
    @Column(name = "dict_name", length = 64, nullable = false)
    private String dictName;

    /**
     * 字典代码
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "字典代码", required = true)
    @Column(name = "dict_code", length = 20, nullable = false)
    private String dictCode;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @Column(name = "start_time")
    private Instant startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @Column(name = "end_time")
    private Instant endTime;

    /**
     * 序号
     */
    @NotNull
    @ApiModelProperty(value = "序号", required = true)
    @Column(name = "seq_no", nullable = false)
    private Integer seqNo;

    /**
     * 预留字段
     */
    @Size(max = 200)
    @ApiModelProperty(value = "预留字段")
    @Column(name = "attr_1", length = 200)
    private String attr1;

    @Size(max = 200)
    @Column(name = "attr_2", length = 200)
    private String attr2;

    @Size(max = 200)
    @Column(name = "attr_3", length = 200)
    private String attr3;

    @Size(max = 200)
    @Column(name = "attr_4", length = 200)
    private String attr4;

    @Size(max = 200)
    @Column(name = "attr_5", length = 200)
    private String attr5;

    @Column(name = "attr_6")
    private Integer attr6;

    @Column(name = "attr_7")
    private Integer attr7;

    @Column(name = "attr_8")
    private Integer attr8;

    @Column(name = "attr_9")
    private Integer attr9;

    @Column(name = "attr_10")
    private Integer attr10;

    @OneToMany(mappedBy = "dictionary")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DictionaryClassify> dictionaryClassifies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictName() {
        return dictName;
    }

    public Dictionary dictName(String dictName) {
        this.dictName = dictName;
        return this;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictCode() {
        return dictCode;
    }

    public Dictionary dictCode(String dictCode) {
        this.dictCode = dictCode;
        return this;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Dictionary startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Dictionary endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public Dictionary seqNo(Integer seqNo) {
        this.seqNo = seqNo;
        return this;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getAttr1() {
        return attr1;
    }

    public Dictionary attr1(String attr1) {
        this.attr1 = attr1;
        return this;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public Dictionary attr2(String attr2) {
        this.attr2 = attr2;
        return this;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public Dictionary attr3(String attr3) {
        this.attr3 = attr3;
        return this;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return attr4;
    }

    public Dictionary attr4(String attr4) {
        this.attr4 = attr4;
        return this;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public String getAttr5() {
        return attr5;
    }

    public Dictionary attr5(String attr5) {
        this.attr5 = attr5;
        return this;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    public Integer getAttr6() {
        return attr6;
    }

    public Dictionary attr6(Integer attr6) {
        this.attr6 = attr6;
        return this;
    }

    public void setAttr6(Integer attr6) {
        this.attr6 = attr6;
    }

    public Integer getAttr7() {
        return attr7;
    }

    public Dictionary attr7(Integer attr7) {
        this.attr7 = attr7;
        return this;
    }

    public void setAttr7(Integer attr7) {
        this.attr7 = attr7;
    }

    public Integer getAttr8() {
        return attr8;
    }

    public Dictionary attr8(Integer attr8) {
        this.attr8 = attr8;
        return this;
    }

    public void setAttr8(Integer attr8) {
        this.attr8 = attr8;
    }

    public Integer getAttr9() {
        return attr9;
    }

    public Dictionary attr9(Integer attr9) {
        this.attr9 = attr9;
        return this;
    }

    public void setAttr9(Integer attr9) {
        this.attr9 = attr9;
    }

    public Integer getAttr10() {
        return attr10;
    }

    public Dictionary attr10(Integer attr10) {
        this.attr10 = attr10;
        return this;
    }

    public void setAttr10(Integer attr10) {
        this.attr10 = attr10;
    }

    public Set<DictionaryClassify> getDictionaryClassifies() {
        return dictionaryClassifies;
    }

    public Dictionary dictionaryClassifies(Set<DictionaryClassify> dictionaryClassifies) {
        this.dictionaryClassifies = dictionaryClassifies;
        return this;
    }

    public Dictionary addDictionaryClassify(DictionaryClassify DictionaryClassify) {
        this.dictionaryClassifies.add(DictionaryClassify);
        DictionaryClassify.setDictionary(this);
        return this;
    }

    public Dictionary removeDictionaryClassify(DictionaryClassify DictionaryClassify) {
        this.dictionaryClassifies.remove(DictionaryClassify);
        DictionaryClassify.setDictionary(null);
        return this;
    }

    public void setDictionaryClassifies(Set<DictionaryClassify> dictionaryClassifies) {
        this.dictionaryClassifies = dictionaryClassifies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dictionary dictionary = (Dictionary) o;
        if (dictionary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dictionary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dictionary{" +
            "id=" + getId() +
            ", dictName='" + getDictName() + "'" +
            ", dictCode='" + getDictCode() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", seqNo='" + getSeqNo() + "'" +
            ", attr1='" + getAttr1() + "'" +
            ", attr2='" + getAttr2() + "'" +
            ", attr3='" + getAttr3() + "'" +
            ", attr4='" + getAttr4() + "'" +
            ", attr5='" + getAttr5() + "'" +
            ", attr6='" + getAttr6() + "'" +
            ", attr7='" + getAttr7() + "'" +
            ", attr8='" + getAttr8() + "'" +
            ", attr9='" + getAttr9() + "'" +
            ", attr10='" + getAttr10() + "'" +
            "}";
    }
}
