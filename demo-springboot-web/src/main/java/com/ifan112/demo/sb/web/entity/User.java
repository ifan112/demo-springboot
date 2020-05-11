package com.ifan112.demo.sb.web.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {

    private Integer id;

    private String username;

    private String password;


}
