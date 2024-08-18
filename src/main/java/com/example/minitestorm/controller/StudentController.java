package com.example.minitestorm.controller;

import com.example.minitestorm.model.Student;
import com.example.minitestorm.model.StudentForm;
import com.example.minitestorm.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/students")
@PropertySource("classpath:upload_file.properties")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @GetMapping("")
    public String index(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("student", new Student());
        return "create";
    }

    @Value("${file-upload}")
    private String upload;
    @PostMapping("/save")
    public String save(StudentForm studentForm) {
//        lay anh ra
        MultipartFile file = studentForm.getImage();
//        lay ten file
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Student student = new Student();
        student.setImage(fileName);
        student.setMark(studentForm.getMark());
        student.setAddress(studentForm.getAddress());
        student.setDob(studentForm.getDob());
        student.setLastName(studentForm.getLastName());
        student.setFistName(studentForm.getFistName());
        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "update";
    }
    @PostMapping("/update")
    public String update(StudentForm studentForm) {
  //      lay anh ra
        MultipartFile file = studentForm.getImage();
//        lay ten file
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Student student = new Student();
        student.setId(studentForm.getId());
        student.setFistName(studentForm.getFistName());
        student.setLastName(studentForm.getLastName());
        student.setDob(studentForm.getDob());
        student.setAddress(studentForm.getAddress());
        student.setMark(studentForm.getMark());
        student.setImage(fileName);
        studentService.update(student.getId(),student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(Student student, RedirectAttributes redirectAttributes) {
        studentService.delete(student.getId());
        redirectAttributes.addFlashAttribute("success", "Removed student successfully!");
        return "redirect:/students";
    }
    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "view";
    }

}
