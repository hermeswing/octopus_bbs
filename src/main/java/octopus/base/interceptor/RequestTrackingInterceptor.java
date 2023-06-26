package octopus.base.interceptor;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.util.AntPathMatcher;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import octopus.base.utils.XORShiftRandom;

/** 처리시간을 DEBUG 로그로 출력하다 */
@Slf4j
public class RequestTrackingInterceptor extends BaseHandlerInterceptor {
    
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    private static final ThreadLocal<Long> startTimeHolder = new ThreadLocal<>();
    
    private static final String HEADER_X_TRACK_ID = "X-Track-Id";
    
    // 난수생성기
    private final XORShiftRandom random = new XORShiftRandom();
    
    private final String[] excludeUrlPatterns;
    
    public RequestTrackingInterceptor(String[] excludeUrlPatterns) {
        this.excludeUrlPatterns = excludeUrlPatterns;
    }
    
    // 컨트롤러 진입전 전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler)
            throws Exception {
        
        // 현재시간 기록
        val beforeNanoSec = System.nanoTime();
        startTimeHolder.set(beforeNanoSec);
        
        // 트래킹 ID
        val trackId = getTrackId(request);
        MDC.put(HEADER_X_TRACK_ID, trackId);
        response.setHeader(HEADER_X_TRACK_ID, trackId);
        
        return true;
    }
    
    // 처리완료후
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex)
            throws Exception {
        
        val beforeNanoSec = startTimeHolder.get();
        if (beforeNanoSec == null) {
            return;
        }
        if (shouldNotFilter(request)) {
            return;
        }
        
        val elapsedNanoSec  = System.nanoTime() - beforeNanoSec;
        val elapsedMilliSec = NANOSECONDS.toMillis(elapsedNanoSec);
        log.debug("path={}, method={}, Elapsed {}ms.", request.getRequestURI(), request.getMethod(),
                elapsedMilliSec);
        
        // 파기하다
        startTimeHolder.remove();
    }
    
    /**
     * 트래킹 ID 생성
     *
     * @param request
     * @return
     */
    private String getTrackId(HttpServletRequest request) {
        String trackId = request.getHeader(HEADER_X_TRACK_ID);
        if (trackId == null) {
            int seed = Integer.MAX_VALUE;
            trackId = String.valueOf(random.nextInt(seed));
        }
        
        return trackId;
    }
    
    protected boolean shouldNotFilter(HttpServletRequest request) {
        for (val excludeUrlPattern : excludeUrlPatterns) {
            if (pathMatcher.match(excludeUrlPattern, request.getServletPath())) {
                return true;
            }
        }
        
        return false;
    }
}