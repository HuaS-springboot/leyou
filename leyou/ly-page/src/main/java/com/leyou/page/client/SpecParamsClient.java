package com.leyou.page.client;



import com.leyou.item.api.SpecParamsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface SpecParamsClient extends SpecParamsApi {

}
