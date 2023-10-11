package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MapMealDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private static final int CALORIES_PER_DAY = 2000;
    private MealDao dao;

    @Override
    public void init() throws ServletException {
        dao = new MapMealDao();
        MealsUtil.getMockMeals().forEach(meal -> dao.add(meal));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action.toLowerCase()) {
            case ("delete"):
                dao.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals?action=meals");
                return;
            case ("edit"):
                forward = INSERT_OR_EDIT;
                Meal meal = dao.getById(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("meal", meal);
                request.setAttribute("id", meal.getId());
                break;
            case ("insert"):
                forward = INSERT_OR_EDIT;
                break;
            default:
                forward = LIST_MEAL;
                request.setAttribute("mealsTo", MealsUtil.getMealsWithExcess(dao.getAll(), CALORIES_PER_DAY));
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DateUtil.PATTERN_INPUT);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            dao.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            dao.update(meal);
        }
        response.sendRedirect("meals");
    }
}