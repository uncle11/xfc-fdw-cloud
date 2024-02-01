package com.xfc.ltree.controller;


import com.xfc.ltree.dto.LtreeCatalogDTO;
import com.xfc.ltree.entities.LtreeCatalog;
import com.xfc.ltree.service.ILtreeCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenss
 * @since 2024-01-31
 */
@RestController
@RequestMapping("/ltree-catalog")
public class LtreeCatalogController {
    @Autowired
    ILtreeCatalogService ltreeCatalogService;

    @GetMapping("/search")
    public LtreeCatalogDTO getCatalogList() {
        return ltreeCatalogService.getCatalogList();
    }

    @PostMapping("")
    public String addCatalog(@RequestBody LtreeCatalog ltreeCatalog) {
        return ltreeCatalogService.addCatalog(ltreeCatalog);
    }

    @PutMapping("")
    public String updateCatalog(@RequestBody LtreeCatalog ltreeCatalog) throws Exception {
        return ltreeCatalogService.updateCatalog(ltreeCatalog);
    }

    @DeleteMapping("/{id}")
    public void delCatalog(@PathVariable("id") String id) throws Exception {
         ltreeCatalogService.delCatalog(id);
    }
}
