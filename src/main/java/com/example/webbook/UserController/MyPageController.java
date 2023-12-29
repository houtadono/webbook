package com.example.webbook.UserController;

import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.webbook.DAO.DAO;
import com.example.webbook.entity.Account;
import com.example.webbook.entity.Book;
import com.example.webbook.entity.Cart;
import com.example.webbook.entity.Item;
import com.example.webbook.entity.Order;
import com.example.webbook.entity.OrderLine;

@Controller
public class MyPageController {
	@GetMapping(value = "/mypage")
	public String myPgae(HttpSession session,HttpServletRequest request,Model model) {	
		session = request.getSession(true);
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		DAO dao = new DAO();
		Cart cart = null;
		Object o = session.getAttribute("cart");
		if (o!=null){
            cart = (Cart)o;
        }
        else {
            cart = new Cart();
        }
		
		Account ac = (Account) session.getAttribute("account");
		int uid = ac.getId();
		
		ArrayList<Item> list = cart.getItems();
		if (list.size()!=0) {
			int count = 0;int totalMoney = 0;
	        for (Item i : list) {
	            count += i.getQuantity();
	            totalMoney+=i.getQuantity()*i.getPrice();
	        }
			LocalDate localDate = LocalDate.now();
			String day = String.valueOf(localDate);
		    int status = 0;
			Order order = new Order(0,uid, count, day,totalMoney, status);
			order.setName(name);
			order.setPhone(phone);
			order.setAddress(address);
			dao.insertOrder(order);
	        
			int oid = dao.getLastOrderId();
			for (Item i : list) {
				int bid = i.getBook().getId();					
				int quantity = i.getQuantity();
				OrderLine OrderLine = new OrderLine(oid, bid, quantity,i.getPrice()*i.getQuantity());
				dao.insertOrderLine(OrderLine);
				dao.updateBookSold(bid, quantity);
			}
			session.removeAttribute("cart");
			session.removeAttribute("size");
		}
		
		ArrayList<Order> listOder = new ArrayList<>();
		listOder = dao.getAllOrderStatus0ByUid(uid);
		model.addAttribute("listO", listOder);
		model.addAttribute("active", 1);
		
		return "/user/mypage";
	}
	
	@GetMapping(value = "/delete/Order")
	public String deleteOrder(HttpServletRequest request,Model model,HttpSession session) {
		session = request.getSession(true);
		DAO dao = new DAO();
		
		String oid = request.getParameter("oid");
		try {
			int o_id = Integer.parseInt(oid);
			dao.changeStatusOrderByID(o_id);		
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/mypage";
	}
	
	@GetMapping(value = "/Ordered")
	public String listOrdered(HttpSession session,Model model) {
		DAO dao = new DAO();
		Account ac = (Account) session.getAttribute("account");
		int uid = ac.getId();
		ArrayList<Order> listO = new ArrayList<>();
		listO = dao.getAllOrderStatus1ByUid(uid);
		model.addAttribute("listO", listO);
		model.addAttribute("active", 2);
		
		return "/user/mypage";
	}
	
	@GetMapping(value = "/OrderDetail")
	public String OrderDetail(HttpServletRequest request,Model model,HttpSession session) {
		DAO dao = new DAO();
		String id = request.getParameter("oid");
		String stt = request.getParameter("check");
		int status = Integer.parseInt(stt);
		System.out.println(id+ " - " + stt);
		ArrayList<OrderLine> listO = new ArrayList<>();
		try {
			int oid = Integer.parseInt(id);		
			listO = dao.getAllOrderLineByOid(oid);	
			for(OrderLine o : listO){
				System.out.println("order line: " + o);
			}
			
		} catch (Exception e) {
			
			// TODO: handle exception
		}
		model.addAttribute("listO", listO);
		model.addAttribute("check", status);
		
		return "/user/orderline";
	}
	
	@GetMapping(value = "/deleteOrderLine")
	public String deleteOrderLine(HttpServletRequest request,Model model) {
		DAO dao = new DAO();
		String oid = request.getParameter("oid");
		String bid = request.getParameter("bid");
		String sl  = request.getParameter("quantity");  
		int o_id = 0,b_id = 0,quantity = 0;
		try {
			 o_id = Integer.parseInt(oid);
			 b_id = Integer.parseInt(bid);
			 quantity = Integer.parseInt(sl);
			dao.detleteOrderLineByOidAndBid(o_id,b_id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		ArrayList<OrderLine> listOrderLine = new ArrayList<>();
		listOrderLine = dao.getAllOrderLineByOid(o_id);
		if (listOrderLine.size()==0) {
			dao.detleteOrderHistory(o_id);
		}
		else{
			Book book = dao.getBookById(b_id);
			dao.updateOrder(quantity,quantity*book.getPrice(), o_id);
		}
		model.addAttribute("listO", listOrderLine);
		model.addAttribute("check", 0);
		
		return "/user/orderline";
	}
	
	@GetMapping(value = "/orderDeleted")
	public String listOrderedDeleted(HttpSession session,Model model) {
		DAO dao = new DAO();
		Account ac = (Account) session.getAttribute("account");
		int uid = ac.getId();
		ArrayList<Order> listO = new ArrayList<>();
		listO = dao.getAllOrderDeletedByUid(uid);
		model.addAttribute("listO", listO);
		model.addAttribute("active", 3);
		
		return "/user/mypage";
	}
	
	@GetMapping(value = "/restoreOrder")
	public String restoreOrderedDeleted(HttpServletRequest request,Model model,HttpSession session) {
		DAO dao = new DAO();
		String oid = request.getParameter("oid");
		try {
			int o_id = Integer.parseInt(oid);
			dao.restoreOrderByID(o_id);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Account ac = (Account) session.getAttribute("account");
		int uid = ac.getId();
		
		ArrayList<Order> listO = new ArrayList<>();
		listO = dao.getAllOrderStatus0ByUid(uid);
		model.addAttribute("listO", listO);
		model.addAttribute("active", 1);
		
		return "/user/mypage";
	}
	
	@GetMapping(value = "/deleteOrderDelete")
	public String deleteOrderedHistory(HttpServletRequest request,Model model,HttpSession session) {
		DAO dao = new DAO();
		String oid = request.getParameter("oid");
		try {
			int o_id = Integer.parseInt(oid);
			dao.detleteOrderLineByOid(o_id);
			dao.detleteOrderHistory(o_id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Account ac = (Account) session.getAttribute("account");
		int uid = ac.getId();
		
		ArrayList<Order> listO = new ArrayList<>();
		listO = dao.getAllOrderDeletedByUid(uid);
		model.addAttribute("listO", listO);
		model.addAttribute("active", 3);
		
		return "/user/mypage";
	}
}