package com.example.education.contrroller;

import com.example.education.service.ApproveService;
import com.example.education.service.CourseService;
import com.example.education.service.StudentCourseService;
import com.example.education.service.StudentService;
import com.example.education.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ASUS
 */
@Controller
public class ApproveController {
    @Autowired
    ApproveService approveService;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    StudentCourseService studentCourseService;

    @GetMapping(value = "/haveLogin/updateApprove")
    @ResponseBody
    public Result updateApprove(@RequestParam String deleteCourseNumber, @RequestParam String addCourseNumber,
                                @RequestParam String studentNumber, @RequestParam String state,
                                @RequestParam String operator) {

        Result result = new Result();
        boolean isNull = approveService.select(deleteCourseNumber,addCourseNumber,studentNumber) == null;
        if (isNull) {
            result.setCode("201");
            result.setMessage("失败，没有相应课程，重新提交申请");
        } else {
//            String state = "通过";
            approveService.update(deleteCourseNumber,addCourseNumber,studentNumber,state,operator);
            // 通过审核后，将该学生的选课进行修改。
            String stateStr = "通过";
            if (stateStr.equals(state)) {
                changeStudentCourse(studentNumber, deleteCourseNumber,addCourseNumber);
            }

            result.setCode("200");
        }
        return result;
    }

    @PostMapping(value = "/haveLogin/saveApprove")
    @ResponseBody
    public Result save(@RequestBody Approve approve) {
        System.out.println(approve.getAddNumber());
        System.out.println("save approve");
        Result result = new Result();
        int flag = 0;
        flag = approveService.save(approve);
        if (flag == 1) {
            result.setCode("200");
        } else {
            result.setCode("202");
            result.setMessage("保存失败");
        }
        return result;
    }

    @GetMapping(value = "/haveLogin/selectApprove")
    @ResponseBody
    public Result selectApprove(@RequestParam String studentNumber) {

        Result result = new Result();
        List<Approve> approves = approveService.selectByStudentNumber(studentNumber);
        if (approves != null) {
            result.setCode("200");
            result.setData(approves);
        } else {
            result.setMessage("没有记录");
            result.setCode("202");
        }
        return result;
    }

    @GetMapping(value = "/haveLogin/selectAllApprove")
    @ResponseBody
    public Result selectAll() {
        Result result = new Result();
        List<Approve> approves = approveService.selectAll();
        result.setData(approves);
        result.setCode("200");
        return result;
    }

    @GetMapping(value = "/haveLogin/selectByState")
    @ResponseBody
    public Result selectByState(String state) {
        Result result = new Result();
        List<Approve> approves = approveService.selectByState(state);
        result.setData(approves);
        result.setCode("200");
        return result;
    }

    private void changeStudentCourse(String studentNumber, String delCourseNumber, String addCourseNumber) {
        Student student = studentService.findByNum(studentNumber);
        Course courseDel = courseService.select(delCourseNumber);
        Course courseAdd = courseService.select(addCourseNumber);
        StudentCourse studentCourseDel = studentCourseService.selectByStudentAndCourse(student, courseDel);
        studentCourseService.updateCourse(studentCourseDel,courseAdd);
    }
}
