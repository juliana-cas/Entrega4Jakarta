package controllers;

import service.TeacherService;
import service.Impl.TeacherServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/teacherdelete")
public class TeacherDeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        TeacherService service = new TeacherServiceImpl(conn);
        String idString = req.getParameter("idd");
        try {
            Long id = Long.parseLong(idString);
            service.delete(id);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Eliminar por ID</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Docente encontrado!</h1>");
                out.println(" <h3>El docente con id " + id + " fue eliminado :  " + service.teacherList() + "</h3>");
                out.println(" </body>");
                out.println("</html>");
            }
        } catch(Exception e){
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No existe un docente con este id");

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
    }
}
