package com.flexbank.ws.configuration.security;

import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.entity.Role;
import com.flexbank.ws.repository.CustomerRepository;
import com.flexbank.ws.service.inter.RoleRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    public AuthorizationFilter(AuthenticationManager authManager,
                               CustomerRepository customerRepository,
                               RoleRepository roleRepository) {
        super(authManager);
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req,res);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,
                                                                  HttpServletResponse res) throws IOException {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null) {

            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

            String user = null;
            try {
                user = Jwts.parser()
                    .setSigningKey( SecurityConstants.getTokenSecret())
                    .parseClaimsJws( token )
                    .getBody()
                    .getSubject();}
            catch (ExpiredJwtException ex){
                res.setHeader("token", "expired");
            }

            if (user != null) {

                Customer customer = customerRepository.findById(Integer.parseInt(user)).get();
                List<GrantedAuthority> authorities = new ArrayList<>();
                Role role = roleRepository.findById(customer.getRoleId()).get();
                authorities.add(new SimpleGrantedAuthority(role.getName().name()));

                return new UsernamePasswordAuthenticationToken(user,
                        null, authorities);
            }

            return null;
        }

        return null;
    }
}
