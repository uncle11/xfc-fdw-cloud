package com.xfc.graph.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.xfc.graph.dto.*;
import com.xfc.graph.repository.GraphRepository;
import com.xfc.graph.service.GraphService;
import com.xfc.graph.utils.StringUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @version 1.0
 * @description: TODO
 * @author: xfc
 * @date 2024-08-18 10:37
 */
@RestController
@RequestMapping("/manage")
@Api(tags = "图数据库管理")
public class GraphManageController {

    @Autowired
    GraphService graphService;


    @PostMapping(value = "/createNode")
    public GraphDTO createNode(@Validated @RequestBody AddNodeDTO addNodeDTO) {
        return graphService.createNode(addNodeDTO);
    }

    @PostMapping(value = "/createLink")
    public GraphDTO createLink(@Validated @RequestBody  AddLinkDTO addLinkDTO) {
        return graphService.createLink(addLinkDTO);
    }
    @GetMapping("/graph")
    public GraphDTO getGraph(@RequestParam String domain){
        return graphService.getGraph(domain);
    }

}
