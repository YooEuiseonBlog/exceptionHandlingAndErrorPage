package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ErrorPageController {

    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500Api(
            HttpServletRequest request, HttpServletResponse response) {

        log.info("API errorPage 500");

        Map<String, Object> result = new HashMap<>();
        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);
        result.put("status", request.getAttribute(ERROR_STATUS_CODE));
        result.put("message", ex.getMessage());

        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

//        Enumeration<String> attributeNames = request.getAttributeNames();
//        attributeNames.asIterator().forEachRemaining(System.out::println);

/*
        javax.servlet.forward.request_uri
        javax.servlet.forward.context_path
        javax.servlet.forward.servlet_path
        javax.servlet.forward.mapping
        org.springframework.web.servlet.View.pathVariables
        org.springframework.web.context.request.async.WebAsyncManager.WEB_ASYNC_MANAGER
        org.springframework.web.servlet.HandlerMapping.bestMatchingHandler
        javax.servlet.error.status_code
        org.springframework.web.servlet.DispatcherServlet.CONTEXT
        org.springframework.web.servlet.resource.ResourceUrlProvider
        org.springframework.web.util.ServletRequestPathUtils.PATH
        org.springframework.web.servlet.DispatcherServlet.LOCALE_RESOLVER
        org.springframework.web.servlet.HandlerMapping.bestMatchingPattern
        requestContextFilter.FILTERED
        org.springframework.web.servlet.DispatcherServlet.OUTPUT_FLASH_MAP
        javax.servlet.error.exception_type
        logId
        org.springframework.web.servlet.DispatcherServlet.FLASH_MAP_MANAGER
        org.springframework.core.convert.ConversionService
        org.springframework.web.servlet.HandlerMapping.matrixVariables
        org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR
        javax.servlet.error.message
        javax.servlet.error.servlet_name
        org.springframework.web.servlet.DispatcherServlet.THEME_SOURCE
        org.springframework.web.servlet.HandlerMapping.producibleMediaTypes
        org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping
        org.springframework.web.servlet.HandlerMapping.uriTemplateVariables
        javax.servlet.error.request_uri
        org.springframework.web.servlet.DispatcherServlet.THEME_RESOLVER
        javax.servlet.error.exception
*/
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }


    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
//        log.info("ERROR_EXCEPTION: ex= {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());
    }
}
