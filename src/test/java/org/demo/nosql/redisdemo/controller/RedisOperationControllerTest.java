package org.demo.nosql.redisdemo.controller;

import org.demo.nosql.redisdemo.command.OperationExecutor;
import org.demo.nosql.redisdemo.command.SequentialOperationExecutor;
import org.demo.nosql.redisdemo.domain.DataValue;
import org.demo.nosql.redisdemo.domain.RedisResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.demo.nosql.redisdemo.domain.RedisResponse.RESPONSE_OK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RedisOperationControllerTest {

    @Mock
    private OperationExecutor executor;

    @InjectMocks
    private RedisOperationController operationController;

    private MockMvc mvc;


    @Before
    public void setUp() {
        executor = mock(SequentialOperationExecutor.class);
        initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(operationController)
                .build();
    }

    @Test
    public void whenGet_DBSize_returnOK() throws Exception {

        when(executor.execute(any())).thenReturn(new RedisResponse(RESPONSE_OK, DataValue.stringDataValue(5)));
        mvc.perform(MockMvcRequestBuilders.get("/redis/commands/dbsize"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPut_set_returnOK() throws Exception {
        when(executor.execute(any())).thenReturn(new RedisResponse(RESPONSE_OK));
        mvc.perform(MockMvcRequestBuilders.put("/redis/commands/set")
                .param("key", "key")
                .param("value", "value"))
                .andExpect(status().isOk());
    }
}
