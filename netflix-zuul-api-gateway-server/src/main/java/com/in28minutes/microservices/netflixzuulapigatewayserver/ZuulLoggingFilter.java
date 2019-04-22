package com.in28minutes.microservices.netflixzuulapigatewayserver;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ZuulLoggingFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger( ZuulLoggingFilter.class );

    @Override
    public String filterType() {
        // pre - before request be executed
        // post - after request be executed
        // error - only on error
        return "pre";
    }

    // as lesser as it is executes first
    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        LOGGER.info(
            "request -> {} request uri -> {}",
            request,
            request.getRequestURI()
        );

        return null;
    }

}
