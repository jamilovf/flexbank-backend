package com.flexbank.ws.configuration.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import com.flexbank.ws.exception.BadRequestException;
import com.flexbank.ws.exception.ErrorMessage;
import com.flexbank.ws.exception.ExceptionMessage;
import com.flexbank.ws.service.inter.CustomerPhoneNumberService;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Data
public class SmsCodeFilter extends OncePerRequestFilter {

    private final CustomerPhoneNumberService customerPhoneNumberService;

    public SmsCodeFilter(CustomerPhoneNumberService customerPhoneNumberService) {
        this.customerPhoneNumberService = customerPhoneNumberService;
    }


    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberService.findByPhoneNumber(req.getHeader("phone"));

        if(customerPhoneNumber.isRegistered()){
            throw new BadRequestException(ErrorMessage.CUSTOMER_ALREADY_REGISTERED.getErrorMessage());
        }

        if(!customerPhoneNumber.isMessageCodeAllowed()){
                ExceptionMessage errorResponse =
                        new ExceptionMessage(new Date(), ErrorMessage.NOT_ALLOWED.getErrorMessage());

                res.setStatus(HttpStatus.FORBIDDEN.value());
                res.setContentType("application/json");
                res.getWriter().write(convertObjectToJson(errorResponse));
                return;
        }

        chain.doFilter(req,res);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        return !SecurityConstants.VERIFY_SMS_CODE_URL.equals(path);
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
