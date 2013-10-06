package org.cherchgk.jsp.tags;

import org.cherchgk.utils.ApplicationSettings;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Обработчик тега isDemoMode.
 *
 * @author Andrey Grigorov
 */
public class IsDemoModeTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        if (ApplicationSettings.isDemoMode()) {
            return TagSupport.EVAL_BODY_INCLUDE;
        } else {
            return TagSupport.SKIP_BODY;
        }
    }
}
