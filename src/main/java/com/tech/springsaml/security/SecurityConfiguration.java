package com.tech.springsaml.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.extensions.saml2.config.SAMLConfigurer.saml;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${security.saml2.metadata-url}")
    String metadataUrl;

    @Value("${server.ssl.key-alias}")
    String keyAlias;

    @Value("${server.ssl.key-store-password}")
    String password;

    @Value("${server.port}")
    String port;

    @Value("${server.ssl.key-store}")
    String keyStoreFilePath;

    ///////////////////////////////


//    @Value("${onelogin.sp.protocol}")
//    private String spProtocol;
//
//    @Value("${onelogin.sp.host}")
//    private String spHost;
//
//    @Value("${onelogin.sp.path}")
//    private String spBashPath;

    @Autowired
    private SAMLUserService samlUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/saml**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .apply(saml())
//                .serviceProvider()
//                .keyStore()
//                .storeFilePath(this.keyStoreFilePath)
//                .password(this.password)
//                .keyname(this.keyAlias)
//                .keyPassword(this.password)
//                .and()
//                .protocol("https")
//                .hostname(String.format("%s:%s", "localhost", this.port))
//                .basePath("/")
//                .and()
//                .identityProvider()
//                .metadataFilePath(this.metadataUrl);

        http
                .csrf().and()
                .authorizeRequests()
                .antMatchers("/saml/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(saml())
                .userDetailsService(samlUserService)
                .serviceProvider()
                .protocol("https")
                .hostname("localhost:8081")
                .basePath("/")
                .keyStore()
                .storeFilePath(keyStoreFilePath)
                .keyPassword(password)
                .keyname(keyAlias)
                .and()
                .and()
                .identityProvider()
                .metadataFilePath(metadataUrl)
                .and()
                .and();

    }
}
