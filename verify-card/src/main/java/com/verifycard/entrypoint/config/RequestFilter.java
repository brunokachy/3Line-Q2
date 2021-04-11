package com.verifycard.entrypoint.config;

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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

        if (!path.startsWith("/api")) {
            filterchain.doFilter(request, response);
        }

        if(validateHeaders(req)){
            if (validateHash(req)){
                filterchain.doFilter(request, response);
            }else {
                HttpServletResponse resp = (HttpServletResponse) response;
                String error = "Unauthorized Access!";

                resp.reset();
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentLength(error.length());
                response.getWriter().write(error);
            }
        }else {
            HttpServletResponse resp = (HttpServletResponse) response;
            String error = "Missing required header field";

            resp.reset();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentLength(error.length());
            response.getWriter().write(error);
        }
    }

    @Override
    public void init(FilterConfig filterconfig) {
    }

    private boolean validateHeaders(HttpServletRequest httpRequest) {

        String appKey = httpRequest.getHeader("appKey");
        String timeStamp = httpRequest.getHeader("timeStamp");
        String authorization = httpRequest.getHeader("authorization");

        return !StringUtils.isEmpty(appKey)
                && !StringUtils.isEmpty(timeStamp)
                && !StringUtils.isEmpty(authorization)
                && authorization.contains("3line");
    }

    private boolean validateHash(HttpServletRequest httpRequest) {
        String appKey = httpRequest.getHeader("appKey");
        String timeStamp = httpRequest.getHeader("timeStamp");
        String authorization = httpRequest.getHeader("authorization");

        String authHash = authorization.replace("3line", "").trim();

        String hash = encryptString(appKey+timeStamp);

        return authHash.equalsIgnoreCase(hash);
    }

    private String encryptString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            StringBuilder hashText = new StringBuilder(no.toString(16));

            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }

            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            return StringUtils.EMPTY;
        }
    }

}
