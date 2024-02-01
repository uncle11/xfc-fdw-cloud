package com.xfc.ltree.mapper;

import com.xfc.ltree.dto.LtreeCatalogDTO;
import com.xfc.ltree.entities.LtreeCatalog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenss
 * @since 2024-01-31
 */
@Mapper
public interface LtreeCatalogMapper extends BaseMapper<LtreeCatalog> {

    @Select("select * from ltree_catalog where path=( select subpath(path,0,nlevel(path)-1) from  ltree_catalog where id =#{id})")
    LtreeCatalog getParentByChildId(String id);


    @Delete("delete from ltree_catalog where path <@ #{path}::ltree;")
    void delCatalog(String path);
}
