package controllers;

import mapper.dtos.GradesDto;
import service.GradesService;
import service.Impl.GradesServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "gradeController", value = "/grades-form")
public class GradeController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        Connection conn = (Connection) request.getAttribute("conn");
        GradesService service = new GradesServiceImpl(conn);
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("h1>Students</h1>");
        out.println(service.gradesList());
        out.println("</body></html>");
    }

    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");

        Connection conn = (Connection) req.getAttribute("conn");
        GradesService service = new GradesServiceImpl(conn);
        String gradeStr = req.getParameter("grade");
        List<String> errores = getErrors(gradeStr);
        Map<String, String> errorsmap = getErrors2(gradeStr);

        if (errorsmap.isEmpty()) {
            Double grade = Double.parseDouble(gradeStr);
            service.update(GradesDto.builder()
                    .grade(grade)
                    .build());
            System.out.println(service.gradesList());

            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <title>Resultado form</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <h1>Resultado form!</h1>");

                out.println("        <ul>");
                out.println("            <li>Grade: " + grade + "</li>");
                out.println("        </ul>");
                out.println("    </body>");
                out.println("</html>");
            }
        } else {

            req.setAttribute("errors", errores);
            req.setAttribute("errorsmap", errorsmap);
            getServletContext().getRequestDispatcher("/GradesCrud.jsp").forward(req, resp);
        }
    }

    private Map<String,String> getErrors2(String grade) {
        Map<String,String> errors = new HashMap<>();
        if(grade==null ||grade.isBlank()){
            errors.put("grade","La nota es requerida");
        }
        return errors;
    }
    private List<String> getErrors(String grade)
    {
        List<String> errors = new ArrayList<String>();
        if(grade==null || grade.isBlank()){
            errors.add("La nota es requerida");
        }
        return errors;
    }
}