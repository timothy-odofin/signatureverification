package mcb.com.api.security;

import mcb.com.api.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Configuration class for Spring Security settings.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1) // Define order to make sure this configuration is applied before the default Spring Security configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui urls
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v2/api-docs/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/auth/**",
            "/login.do",
            "/h2-console/**"
            // other public endpoints of your API may be appended to this array
    };

    @Resource(name = "userDetailsService")
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    /**
     * Configures the authentication manager to use the custom JwtUserDetailsService and BCryptPasswordEncoder.
     *
     * @param auth AuthenticationManagerBuilder instance.
     * @throws Exception If an exception occurs during configuration.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    /**
     * Configures HTTP security settings, including URL patterns and authentication filters.
     *
     * @param http HttpSecurity instance.
     * @throws Exception If an exception occurs during configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers( "/account/event-sources/update/**", "/account/signature/validate/**","/account/users/**")
                .access("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SUPER')")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Configures Web security settings.
     *
     * @param web WebSecurity instance.
     * @throws Exception If an exception occurs during configuration.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Ignore security for certain paths (e.g., Swagger endpoints)
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    /**
     * Creates a BCryptPasswordEncoder bean.
     *
     * @return BCryptPasswordEncoder bean instance.
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates an AuthenticationManager bean.
     *
     * @return AuthenticationManager bean instance.
     * @throws Exception If an exception occurs while creating the bean.
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Creates a JwtAuthenticationFilter bean.
     *
     * @return JwtAuthenticationFilter bean instance.
     * @throws Exception If an exception occurs while creating the bean.
     */
    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }
}
