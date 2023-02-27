package com.example.demo.advice;


import com.example.demo.exception.NotCorrectlyCellException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @Value("${spring.http.multipart.max-file-size}")
    String maxSizeInMB;

    @Value("${cell.info.message}")
    private String infoMessage;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError2(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", "Maximum upload size of " + maxSizeInMB + " exceeded");

        return "redirect:/api/v1/questions";
    }

    @ExceptionHandler(value = NotCorrectlyCellException.class)
    public String exception(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", " " + infoMessage + " ");
        return "redirect:/api/v1/questions";
    }
}
