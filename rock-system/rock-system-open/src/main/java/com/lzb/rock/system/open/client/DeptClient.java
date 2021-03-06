package com.lzb.rock.system.open.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import com.lzb.rock.base.config.FeignClientCfg;
import com.lzb.rock.system.open.facade.DeptFacade;

/**
 * 部门表 
 * 远程调用客户端
 * @author lzb
 * @Date 2019-10-24 21:51:04
 */
@FeignClient(name= DeptFacade.SERVICE_NAME,configuration = FeignClientCfg.class)
public interface DeptClient extends DeptFacade{
}
