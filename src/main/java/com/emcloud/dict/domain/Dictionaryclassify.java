package com.emcloud.dict.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 字典分类表
 * @author Capejor
 */
@ApiModel(description = "字典分类表 @author Capejor")
@Entity
@Table(name = "dictionary_classify")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dictionaryclassify")
public class Dictionaryclassify implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 字典代码
     */
    @Size(max = 20)
    @ApiModelProperty(value = "字典代码")
    @Column(name = "dict_code", length = 20)
    private String dictCode;

    /**
     * 分类代码
     */
    @NotNull
    @ApiModelProperty(value = "分类代码", required = true)
    @Column(name = "dict_classify_code", nullable = false)
    private Integer dictClassifyCode;

    /**
     * 分类值
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "分类值", required = true)
    @Column(name = "dict_classify_value", length = 100, nullable = false)
    private String dictClassifyValue;

    /**
     * 父分类代码
     */
    @ApiModelProperty(value = "父分类代码")
    @Column(name = "parent_classify_code")
    private Integer parentClassifyCode;

    /**
     * 序号
     */
    @NotNull
    @ApiModelProperty(value = "序号", required = true)
    @Column(name = "jhi_sort", nullable = false)
    private Integer sort;

    /**
     * 是否有效
     */
    @NotNull
    @ApiModelProperty(value = "是否有效", required = true)
    @Column(name = "jhi_enable", nullable = false)
    private Boolean enable;

    /**
     * 备注
     */
    @Size(max = 200)
    @ApiModelProperty(value = "备注")
    @Column(name = "remark", length = 200)
    private String remark;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictCode() {
        return dictCode;
    }

    public Dictionaryclassify dictCode(String dictCode) {
        this.dictCode = dictCode;
        return this;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public Integer getDictClassifyCode() {
        return dictClassifyCode;
    }

    public Dictionaryclassify dictClassifyCode(Integer dictClassifyCode) {
        this.dictClassifyCode = dictClassifyCode;
        return this;
    }

    public void setDictClassifyCode(Integer dictClassifyCode) {
        this.dictClassifyCode = dictClassifyCode;
    }

    public String getDictClassifyValue() {
        return dictClassifyValue;
    }

    public Dictionaryclassify dictClassifyValue(String dictClassifyValue) {
        this.dictClassifyValue = dictClassifyValue;
        return this;
    }

    public void setDictClassifyValue(String dictClassifyValue) {
        this.dictClassifyValue = dictClassifyValue;
    }

    public Integer getParentClassifyCode() {
        return parentClassifyCode;
    }

    public Dictionaryclassify parentClassifyCode(Integer parentClassifyCode) {
        this.parentClassifyCode = parentClassifyCode;
        return this;
    }

    public void setParentClassifyCode(Integer parentClassifyCode) {
        this.parentClassifyCode = parentClassifyCode;
    }

    public Integer getSort() {
        return sort;
    }

    public Dictionaryclassify sort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean isEnable() {
        return enable;
    }

    public Dictionaryclassify enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getRemark() {
        return remark;
    }

    public Dictionaryclassify remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        Dictionaryclassify dictionaryclassify = (Dictionaryclassify) o;
        if (dictionaryclassify.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dictionaryclassify.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dictionaryclassify{" +
            "id=" + getId() +
            ", dictCode='" + getDictCode() + "'" +
            ", dictClassifyCode='" + getDictClassifyCode() + "'" +
            ", dictClassifyValue='" + getDictClassifyValue() + "'" +
            ", parentClassifyCode='" + getParentClassifyCode() + "'" +
            ", sort='" + getSort() + "'" +
            ", enable='" + isEnable() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
