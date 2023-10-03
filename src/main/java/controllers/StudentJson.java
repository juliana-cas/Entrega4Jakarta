package controllers;

import domain.models.Student;
import mapper.dtos.StudentDto;
import repository.Impl.StudentRepositoryImpl;
import repository.Impl.StudentRepositoryLogicImpl;
import service.StudentService;
import service.Impl.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet(value="/student.json")
public class StudentJson extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        ServletInputStream jsonStream = req.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        StudentDto student = mapper.readValue(jsonStream, StudentDto.class);
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println(" <head>");
            out.println(" <meta charset=\"UTF-8\">");
            out.println(" <title>Detalle de estudiante desde json</title>");
            out.println(" </head>");
            out.println(" <body>");
            out.println(" <h1>Detalle de estudiante desde json!</h1>");
            out.println("<ul>");
            out.println("<li>Id: "+ student.studentId() + "</li>");
            out.println("<li>Nombre: " + student.studentName() + "</li>");
            out.println("<li>Email: " + student.studentEmail() + "</li>");
            out.println("<li>>Career: " + student.career() + "</li>");
            out.println("<li>Semester: " + student.semester() + "</li>");
            out.println("</ul>");
            out.println(" </body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        StudentService service = new StudentServiceImpl(conn);
        List<StudentDto> students = service.studentList();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(students);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}