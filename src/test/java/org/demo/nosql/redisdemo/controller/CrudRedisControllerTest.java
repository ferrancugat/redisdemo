package org.demo.nosql.redisdemo.controller;

import org.demo.nosql.redisdemo.service.CrudCommandRedisService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CrudRedisControllerTest {

    @Mock
    private CrudCommandRedisService service;

    @InjectMocks
    private CrudRedisController operationController;

    private MockMvc mvc;


    @Before
    public void setUp() {
        service = mock(CrudCommandRedisService.class);
        initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(operationController)
                .build();
    }

    @Test
    public void whenPut_set_returnOK() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/redis/commands/set")
                .param("key", "key")
                .param("value", "value"))
                .andExpect(status().isNoContent());
    }
}
