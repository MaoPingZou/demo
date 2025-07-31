package com.example.liteflowdemo;

import com.example.liteflowdemo.entity.RiskCommonReq;
import com.example.liteflowdemo.entity.RiskParamContext;
import com.yomahub.liteflow.core.NodeComponent;

public abstract class AbstractCmp extends NodeComponent {

    @Override
    public void process() {
        System.out.println("流程开始");
        doProcess(new RiskParamContext(), new RiskCommonReq());
        System.out.println("流程开始");
    }

    public abstract void doProcess(RiskParamContext riskParamContext, RiskCommonReq riskCommonReq);

}
