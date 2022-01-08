package com.flexbank.ws.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured("ROLE_ADMIN")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
}
