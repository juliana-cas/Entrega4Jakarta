package controllers.Examples;

import mapper.dtos.StudentDto;
import repository.Impl.StudentRepositoryImpl;
import repository.Impl.StudentRepositoryLogicImpl;
import service.LoginService;
import service.StudentService;
import service.TeacherService;
import service.Impl.LoginServiceImpl;
import service.Impl.StudentServiceImpl;
import service.Impl.TeacherServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet({"/loginlist","/loginlist.html"})
public class ListStudentsServlet extends HttpServlet {

    private String message;

    public void init(){
        message = "Hello World!";
    }

    final static String USERNAME = "root";
    final static String PASSWORD = "";

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            resp.setContentType("text/html;charset=UTF-8");
            Cookie usernameCookie = new Cookie("username", username);
            resp.addCookie(usernameCookie);
            resp.sendRedirect(req.getContextPath() + "/loginlist.html");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Login correcto</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Login correcto!</h1>");
                out.println(" <h3>Hola "+ username + " has iniciado sesión con éxito!</h3>");
                out.println(" </body>");
                out.println("</html>");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no esta autorizado para ver la lista de estudiantes");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws
            ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        StudentService service = new StudentServiceImpl(conn);
        LoginService auth = new LoginServiceImpl();
        Optional<String> cookieOptional = auth.getUsername(req);
        List<StudentDto> students = service.studentList();
        if (cookieOptional.isPresent()) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println(" <head>");
                out.println(" <meta charset=\"UTF-8\">");
                out.println(" <title>Hola " + cookieOptional.get() + "</title>");
                out.println(" </head>");
                out.println(" <body>");
                out.println(" <h1>Hola " + cookieOptional.get() + " has iniciado sesión con éxito!</h1>");
                out.println(" <h1>Aqui tienes la lista de estudiantes</h1>");
                out.println(" <h3> " + students + " </h3>");
                out.println(" </body>");
                out.println("</html>");
            }
        } else {
            getServletContext().getRequestDispatcher("/loginlist.jsp").forward(req, response);
        }
    }
}
