package com.ifan112.demo.springboot.test.controller;

import com.ifan112.demo.springboot.test.model.Module;
import com.ifan112.demo.springboot.test.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping("/{id}/modules")
    public List<Module> queryModulesOfProject(@PathVariable(name = "id") Integer projectId) {
        return moduleService.getModulesOfProject(projectId);
    }
}
