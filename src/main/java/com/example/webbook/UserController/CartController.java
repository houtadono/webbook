package com.example.webbook.UserController;

import java.util.ArrayList;
import java.util.LinkedList;

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
    @GetMapping(value = "/checkout")
	public String checkout(Model model,HttpSession session,HttpServletRequest request) {
		session = request.getSession(true);
		DAO dao = new DAO();
		Cart cart = (Cart)session.getAttribute("cart");
		if (cart != null){
            session.setAttribute("totalMoney", cart.getTotalMoney());
            session.setAttribute("cart", cart);
            session.setAttribute("size", cart.getSize());
        }
        
        // session.setAttribute("totalMoney", totalMoney);
        // session.setAttribute("cart", cart);
        // session.setAttribute("size", count);
		LinkedList<String> pageHistory = (LinkedList<String>) session.getAttribute("pageHistory");
		if (pageHistory == null) {
			pageHistory = new LinkedList<>();
		}
        if(!pageHistory.getFirst().equalsIgnoreCase("checkout"))
		    pageHistory.addFirst("checkout");
		int maxHistorySize = 10;
		while (pageHistory.size() > maxHistorySize) {
			pageHistory.removeLast();
		}
		session.setAttribute("pageHistory", pageHistory);
		return "/user/checkout";
	}

    @GetMapping(value = "/addcart")
	public String addCart(Model model,HttpSession session,HttpServletRequest request) {
		session = request.getSession(true);
		DAO dao = new DAO();
		Cart cart = (Cart)session.getAttribute("cart");

		String bnum = request.getParameter("quantity");
		String id = request.getParameter("bookid");

		int num,bid =0;
		try {
			num = Integer.parseInt(bnum);
			bid = Integer.parseInt(id);
			Book book = dao.getBookById(bid);
			Item item = new Item(book, num, book.getPrice()*(100-book.getDiscount())/100 );
			cart.addItem(item); 
            System.out.println("cart new: "+cart.toString());
            dao.addItemIntoCart(item, cart);
		} catch (Exception e) {
			
		}

        session.setAttribute("totalMoney", cart.getTotalMoney());
        session.setAttribute("cart", cart);
        session.setAttribute("size", cart.getSize());
        // session.setAttribute("totalMoney", totalMoney);
        // session.setAttribute("cart", cart);
        // session.setAttribute("size", count);
		LinkedList<String> pageHistory = (LinkedList<String>) session.getAttribute("pageHistory");
		if (pageHistory == null) {
			pageHistory = new LinkedList<>();
		}
        if(!pageHistory.getFirst().equalsIgnoreCase("addcart"))
		    pageHistory.addFirst("addcart");
		int maxHistorySize = 10;
		while (pageHistory.size() > maxHistorySize) {
			pageHistory.removeLast();
		}
		session.setAttribute("pageHistory", pageHistory);
		return "/user/cart";
	}

	@GetMapping(value = "/addcart1")
	public String addCart1(Model model,HttpSession session,HttpServletRequest request) {
		session = request.getSession(true);
		DAO dao = new DAO();
		Cart cart = null;
		Object o = (Cart)session.getAttribute("cart");
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
			Item item = new Item(book, num, book.getPrice()*(100-book.getDiscount())/100 );
			cart.addItem(item);
		} catch (Exception e) {
			
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
		LinkedList<String> pageHistory = (LinkedList<String>) session.getAttribute("pageHistory");
		if (pageHistory == null) {
			pageHistory = new LinkedList<>();
		}
        if(!pageHistory.getFirst().equalsIgnoreCase("addcart"))
		    pageHistory.addFirst("addcart");
		int maxHistorySize = 10;
		while (pageHistory.size() > maxHistorySize) {
			pageHistory.removeLast();
		}
		session.setAttribute("pageHistory", pageHistory);
		return "/user/cart";
	}
	
	@GetMapping(value = "/process")
	public String Process(Model model,HttpServletRequest request,HttpSession session) {
        session = request.getSession(true);
		DAO dao = new DAO();
		Cart cart = (Cart)session.getAttribute("cart");

        String tnum = request.getParameter("num").trim();
        String tid = request.getParameter("id");

		int id, num;
        try {
            id = Integer.parseInt(tid);
            num = Integer.parseInt(tnum);
            Book book = dao.getBookById(id);
            if ((num < 0) && (cart.getQuantityById(id) <= -num)) {
                Item itemdelete = cart.getItemById(id);
                cart.removeItem(id);
                dao.deleteItemFromCart(itemdelete, cart);
            } else {
                int old_size = cart.getSize();
                Item item = new Item(book, num, book.getPrice()*(100-book.getDiscount())/100 );
                cart.addItem(item);
                if (old_size != cart.getSize()){
                    int new_quantity = cart.getItemById(id).getQuantity();
                    dao.editQuantityItem(new_quantity, item, cart);
                }
            }
        } catch (Exception e) {

        }

        session.setAttribute("totalMoney", cart.getTotalMoney());
        session.setAttribute("cart", cart);
        session.setAttribute("size", cart.getSize());
		return "/user/cart";
	}
	
	@GetMapping(value = "/delete")
	public String delete(Model model,HttpServletRequest request,HttpSession session) {
        session = request.getSession(true);
		DAO dao = new DAO();
		Cart cart = (Cart)session.getAttribute("cart");

        int id = Integer.parseInt(request.getParameter("id"));
        Item itemdelete = cart.getItemById(id);
        cart.removeItem(id);
        dao.deleteItemFromCart(itemdelete, cart);

        session.setAttribute("totalMoney", cart.getTotalMoney());
        session.setAttribute("cart", cart);
        session.setAttribute("size", cart.getSize());
		
		return "/user/cart";
	}
}
