package com.keminapera.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KeminaPera
 */
@RestController
public class ApiController {

    @Autowired
    private RouteLocator routeDefinitionRouteLocator;

    /*@GetMapping("ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }*/

}
