package com.xfc.graph.controller;

import com.xfc.graph.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-03 14:46
 */
@RestController
@RequestMapping
public class GraphController {
    @Autowired
    GraphService graphService;
    @PostMapping("/save-n")
    public String saveNode(){
       return graphService.saveNode();
    }
    @PostMapping("/save-n-r")
    public String saveNAndR(){
        return graphService.saveNAndR();
    }

}
