package com.flexbank.ws.configuration.security;

import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.service.inter.CustomerPhoneNumberService;
import lombok.Data;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Data
public class SmsCodeFilter extends OncePerRequestFilter {

    private final CustomerPhoneNumberService customerPhoneNumberService;

    public SmsCodeFilter(CustomerPhoneNumberService customerPhoneNumberService) {
        this.customerPhoneNumberService = customerPhoneNumberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberService.findByPhoneNumber(req.getHeader("phone"));

        if(!customerPhoneNumberDto.isMessageCodeAllowed()){
            throw new RuntimeException("Not Allowed!");
        }

        chain.doFilter(req,res);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        return !SecurityConstants.VERIFY_SMS_CODE_URL.equals(path);
    }
}
