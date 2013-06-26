package org.cherchgk.utils;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class ActionContextHelper {

    private static HttpServletRequest getRequest() {
        return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    }

    public static String getRequestParameterValue(String paramName) {
        return getRequest().getParameter(paramName);
    }

    public static Map<String, Object> getRequestParameters() {
        return getRequest().getParameterMap();
    }
}
