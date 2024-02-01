package com.xfc.ltree.service;

import com.xfc.ltree.dto.LtreeCatalogDTO;
import com.xfc.ltree.entities.LtreeCatalog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chenss
 * @since 2024-01-31
 */
public interface ILtreeCatalogService extends IService<LtreeCatalog> {

    LtreeCatalogDTO getCatalogList();

    String addCatalog(LtreeCatalog ltreeCatalog);

    String updateCatalog(LtreeCatalog ltreeCatalog) throws Exception;

    void delCatalog(String id) throws Exception;
}
