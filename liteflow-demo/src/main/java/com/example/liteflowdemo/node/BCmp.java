package com.example.liteflowdemo.node;

import com.example.liteflowdemo.AbstractCmp;
import com.example.liteflowdemo.entity.RiskCommonReq;
import com.example.liteflowdemo.entity.RiskParamContext;
import com.yomahub.liteflow.annotation.LiteflowComponent;

@LiteflowComponent("b")
public class BCmp extends AbstractCmp {

    @Override
    public void doProcess(RiskParamContext riskParamContext, RiskCommonReq riskCommonReq) {
        System.out.println("Bcmp doProcess");
    }
}