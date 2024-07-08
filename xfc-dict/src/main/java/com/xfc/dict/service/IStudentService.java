package com.xfc.dict.service;

import com.xfc.dict.entities.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xfc.dict.vo.StudentVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
public interface IStudentService extends IService<Student> {

    StudentVO getStudentInfoById(String id);
}
