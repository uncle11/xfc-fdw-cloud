package com.xfc.dict.controller;


import com.xfc.dict.entities.Student;
import com.xfc.dict.service.IStudentService;
import com.xfc.dict.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    IStudentService studentService;

    @PostMapping("")
    public boolean saveStudent(@RequestBody Student student){
      return  studentService.save(student);
    }

    @GetMapping("/{id}")
    public StudentVO getStudentInfoById(@PathVariable("id")String id ){
       return studentService.getStudentInfoById(id);
    }



}
