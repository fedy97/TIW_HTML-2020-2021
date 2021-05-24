
package it.polimi.tiw.controller;

import it.polimi.tiw.bean.OrderBean;
import it.polimi.tiw.bean.User;
import it.polimi.tiw.dao.OrderDAO;
import it.polimi.tiw.utils.GenericServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/orders")
public class OrderController extends GenericServlet {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class.getSimpleName());

    private static final String RESULTS_PAGE_PATH = "/orders.html";

    public OrderController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // get and check params
        String orderId = null;

        Optional<User> user = getUserData(req);
        if (!user.isPresent()) {
            resp.sendRedirect(getServletContext().getContextPath() + LOGIN_PAGE_PATH);
            return;
        }

        String userId = user.get().getId();


        try {
            orderId = req.getParameter("order-id");
        } catch (NumberFormatException | NullPointerException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect param values");
            return;
        }

        try {
            List<OrderBean> foundOrder = getOrder(orderId);
            // check if the order exists
            if (foundOrder == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                return;
            }
            // check if the order belongs to the logged user
            if (!foundOrder.get(0).getUser_id().equals(userId)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You cannot view this order");
                return;
            }

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("foundOrder", foundOrder.get(0));
            templateEngine.process(RESULTS_PAGE_PATH, ctx, resp.getWriter());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }

    }

    private List<OrderBean> getOrder(String id) throws SQLException {

        OrderDAO orderDAO = new OrderDAO(connection);
        return orderDAO.findOrderById(id);
    }
}
