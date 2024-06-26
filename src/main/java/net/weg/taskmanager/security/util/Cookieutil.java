package net.weg.taskmanager.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;

public class Cookieutil {

    //centraliza as questoes relacionadas aos cookies

    public Cookie createCookie(UserDetails userDetails) {
        String token = new JwtUtil().createToken(userDetails);
        Cookie cookie = new Cookie("EV", token);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        return cookie;
    }

    public Cookie createCookieNull() {
//        String token = new JwtUtil().gerarToken("""");
        Cookie cookie = new Cookie("EV", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }


    public Cookie getCookie(HttpServletRequest request, String name) throws Exception {
        System.out.println("cookies");
        System.out.println(Arrays.toString(request.getCookies()));
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie;
        }
        throw new Exception("Não foi possivel encontrar cookie com o nome: " + name);
    }
}
