package com.example.Traceability.app.exception;


import com.example.Traceability.app.config.ErrorObject;
import com.example.Traceability.app.controller.MyCostumeRequestMissingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MyCostumeRequestMissingException.class)
    public ModelAndView handleError(MyCostumeRequestMissingException e){
        ErrorObject errorObject = ErrorObject.builder().message(e.getMessage()).text("Moze nam se na kvadrat!").build();

        ModelAndView modelAndView = new ModelAndView("myError");
        modelAndView.setStatus(HttpStatus.BAD_GATEWAY);
        modelAndView.addObject("errorObject",
                ErrorObject.builder().message(e.getMessage())
                        .text("Some data is missing try again!").build());
        return modelAndView;
    }
}
