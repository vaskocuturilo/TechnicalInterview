package com.example.demo.advice;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @Value("${spring.http.multipart.max-file-size}")
    String maxSizeInMB;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError2(MaxUploadSizeExceededException ex, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", "Maximum upload size of " + maxSizeInMB + " exceeded");

        return "redirect:/api/v1/questions";

    }
}
