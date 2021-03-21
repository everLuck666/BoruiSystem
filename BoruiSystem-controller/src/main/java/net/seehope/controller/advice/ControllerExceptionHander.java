package net.seehope.controller.advice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.seehope.common.RestfulJson;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@ControllerAdvice
public class ControllerExceptionHander {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestfulJson defaultHander(HttpServletRequest request, HttpServletResponse response, Exception e){
        return RestfulJson.errorMsg(e.getLocalizedMessage());
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public  RestfulJson  handleMethodArgumentNotValidException(HttpServletRequest req, HttpServletResponse resp,
                                                               MethodArgumentNotValidException e) throws JsonProcessingException {

        Map<String, String> errorMsg = new HashMap<String, String>();

        List<FieldError> fieldsList = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldsList) {
            errorMsg.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        /*
         * e.getBindingResult().getFieldErrors().forEach(fieldError -> {
         * System.out.println(fieldError.getField() + ":" +
         * fieldError.getDefaultMessage()); });
         */

        return RestfulJson.errorMsg(new ObjectMapper().writeValueAsString(errorMsg));//把对象换成了json的形式
    }

//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public RestfulJson handerUpdateException(HttpServletRequest request, HttpServletResponse response, Exception e){
//        return RestfulJson.errorMsg(e.getLocalizedMessage());
//    }

}