package com.xfc.ltree.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xfc.common.utils.BeanCopyUtils;
import com.xfc.ltree.dto.LtreeCatalogDTO;
import com.xfc.ltree.entities.LtreeCatalog;
import com.xfc.ltree.mapper.LtreeCatalogMapper;
import com.xfc.ltree.service.ILtreeCatalogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenss
 * @since 2024-01-31
 */
@Service
public class LtreeCatalogServiceImpl extends ServiceImpl<LtreeCatalogMapper, LtreeCatalog> implements ILtreeCatalogService {
    @Override
    public LtreeCatalogDTO getCatalogList() {
        //设置原始根节点（需要写死一个原始根节点，我在这里直接写死了，可以通过yml配置文件动态指定）
        LtreeCatalogDTO resTree=new LtreeCatalogDTO("1",null,"目录树",null);
        List<LtreeCatalogDTO>resChildren=new ArrayList<>();
        List<LtreeCatalogDTO>childrenList=new ArrayList<>();
        List<LtreeCatalogDTO>rootList=new ArrayList<>();
        LambdaQueryWrapper<LtreeCatalog> queryWrapper=new LambdaQueryWrapper<>();
        List<LtreeCatalog> ltreeCatalogs = baseMapper.selectList(queryWrapper.orderByDesc(LtreeCatalog::getCreateTime));
        for (LtreeCatalog ltreeCatalog : ltreeCatalogs) {
            if(("1").equals(ltreeCatalog.getId())){
                continue;
            }
            LtreeCatalog parentCatalog= baseMapper.getParentByChildId(ltreeCatalog.getId());
            if("1".equals(parentCatalog.getId())){
                rootList.add(new LtreeCatalogDTO(ltreeCatalog.getId(),parentCatalog.getId(),ltreeCatalog.getName(),null));

            }else {
                childrenList.add(new LtreeCatalogDTO(ltreeCatalog.getId(),parentCatalog.getId(),ltreeCatalog.getName(),null));
            }
        }
        for (LtreeCatalogDTO rootNode : rootList) {
            LtreeCatalogDTO tree=buildTree(childrenList,rootNode);
            resChildren.add(tree);
        }
        resTree.setChildren(resChildren);
        return resTree;
    }
    public  LtreeCatalogDTO buildTree(List<LtreeCatalogDTO> ltreeCatalogDTOS, LtreeCatalogDTO catalogP) {
        List<LtreeCatalogDTO> childrenList = new ArrayList<>();
        for (LtreeCatalogDTO catalogC : ltreeCatalogDTOS) {
            // 当前数据的 parentId 等于 父节点的 id，则该数据是当前父级节点的子级。
            if (catalogC!=null && catalogC.getParentId().equals(catalogP.getId())) {
                // 递归调用
                childrenList.add(buildTree(ltreeCatalogDTOS, catalogC));
            }
        }
        catalogP.setChildren(childrenList);
        return catalogP;
    }


    @Override
    public String addCatalog(LtreeCatalog ltreeCatalog) {
        return baseMapper.insert(ltreeCatalog)==1?"新增成功":"新增失败";
    }

    @Override
    public String updateCatalog(LtreeCatalog ltreeCatalog) throws Exception {
        LtreeCatalog ltreeCatalogExist = baseMapper.selectById(ltreeCatalog.getId());
        if(ltreeCatalogExist==null){
            throw new Exception("节点不存在");
        }
        return baseMapper.updateById(ltreeCatalog)==1?"更新成功":"更新失败";
    }

    @Override
    public void delCatalog(String id) throws Exception {
        LtreeCatalog ltreeCatalog = baseMapper.selectById(id);
        if(ltreeCatalog==null){
            throw new Exception("节点不存在");
        }
         baseMapper.delCatalog(ltreeCatalog.getPath());
    }
}
