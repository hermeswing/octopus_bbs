package octopus.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import octopus.base.WebConst;
import octopus.base.utils.FunctionNameAware;

/** 함수명 출력 */
@Slf4j
public class LoggingFunctionNameInterceptor extends BaseHandlerInterceptor {
    
    private static final String MDC_FUNCTION_NAME = WebConst.MDC_FUNCTION_NAME;
    
    // Controller 진입 전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler)
            throws Exception {
        
        val fna = getBean(handler, FunctionNameAware.class);
        
        log.debug("fna :: {}", fna);
        
        if (fna != null) {
            val functionName = fna.getFunctionName();
            
            log.debug("functionName :: {}", functionName);
            
            MDC.put(MDC_FUNCTION_NAME, functionName);
        }
        
        return true;
    }
}