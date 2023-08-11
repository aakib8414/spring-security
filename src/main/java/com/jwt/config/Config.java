package com.jwt.config;

import com.jwt.filter.JwtRequestFilter;
import com.jwt.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//when used preAutorized anotation for method
public class Config extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userDetailService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private JwtAuthEntryPoint entryPoint;
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http//1
//               .csrf().disable()//non browser like postman can't hit if enabled
//               //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//             //  .and()
//               .authorizeRequests()
//               .antMatchers("/signin").permitAll()
//               .antMatchers("/public/**").hasRole("USER")//.permitAll()//home page should be permitted
//               .antMatchers("/users/**").hasRole("ADMIN")
//               .anyRequest()
//               .authenticated()
//               .and()
//               .httpBasic();//popup form and don't have logout
               //.formLogin()//form base login and logout
              // .loginPage("/signin")//custom login page
               //.loginProcessingUrl("/dologin")//processing url
              // .defaultSuccessUrl("/users/");
//2nd way
        .csrf().disable()
               .authorizeRequests()
               .antMatchers("/token","/signin","/refresh").permitAll()
               .antMatchers("/public/**").hasRole("USER")//.permitAll()//home page should be permitted
               .antMatchers("/users/**").hasRole("ADMIN")
               . anyRequest().authenticated().and()
               .exceptionHandling().authenticationEntryPoint(entryPoint)
               .and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth  //1
//                .inMemoryAuthentication()
//                .withUser("aakib")
//                .password(this.passwordEncoder().encode("aakib")).roles("USER")
//                .and()
//                .withUser("admin").password(this.passwordEncoder().encode("admin")).roles("ADMIN");
    auth//2
            .userDetailsService(this.userDetailService).passwordEncoder(passwordEncoder());
    }
    @Bean()
    public BCryptPasswordEncoder  passwordEncoder(){
        return
//                NoOpPasswordEncoder.getInstance();//tels we are using plain text as a passowrd
               new BCryptPasswordEncoder(15);
    }

    @Bean
    public AuthenticationManager auhAuthenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
