package controllers.iine;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Iine;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class IinesCreateServlet
 */
@WebServlet("/iines/create")
public class IinesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IinesCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();
        
/*
        if(){
*/

        //iineのインスタンスを生成
        Iine i = new Iine();

        // iの各フィールドにデータを代入
        i.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

        // showで登録したセッションスコープからレポートのIDを取得して
        // 該当のIDをレポートidに登録
        i.setReport(em.find(Report.class, (Integer)(request.getSession().getAttribute("report_id"))));;

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        i.setCreated_at(currentTime);
        i.setUpdated_at(currentTime);

        // showで登録したセッションスコープからメッセージのIDを取得して
        // 該当のIDのレポート1件のみをデータベースから取得
          Report r = em.find(Report.class, (Integer)(request.getSession().getAttribute("report_id")));

        //DB内のいいねを変数に指定
          int iine =r.getIine();

          //いいねを1足して上書き
          r.setIine(++iine);


        em.getTransaction().begin();
        em.persist(i);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "いいねが完了しました。");
        
/*
          }else{
            em.getTransaction().begin();
            em.remove(i);       // データ削除
            em.getTransaction().commit();
            em.close();
        }
*/

     // セッションスコープ上の不要になったデータを削除
        request.getSession().removeAttribute("report_id");



        // indexページへリダイレクト
        response.sendRedirect(request.getContextPath() + "/reports/index");

    }

}
