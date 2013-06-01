package org.cherchgk.utils;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class ActionContextHelper {

    public static String getRequestParameterValue(String paramName) {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        return request.getParameter(paramName);
    }

    public static Map<String, Object> getRequestParameters() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        return request.getParameterMap();
    }
}
