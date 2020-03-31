package com.leyou.search.client;



import com.leyou.item.api.SpecParamsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface SpecParamsClient extends SpecParamsApi {

}
