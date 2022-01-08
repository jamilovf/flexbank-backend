package com.flexbank.ws.configuration.security;

import com.flexbank.ws.repository.CustomerRepository;
import com.flexbank.ws.service.inter.AuthService;
import com.flexbank.ws.service.inter.CustomerPhoneNumberService;
import com.flexbank.ws.service.inter.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerPhoneNumberService customerPhoneNumberService;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public WebSecurity(AuthService authService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       CustomerPhoneNumberService customerPhoneNumberService,
                       CustomerRepository customerRepository,
                       RoleRepository roleRepository) {
        this.authService = authService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customerPhoneNumberService = customerPhoneNumberService;
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(SecurityConstants.VERIFY_PHONE_NUMBER_URL)
                .permitAll()
                .antMatchers(SecurityConstants.VERIFY_SMS_CODE_URL)
                .permitAll().and()
                .addFilterBefore(new SmsCodeFilter(customerPhoneNumberService),
                        AuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(SecurityConstants.SIGNUP_URL)
                .permitAll().and()
                .addFilterBefore(new SignupFilter(customerPhoneNumberService),
                        AuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager(), customerRepository, roleRepository))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(bCryptPasswordEncoder);
    }

    protected AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), authService);
        filter.setFilterProcessesUrl(SecurityConstants.SIGNIN_URL);
        return filter;
    }
}
