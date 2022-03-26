package com.flexbank.ws.configuration.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import com.flexbank.ws.exception.BadRequestException;
import com.flexbank.ws.exception.ErrorMessage;
import com.flexbank.ws.exception.ExceptionMessage;
import com.flexbank.ws.service.inter.CustomerPhoneNumberService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class ResetPasswordFilter extends OncePerRequestFilter {

    private final CustomerPhoneNumberService customerPhoneNumberService;

    public ResetPasswordFilter(CustomerPhoneNumberService customerPhoneNumberService) {
        this.customerPhoneNumberService = customerPhoneNumberService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {


        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberService.findByPhoneNumber(req.getHeader("phone"));

        if(!customerPhoneNumber.isPasswordResetAllowed()){
            ExceptionMessage errorResponse =
                    new ExceptionMessage(new Date(), ErrorMessage.NOT_ALLOWED.getErrorMessage());

            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.setContentType("application/json");
            res.getWriter().write(convertObjectToJson(errorResponse));
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        return !SecurityConstants.RESET_PASSWORD_URL.equals(path);
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
