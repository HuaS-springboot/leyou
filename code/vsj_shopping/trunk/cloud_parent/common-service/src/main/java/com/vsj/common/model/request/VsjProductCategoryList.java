package com.vsj.common.model.request;

import com.vsj.model.VsjProductCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class VsjProductCategoryList implements Serializable {

    private List<VsjProductCategory> categoryList ;
}
