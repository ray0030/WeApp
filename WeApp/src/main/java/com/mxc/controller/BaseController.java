package com.mxc.controller;


import com.util.JsonUtils;
import com.util.ResponseUtil;
import com.util.StringUtil;
import com.util.SysException;
import com.util.SysException.ExceptionEnum;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * ���п������Ļ���
 *
 */
public class BaseController {
	
	protected Logger _logger = LoggerFactory.getLogger(this.getClass());
	
	/**
     * ���json���
     *
     * @param response
     * @param context
     * @throws Exception
     */
    protected void writeJson(HttpServletResponse response, String context)
            throws Exception {
        _logger.info("��Ӧjson���ģ�"+context);
        ResponseUtil.outJson(context, response);
    }
    
    /**
     * ����request��ת��JSON����
     * @param request
     * @return
     * @throws IOException
     */
    public static JSONObject getRequestAsJSONObject(HttpServletRequest request) throws IOException{
        String ret = request.getAttribute("data").toString();
        return StringUtil.isNotEmpty(ret) ? JSONObject.fromObject(ret) : null;
    }
    
    /**
     * ����request��ת�ɶ���
     * @param request
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T getRequestObject(HttpServletRequest request, Class<T> type) throws IOException{
        String ret = request.getAttribute("data").toString();
        return StringUtil.isNotEmpty(ret) ? JsonUtils.toObject(ret, type) : null;
    }
    

    /**
     * �ж�Object����
     */
    protected String checkObject(Object object) {
        if (object == null) {
            throw new SysException(SysException.ExceptionEnum._99999);
        }
        return "";
    }
    
    protected void generateExceptionStr(ExceptionEnum ee) {
        throw new SysException(ee);
    }
}
