package com.example.liteflowdemo.service;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.DefaultContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CoreFlow {

    @Resource
    private FlowExecutor flowExecutor;

    public void testConfig() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg", DefaultContext.class);
        System.out.println("response = " + response);
    }
}
