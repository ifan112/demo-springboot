package com.ifan112.demo.springboot.test.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Module {

    private Integer id;
    private Integer projectId;
    private String groupId;
    private String artifactId;
    private String version;
    private String packing;
    private Integer parentModuleId;

}
