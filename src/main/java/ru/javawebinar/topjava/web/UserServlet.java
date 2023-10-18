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
            SecurityUtil.setAuthUser(user);
            response.sendRedirect("meals");
            return;
        }
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
}