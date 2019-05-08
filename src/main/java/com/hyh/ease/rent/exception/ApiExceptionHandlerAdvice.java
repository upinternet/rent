package com.hyh.ease.rent.exception;

import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(annotations = RestController.class)
class ApiExceptionHandlerAdvice {

    /**
     * Handle exceptions thrown by handlers.
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> exception(Exception exception, HttpServletResponse response) {
        ErrorDTO errorDTO = new ErrorDTO();
        if(exception instanceof ApiException){//api异常
            ApiException apiException = (ApiException)exception;
            errorDTO.setErrorCode(apiException.getErrorCode());
        }else{//未知异常
            errorDTO.setErrorCode(0L);
        }
        errorDTO.setTip(exception.getMessage());
        ResponseEntity<ErrorDTO> responseEntity = new ResponseEntity<>(errorDTO, HttpStatus.valueOf(response.getStatus()));
        return responseEntity;
    }

    class ErrorDTO{
        private Long errorCode;
        private String tip;

        public Long getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Long errorCode) {
            this.errorCode = errorCode;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }
    }
}