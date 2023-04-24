package octopus.base.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import octopus.base.interceptor.LoggerInterceptor;
import octopus.base.resolver.LoginUserArgumentResolver;

@RequiredArgsConstructor
@Configuration // @Configuration와 @EnableWebMvc를 같이 쓰면 안된다
public class WebConfig implements WebMvcConfigurer {
    
    private final LoginUserArgumentResolver loginUserArgumentResolver;
    
    /**
     * 사용자 정보를 Parameter로 받을 수 있도록 Argument를 추가
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new LoggerInterceptor())
                .excludePathPatterns("/css/**", "/images/**", "/js/**");
    }
    
}