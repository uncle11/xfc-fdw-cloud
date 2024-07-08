package com.xfc.dict.service.impl;

import com.xfc.common.utils.BeanCopyUtils;
import com.xfc.dict.entities.Student;
import com.xfc.dict.mapper.StudentMapper;
import com.xfc.dict.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xfc.dict.vo.StudentVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xfc
 * @since 2024-07-07
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    @Override
    public StudentVO getStudentInfoById(String id) {
        Student student = baseMapper.selectById(id);
        StudentVO studentVO= BeanCopyUtils.copyBean(student,StudentVO.class);
        return studentVO;
    }
}
