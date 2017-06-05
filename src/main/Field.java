package main;

import java.util.List;

/**
 * Created by winmaster on 2017/3/7.
 */
public class Field {
    private String fieldContext;
    private List<SubField> fieldSubFields;
    private Integer fieldInteger;

    public void setFieldContext(String fieldContext) {
        this.fieldContext = fieldContext;
    }

    public String getFieldContext() {
        return fieldContext;
    }

    public void setFieldSubFields(List<SubField> fieldSubFields) {
        this.fieldSubFields = fieldSubFields;
    }

    public List<SubField> getFieldSubFields() {
        return fieldSubFields;
    }

    public Integer getFieldInteger() {
        return fieldInteger;
    }

    public void setFieldInteger(Integer fieldInteger) {
        this.fieldInteger = fieldInteger;
    }
}
