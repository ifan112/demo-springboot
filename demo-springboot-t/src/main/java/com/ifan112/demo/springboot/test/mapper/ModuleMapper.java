package com.ifan112.demo.springboot.test.mapper;

import com.ifan112.demo.springboot.test.model.Module;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ModuleMapper {

    @Select("select id, project_id, group_id, artifact_id, packing, version, parent_module_id from module")
    @Results({
            @Result(column = "project_id", property = "projectId"),
            @Result(column = "group_id", property = "groupId"),
            @Result(column = "artifact_id", property = "artifactId"),
            @Result(column = "parent_module_id", property = "parentModuleId")
    })
    List<Module> selectAllModule();
}
