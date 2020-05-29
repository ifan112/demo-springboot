package com.ifan112.demo.springboot.test.controller;

import com.ifan112.demo.springboot.test.base.EurekaBaseTest;
import com.ifan112.demo.springboot.test.service.EurekaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class EurekaControllerTest extends EurekaBaseTest {

    @MockBean
    private EurekaService eurekaService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInfo() throws Exception {
        Mockito.when(eurekaService.getApps())
                .thenReturn(prepareRespEntity());

        mockMvc.perform(get("/eureka/info"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "应用个数: 1\n" +
                        "实例个数: 1"));

    }
}