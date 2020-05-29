package com.ifan112.demo.springboot.test.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleDep {

    private Integer id;
    private Integer moduleId;

    private String groupId;
    private String artifactId;
    private String vesion;
}
