package com.datvm.hairbookingapp.config;

import com.datvm.hairbookingapp.entity.Account;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.service.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver handlerExceptionResolver;

    private final List<String> AUTH_PERMISSION = List.of( //những api mà ai cũng truy cập đc
            "/api/v1/login",
            "/api/v1/register",
            "/api/v1/forgot-password",
            "/api/v1/*"
    );

    public boolean checkIsPublicAPI(String uri){
        //uri: /api/v1/...
        //Nếu gặp những API như list trên -> cho phép truy cập luôn
        //ngược lại , phân quyền ( authorization) , check token
        AntPathMatcher matcher = new AntPathMatcher(); //check pattern vs uri nguoi dung truy cap
        return AUTH_PERMISSION.stream().anyMatch(pattern -> matcher.match(pattern, uri));
        // nếu match -> true ; else -> false
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);  // Extract token after "Bearer "
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //check xem api user that user request allow who can access ( có phải là 1 public api hay ko , ai cũng dùng đc )
        boolean isPublicApi = checkIsPublicAPI(request.getRequestURI());
        if(isPublicApi)
            filterChain.doFilter(request, response);
        else {
            //neu ko phai public api -> kiem tra token
            String token = getTokenFromRequest(request);
            if(token == null) {
                handlerExceptionResolver.resolveException(request, response, null, new AppException(ErrorCode.TOKEN_MISSING));
                return; //ko cho phep truy cap
            }
            // => co' token
            // check xem token co' đúng hay ko -> lấy thông tin account từ token
            Account account;
            try{
                account = tokenProvider.getAccountByToken(token);
            }catch (ExpiredJwtException e){
                handlerExceptionResolver.resolveException(request, response,null, new AppException(ErrorCode.TOKEN_EXPIRED));

                //response token het han
                return;
            }catch (MalformedJwtException malformedJwtException){
                handlerExceptionResolver.resolveException(request, response, null, new AppException(ErrorCode.TOKEN_WRONG));

                //response token sai format
                return;
            }

            //=> token chuan. Cho phep truy cap
            //luu lai thong tin cua account
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(account, token, account.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //token ok, cho vao
            filterChain.doFilter(request, response);
        }
    }
}
