package controllers.iine;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Iine;
import models.Report;
import utils.DBUtil;



/**
 * Servlet implementation class IinesIndexServlet
 */
@WebServlet("/iines/index")
public class IinesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IinesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        Report report_id = em.find(Report.class, Integer.parseInt(request.getParameter("id")));



        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e){
            page =1;
        }

        List<Iine>iines = em.createNamedQuery("getReportAllIines",Iine.class)
                            .setParameter("report", report_id)
                            .setFirstResult(15*(page-1))
                            .setMaxResults(15)
                            .getResultList();



        long iines_count =(long)em.createNamedQuery("getReportIinesCount",Long.class)
                           .setParameter("report",report_id)
                           .getSingleResult();

        em.close();

        request.setAttribute("iines", iines);
        request.setAttribute("iines_count",iines_count);
        request.setAttribute("page", page);
        if(request.getSession().getAttribute("flush")!=null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }


        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/iines/show.jsp");
        rd.forward(request, response);

    }

}
