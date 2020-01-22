package servlet;

import com.google.gson.Gson;
import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(PageGenerator.getInstance().getPage("registerPage.html", null));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = "";

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User(email, password);

        if (!userService.addUser(user)) {
            resp.setContentType("text/html;charset=utf-8");
            json = gson.toJson("Пользователь уже зарегистрирован");
            resp.getWriter().println(json);
            resp.setStatus(HttpServletResponse.SC_FOUND);
            return;
        }

        json = gson.toJson(user);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(json);
        resp.setStatus(HttpServletResponse.SC_OK);

    }
}
