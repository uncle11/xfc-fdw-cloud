package com.xfc.graph.service.impl;

import com.xfc.graph.entity.Knows;
import com.xfc.graph.entity.PersonNode;
import com.xfc.graph.repository.KnowsRepository;
import com.xfc.graph.repository.PersonRepository;
import com.xfc.graph.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2024-03-03 14:46
 */
@Service
public class GraphServiceImpl implements GraphService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private KnowsRepository knowsRepository;
    @Override
    public String saveNAndR() {
        PersonNode person = new PersonNode();
        person.setName("小肥肠1");
        personRepository.save(person);

        PersonNode person1 = new PersonNode();
        person1.setName("豆豆");
        personRepository.save(person1);

        // 创建关系并设置目标节点
        Knows knowsRelationship = new Knows();
        knowsRelationship.setPersonNode(person1); // 设置关系的目标节点

        if (person.getKnows() == null) {
            person.setKnows(new ArrayList<>());
        }
        person.getKnows().add(knowsRelationship);
        personRepository.save(person);
        return "新增成功";
    }

    @Override
    public String saveNode() {
        PersonNode person = new PersonNode();
        person.setName("小肥肠");
        personRepository.save(person);
        return "新增成功";
    }
}
