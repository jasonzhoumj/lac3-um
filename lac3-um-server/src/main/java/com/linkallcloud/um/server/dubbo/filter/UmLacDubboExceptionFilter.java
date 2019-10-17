package com.linkallcloud.um.server.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.linkallcloud.exception.LacDubboExceptionFilter;

@Activate(group = Constants.PROVIDER, order = -999)
public class UmLacDubboExceptionFilter extends LacDubboExceptionFilter {

}
