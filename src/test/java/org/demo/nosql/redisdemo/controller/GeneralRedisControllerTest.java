package org.demo.nosql.redisdemo.controller;

import org.demo.nosql.redisdemo.service.GeneralCommandRedisService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GeneralRedisControllerTest {

    @Mock
    private GeneralCommandRedisService service;

    @InjectMocks
    private GeneralRedisController operationController;

    private MockMvc mvc;


    @Before
    public void setUp() {
        service = mock(GeneralCommandRedisService.class);
        initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(operationController)
                .build();
    }

    @Test
    public void whenGet_DBSize_returnOK() throws Exception {

        when(service.dbSize()).thenReturn(Optional.of(5L));
        mvc.perform(MockMvcRequestBuilders.get("/redis/commands/dbsize"))
                .andExpect(status().isOk());
    }
}
