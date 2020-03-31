package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    PRICE_CANNOT_BE_BULL(400,"价格不能为空"),
    CATEGORY_NOT_FOND(404,"商品分类没查到"),
    BRAND_NOT_FIND(500,"品牌没找到"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    UPLOAD_FILE_ERROR(500,"上传文件失败"),
    INVALID_FILE_TYPE(500,"无效的文件类型"),
    GOODS_NOT_FIND(500,"商品没找到"),
    SPEC_GROUP_NOT_FIND(500,"规格组没找到"),
    SPEC_PARAMS_NOT_FIND(500,"规格参数没找到"),
    SAVE_GOODS_ERROR(500,"新增商品失败"),
    SPU_ID_NOT_NULL(500,"商品的id不能为空"),
    UPDATE_SPU_ERROR(500,"修改商品失败"),
    SPU_DETAIL_NULL(500,"商品细节查询失败"),
    SPU_NOT_FIND(500,"商品查询失败"),
    SKU_NOT_FIND(500,"商品的sku没查到")
    ;
    private Integer code;
    private String message;

}
