package main;

import java.util.List;

/**
 * Created by winmaster on 2017/3/7.
 */
public class Province {
    private String provinceContext;
    private List <Field> provinceFields;
    private List<Integer> provinceFieldInteger;

    public void setProvinceContext(String provinceContext) {
        this.provinceContext = provinceContext;
    }

    public String getProvinceContext() {
        return provinceContext;
    }

    public void setProvinceFields(List<Field> provinceFields) {
        this.provinceFields = provinceFields;
    }

    public List<Field> getProvinceFields() {
        return provinceFields;
    }

    public List<Integer> getProvinceFieldInteger() {
        return provinceFieldInteger;
    }

    public void setProvinceFieldInteger(List<Integer> provinceFieldInteger) {
        this.provinceFieldInteger = provinceFieldInteger;
    }
}
