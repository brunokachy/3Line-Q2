package com.verifycard.entrypoint.config;

import com.verifycard.core.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String path = req.getRequestURI();

        if(!path.startsWith("/api")){
            filterchain.doFilter(request, response);
        }

        validateHeaders(req, response);

    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
    }

    private void validateHeaders(HttpServletRequest httpRequest, ServletResponse response) throws IOException {

        String appKey = httpRequest.getHeader("appKey");
        String timeStamp = httpRequest.getHeader("timeStamp");
        String authorization = httpRequest.getHeader("authorization:");

        if (StringUtils.isEmpty(appKey) && StringUtils.isEmpty(timeStamp) && StringUtils.isEmpty(authorization)) {
            HttpServletResponse resp = (HttpServletResponse) response;
            String error = "Invalid API KEY";

            resp.reset();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentLength(error .length());
            response.getWriter().write(error);
        }


    }

}
