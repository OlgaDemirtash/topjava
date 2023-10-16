package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.setAttribute("user", SecurityUtil.authUserId());
        String userParameter = request.getParameter("user");
        if (userParameter != null) {
            int user = Integer.parseInt(userParameter);
            if (user == 1) {
                SecurityUtil.setAuthUser(1);
            } else if (user == 2) {
                SecurityUtil.setAuthUser(2);
            }
            response.sendRedirect("/topjava");
            return;
        }
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

    }


}
