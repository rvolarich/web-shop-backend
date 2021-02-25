//package com.volare_automation.springwebshop.authentication;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import java.util.Arrays;
//
//@Configuration
//public class CorsConfig {
//
//   // @Bean
//    public CorsConfigurationSource corsConfigurationSource(){
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:3000"));
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE"));
//        corsConfiguration.setAllowedHeaders(Arrays.asList("content-type"));
//        corsConfiguration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**", corsConfiguration);
//        return configSource;
//    }
//}
