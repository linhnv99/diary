package com.linhnv.diary.models.bos;

import com.linhnv.diary.utils.Utils;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class API {
    private Pattern pathPattern;
    private HttpMethod[] methods;
    private String path;

    private API(Pattern pathPattern, HttpMethod[] methods) {
        this.pathPattern = pathPattern;
        this.methods = methods;
    }

    private API(Pattern pathPattern, String path, HttpMethod[] methods) {
        this.pathPattern = pathPattern;
        this.methods = methods;
        this.path = path;
    }

    /**
     * @param pathRegex
     * @param methods
     * @return
     */
    public static API with(String pathRegex, HttpMethod... methods) {
        return new API(Pattern.compile(pathRegex), methods);
    }

    /**
     * @param pathRegex
     * @param methods
     * @return
     */
    public static API with(String pathRegex, String path, HttpMethod... methods) {
        return new API(Pattern.compile(pathRegex), path, methods);
    }

    /**
     * @param request
     * @return
     */
    public boolean isValidRequest(HttpServletRequest request) {
        return isProcessableEndpoint(request.getRequestURI()) && isMethod(request.getMethod());
    }

    private boolean isProcessableEndpoint(String path) {
        return this.pathPattern.matcher(path).matches();
    }

    public boolean isFindRequest(HttpServletRequest request) {
        return isFindPath(request.getRequestURI()) && isMethod(request.getMethod());
    }

    private boolean isFindPath(String path) {
        return path.contains(this.pathPattern.toString());
    }

    private boolean isMethod(String method) {
        if (this.methods == null || this.methods.length == 0) {
            return true;
        }
        for (HttpMethod httpmethod : this.methods) {
            if (httpmethod.matches(method)) {
                return true;
            }
        }
        return false;
    }

    public HttpMethod getMethod() {
        return Utils.isNullOrEmpty(methods) ? null : methods[0];
    }

    public String getPath() {
        return path;
    }
}