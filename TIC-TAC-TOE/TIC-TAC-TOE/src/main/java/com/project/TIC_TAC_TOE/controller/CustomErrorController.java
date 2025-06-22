package com.project.TIC_TAC_TOE.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;  // Updated import
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController { // Implementing the interface

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Get the error status code
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // Handle specific error codes
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "forward:/index.html"; // Forward 404 to frontend
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500"; // Custom error page for 500
            }
        }

        // Default case
        return "forward:/index.html";
    }

}
