package com.xfc.graph.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.xfc.graph.dto.*;

import java.util.HashMap;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-03 14:46
 */
public interface GraphService {
    String saveNode();

    String saveNAndR();

    GraphDTO createNode(AddNodeDTO addNodeDTO);

    GraphDTO createLink(AddLinkDTO addLinkDTO);

    GraphDTO getGraph(String label);
}
