package com.it2go.micro.carfleetservice.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * created by mmbarek on 19.01.2021.
 */
@Profile("cloud")
@EnableDiscoveryClient
@Configuration
public class EurekaDiscoveryConfig {

}
