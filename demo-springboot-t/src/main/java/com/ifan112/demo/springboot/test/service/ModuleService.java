package com.ifan112.demo.springboot.test.service;

import com.ifan112.demo.springboot.test.mapper.ModuleMapper;
import com.ifan112.demo.springboot.test.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;

    public List<Module> getModulesOfProject(Integer projectId) {
        List<Module> moduleList = moduleMapper.selectAllModule();

        Stream<Module> moduleListOfProject
                = moduleList.stream().filter(m -> projectId.equals(m.getProjectId()));

        return moduleListOfProject.collect(Collectors.toList());
    }

}
