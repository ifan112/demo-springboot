package com.ifan112.demo.springboot.test.mapper;

import com.ifan112.demo.springboot.test.model.Module;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ModuleMapper {

    String COLUMN_ID = "id";
    String COLUMN_OTHER = "project_id, group_id, artifact_id, packing, version, parent_module_id";
    String COLUMN_ALL = COLUMN_ID + "," + COLUMN_OTHER;

    @Select("select " + COLUMN_ALL + " from module")
    @Results({
            @Result(column = "project_id", property = "projectId"),
            @Result(column = "group_id", property = "groupId"),
            @Result(column = "artifact_id", property = "artifactId"),
            @Result(column = "parent_module_id", property = "parentModuleId")
    })
    List<Module> selectAllModule();

    @Insert("INSERT INTO module (" + COLUMN_OTHER + ") " +
            "VALUES ( #{module.projectId}, #{module.groupId}, #{module.artifactId}, #{module.packing}, #{module.version}, #{module.parentModuleId}) ")
    int insert(@Param("module") Module module);

    @Delete("DELETE FROM module where " + COLUMN_ID + " = #{id}")
    int deleteById(@Param("id") Integer id);
}
