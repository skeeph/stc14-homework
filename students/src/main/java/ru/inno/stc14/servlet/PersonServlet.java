package ru.inno.stc14.servlet;

import khabib.lec12_dao.dao.PersonDAO;
import khabib.lec12_dao.dao.PersonDAOImpl;
import khabib.lec12_dao.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PersonServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServlet.class);
    private PersonDAO dao;
    private Connection conn;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String login = "inno";
        String pass = "polis";
        try {
            conn = DriverManager.getConnection(url, login, pass);
            dao = new PersonDAOImpl(conn);
            LOGGER.info("Подключение к БД выполнено");
        } catch (SQLException e) {
            LOGGER.error("Ошибка в БД", e);
        }
    }

    @Override
    public void destroy() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.getAttribute("name");
        System.out.println("get " + Thread.currentThread().getId());
        Writer writer = resp.getWriter();
        try {
            for (Person person : this.dao.getAllPersons()) {
                writer.write(person.toString() + "\n");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post " + Thread.currentThread().getId());
        //resp.getWriter().write("Hello, " + req.getParameter("name"));

        // TODO: 12.02.2019 логика сохранения человека
        HttpSession session = req.getSession();
        session.setAttribute("name", req.getParameter("name"));

        getServletContext()
                .getRequestDispatcher("/index.jsp")
                .forward(req, resp);
    }
}