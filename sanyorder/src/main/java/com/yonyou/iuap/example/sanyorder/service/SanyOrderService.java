package com.yonyou.iuap.example.sanyorder.service;

import com.yonyou.iuap.baseservice.bpm.service.GenericBpmService;
import com.yonyou.iuap.bpm.pojo.BPMFormJSON;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.sanyorder.dao.SanyOrderMapper;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


@Service
public class SanyOrderService extends GenericBpmService<SanyOrder>{

	/**
	 * 新增保存工单信息
	 */
    @Override
    public SanyOrder insert(SanyOrder SanyOrder) {
            SanyOrder.setOrderCode(SanyOrder.getBpmBillCode());//编码code生成
        return super.insert(SanyOrder);
    }
	@Override
	public SanyOrder save(SanyOrder SanyOrder) {
			SanyOrder.setOrderCode(SanyOrder.getBpmBillCode());//编码code生成
		return super.save(SanyOrder);
	}
	

	private SanyOrderMapper sanyOrderMapper;
	
	@Autowired
	public void setSanyOrderMapper(SanyOrderMapper sanyOrderMapper) {
		this.sanyOrderMapper = sanyOrderMapper;
		super.setIbatisMapperEx(this.sanyOrderMapper);
	}

    @Override
    public  BPMFormJSON buildVariables(SanyOrder entity)
    {
        BPMFormJSON bpmform = new BPMFormJSON();
        String userName = InvocationInfoProxy.getUsername();
        try {
            userName = URLDecoder.decode(userName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            userName =InvocationInfoProxy.getUsername();
        }
        String title = userName + "提交的【工单】,单号 是" + entity.getBpmBillCode() + ", 请审批";
        bpmform.setTitle(title);
        bpmform.setFormUrl("/iuap_pap_quickstart/pages/workorder/workorder.js");	// 单据url
        bpmform.setProcessInstanceName(title);										// 流程实例名称
        bpmform.setServiceClass("/iuap_pap_quickstart/sany_order");// 流程审批后，执行的业务处理类(controller对应URI前缀)

        return bpmform;
    }

}