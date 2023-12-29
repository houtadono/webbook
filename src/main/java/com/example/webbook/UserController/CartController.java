package com.example.webbook.UserController;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.webbook.DAO.DAO;
import com.example.webbook.entity.Book;
import com.example.webbook.entity.Cart;
import com.example.webbook.entity.Item;

@Controller
public class CartController {
	
	@GetMapping(value = "/addcart")
	public String addCart(Model model,HttpSession session,HttpServletRequest request) {
		session = request.getSession(true);
		DAO dao = new DAO();
		Cart cart = null;
		Object o = session.getAttribute("cart");
		if (o!=null){
            cart = (Cart)o;
        }
        else {
            cart = new Cart();
        }
		String bnum = request.getParameter("quantity");
		String id = request.getParameter("bookid");
		int num,bid =0;
		try {
			num = Integer.parseInt(bnum);
			bid = Integer.parseInt(id);
			Book book = dao.getBookById(bid);
			Item item = new Item(book, num,book.getPrice());
			cart.addItem(item);
		} catch (Exception e) {
			// TODO: handle exception
			num = 1;
		}
		ArrayList<Item> list = cart.getItems();
        int count=0;
        for (Item i : list) {
            count+=i.getQuantity();
        }
        int totalMoney = 0;
        for (Item i : list) {
            totalMoney+=i.getQuantity()*i.getPrice();
        }
        session.setAttribute("totalMoney", totalMoney);
        session.setAttribute("cart", cart);
        session.setAttribute("size", count);
		return "/user/cart";
	}
	
	@GetMapping(value = "/process")
	public String Process(Model model,HttpServletRequest request,HttpSession session) {
		session = request.getSession(true);
        Cart cart = null;
        Object o = session.getAttribute("cart");
        if (o != null) {
            cart = (Cart) o;
        } else {
            cart = new Cart();
        }
        String tnum = request.getParameter("num").trim();
        String tid = request.getParameter("id");
        int id, num;
        try {
            id = Integer.parseInt(tid);
            num = Integer.parseInt(tnum);
            if ((num == -1) && (cart.getQuantityById(id) <= 1)) {
                cart.removeItem(id);
            } else {
                DAO dao = new DAO();
                Book p = dao.getBookById(id);
                Item t = new Item(p, num,p.getPrice());
                cart.addItem(t);
            }
        } catch (Exception e) {

        }
        ArrayList<Item> list = cart.getItems();
        int count = 0;
        for (Item i : list) {
            count += i.getQuantity();
        }
        
        int totalMoney = 0;
        for (Item i : list) {
            totalMoney+=i.getQuantity()*i.getPrice();
        }
        
        session.setAttribute("totalMoney", totalMoney);
        session.setAttribute("cart", cart);
        session.setAttribute("size", count);
		return "/user/cart";
	}
	
	@GetMapping(value = "/delete")
	public String delete(Model model,HttpServletRequest request,HttpSession session) {
		session = request.getSession(true);
        Cart cart = null;
        Object o = session.getAttribute("cart");
        if (o != null) {
            cart = (Cart) o;
        } else {
            cart = new Cart();
        }
        int id = Integer.parseInt(request.getParameter("id"));
        cart.removeItem(id);
        ArrayList<Item> list = cart.getItems();
        session.setAttribute("cart", cart);
        ArrayList<Item> listItem = cart.getItems();
        int count = 0;
        for (Item i : list) {
            count += i.getQuantity();
        }
        session.setAttribute("size", count);
		return "/user/cart";
	}
	
	
}
