package com.example.webbook.UserController;

import java.util.ArrayList;
import java.util.LinkedList;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.webbook.DAO.DAO;
import com.example.webbook.entity.Account;
import com.example.webbook.entity.Book;
import com.example.webbook.entity.Cart;
import com.example.webbook.entity.Category;

@Controller
public class HomeController {
	
	@GetMapping(value = "/home")
	public String home(HttpSession session, Model model,HttpServletRequest request,
			@CookieValue(value = "c_user", defaultValue = "") String user,
			@CookieValue(value = "c_pass", defaultValue = "") String pass,
			@CookieValue(value = "c_rm", defaultValue = "0") String rm) {
				session = request.getSession(false);
		session.removeAttribute("accountAdmin");
		
		DAO dao = new DAO();
		ArrayList<Book> list1 = new ArrayList<>();
		ArrayList<Category> list2 = new ArrayList<>();
		list1 = dao.getAllBook();
		list2 = dao.getAllCategory(0);
		
		// Top sell
		ArrayList<Book> listT = new ArrayList<>();
		listT = dao.getTopSell();
		ArrayList<Book> listT1 = new ArrayList<>();
		ArrayList<Book> listT2 = new ArrayList<>();
		ArrayList<Book> listT3 = new ArrayList<>();
		ArrayList<Book> listT4 = new ArrayList<>();
		ArrayList<Book> listT5 = new ArrayList<>();
		ArrayList<Book> listT6 = new ArrayList<>();
		for (int i = 0; i < listT.size(); i++) {
			if (i<3) {
				listT1.add(listT.get(i));
			}
			else if (i>=3 && i<6) {
				listT2.add(listT.get(i));
			}
			else if ( i>=6 && i<9) {
				listT3.add(listT.get(i));
			}
			else if (i>=9 && i<12) {
				listT4.add(listT.get(i));
			}
			else if (i>=12 && i<15) {
				listT5.add(listT.get(i));
			}
			else {
				listT6.add(listT.get(i));
			}
		}
		model.addAttribute("listT1", listT1);
		model.addAttribute("listT2", listT2);
		model.addAttribute("listT3", listT3);
		model.addAttribute("listT4", listT4);
		model.addAttribute("listT5", listT5);
		model.addAttribute("listT6", listT6);
		model.addAttribute("listC", list2);
		// Phân trang
		int page,numpage = 24;
		int num = (list1.size()%numpage==0)?list1.size()/numpage:list1.size()/numpage+1;
		if(num==0) {
			num=1;
		}
		String str = request.getParameter("page");
		if (str==null) {
			page = 1;
		}
		else {
			page = Integer.parseInt(str);
		}
		if (page>num) {
			page = num;
		}
		int start,end;
		start = (page-1)*numpage;
		end = Math.min(page*numpage, list1.size());
		ArrayList<Book> list = dao.getListByPage(list1, start, end);		
		ArrayList<Integer> numlist = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			numlist.add(i+1);
		}
		model.addAttribute("listS", list);
		model.addAttribute("page", page);
		model.addAttribute("numlist", numlist);
		model.addAttribute("username", user);
		model.addAttribute("password", pass);
		model.addAttribute("rm", Integer.parseInt(rm));
		model.addAttribute("currentPage", "home");
		LinkedList<String> pageHistory = (LinkedList<String>) session.getAttribute("pageHistory");
		if (pageHistory == null) {
			pageHistory = new LinkedList<>();
			pageHistory.addFirst("home");
		}
		if(!pageHistory.getFirst().equalsIgnoreCase("home"))
		    pageHistory.addFirst("home");
		int maxHistorySize = 10;
		while (pageHistory.size() > maxHistorySize) {
			pageHistory.removeLast();
		}
		session.setAttribute("pageHistory", pageHistory);
		return "/user/home";
	}
	
	@GetMapping(value = "/category")
	public String category(Model model,HttpServletRequest request,HttpSession session) {
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		// Lấy sách theo cid và phân trang
		ArrayList<Book> list1 = new ArrayList<>();
		DAO dao = new DAO();
		try {
			int cid = Integer.parseInt(id);
			list1 = dao.getAllBookByCid(cid);
		} catch (Exception e) {
			
		}
		
		int page,numpage = 18;
		int num = (list1.size()%18==0)?list1.size()/18:list1.size()/18+1;
		if(num==0) {
			num=1;
		}
		String str = request.getParameter("page");
		if (str==null) {
			page = 1;
		}
		else {
			page = Integer.parseInt(str);
		}
		if (page>num) {
			page = num;
		}
		int start,end;
		start = (page-1)*numpage;
		end = Math.min(page*numpage, list1.size());
		ArrayList<Book> list = dao.getListByPage(list1, start, end);
		ArrayList<Integer> numlist = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			numlist.add(i+1);
		}
		model.addAttribute("listS", list);
		model.addAttribute("page", page);
		model.addAttribute("numlist", numlist);
		
		// lấy category
		ArrayList<Category> list2 = new ArrayList<>();
		list2 = dao.getAllCategory(0);
		model.addAttribute("listC", list2);
		
		// lấy top bán chạy
		ArrayList<Book> listT = new ArrayList<>();
		listT = dao.getTopSell();
		ArrayList<Book> listT1 = new ArrayList<>();
		ArrayList<Book> listT2 = new ArrayList<>();
		ArrayList<Book> listT3 = new ArrayList<>();
		ArrayList<Book> listT4 = new ArrayList<>();
		ArrayList<Book> listT5 = new ArrayList<>();
		ArrayList<Book> listT6 = new ArrayList<>();
		for (int i = 0; i < listT.size(); i++) {
			if (i<3) {
				listT1.add(listT.get(i));
			}
			else if (i>=3 && i<6) {
				listT2.add(listT.get(i));
			}
			else if ( i>=6 && i<9) {
				listT3.add(listT.get(i));
			}
			else if (i>=9 && i<12) {
				listT4.add(listT.get(i));
			}
			else if (i>=12 && i<15) {
				listT5.add(listT.get(i));
			}
			else {
				listT6.add(listT.get(i));
			}
		}
		model.addAttribute("listT1", listT1);
		model.addAttribute("listT2", listT2);
		model.addAttribute("listT3", listT3);
		model.addAttribute("listT4", listT4);
		model.addAttribute("listT5", listT5);
		model.addAttribute("listT6", listT6);
		session = request.getSession(false);
		LinkedList<String> pageHistory = (LinkedList<String>) session.getAttribute("pageHistory");
		if (pageHistory == null) {
			pageHistory = new LinkedList<>();
		}
		try{
			if(!pageHistory.getFirst().equalsIgnoreCase("category?id="+id))
		    	pageHistory.addFirst("category?id="+id);
		}catch(Exception e){
			pageHistory = new LinkedList<String>();
			pageHistory.add("home");
		}
		
		int maxHistorySize = 10;
		while (pageHistory.size() > maxHistorySize) {
			pageHistory.removeLast();
		}
		session.setAttribute("pageHistory", pageHistory);
		return "/user/home";
	}
	
	@PostMapping(value = "/login")
	public String login(HttpServletRequest req,HttpServletResponse res,Model model,HttpSession session) {
		String name = req.getParameter("user");
		String pass = req.getParameter("pass");
		String remember = req.getParameter("remember");
		
		// Tạo cookies
		Cookie cook_user = new Cookie("c_user", name);
        Cookie cook_pass = new Cookie("c_pass", pass);
        Cookie cook_remember = new Cookie("c_rm", remember);
        if (remember!=null){
            cook_user.setMaxAge(60*60*24*7);
            cook_pass.setMaxAge(60*60*24*7);
            cook_remember.setMaxAge(60*60*24*7);
        }
        else {
            cook_user.setMaxAge(0);
            cook_pass.setMaxAge(0);
            cook_remember.setMaxAge(0);
        }
        res.addCookie(cook_user);
        res.addCookie(cook_pass);
        res.addCookie(cook_remember);
        
        // Khởi tạo session Account
        DAO dao = new DAO();
        Account ac = dao.getAccount(name, pass);
        if (ac == null) {
            session.setAttribute("mess", "Sai tài khoản hoặc mật khẩu");
        }
        else {
            session.setAttribute("account", ac);
			Cart cart =dao.getCartByUid(ac.getId());
			session.setAttribute("totalMoney", cart.getTotalMoney());
			session.setAttribute("cart", cart);
			session.setAttribute("size", cart.getSize());
			System.out.println(cart);
        } 
        session.removeAttribute("checkLoginUser");
		return "redirect:/home";
	}
	
	@GetMapping(value = "/noLogin")
	public String noLogin(HttpServletRequest request,Model model,HttpSession session) {
		session.setAttribute("checkLoginUser", 1);
		session.removeAttribute("mess");
		return"redirect:/home";
	}
	
	@GetMapping(value = "/logout")
	public String logout(HttpSession session,HttpServletRequest req) {
		session.removeAttribute("account");
		session.removeAttribute("cart");
		session.removeAttribute("size");
		session.removeAttribute("mess");
		return"redirect:/home";
	}
	
	@GetMapping(value = "/signup")
	public String signup() {
		return "/user/signup";
	}
	
	@PostMapping(value = "/signup/form")
	public String signupForm(HttpServletRequest request,Model model) {
		DAO dao = new DAO();
		String email = request.getParameter("email");
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String repassword = request.getParameter("repass");
        
        if (dao.getAccountByEmail(email)==1) {
        	model.addAttribute("email", email);
            model.addAttribute("user", username);
            model.addAttribute("pass", password);
            model.addAttribute("repass", repassword);
            model.addAttribute("mess4", "Email đã đăng ký");
        	return "/user/signup";
        }
        else if(dao.getAccountByUserName(username)==1){
        	model.addAttribute("email", email);
            model.addAttribute("user", username);
            model.addAttribute("pass", password);
            model.addAttribute("repass", repassword);
            model.addAttribute("mess2", "Tên đăng nhập đã tồn tại");
            return "/user/signup";
        }
        else if (!password.equals(repassword)){
        	model.addAttribute("email", email);
            model.addAttribute("user", username);
            model.addAttribute("pass", password);
            model.addAttribute("repass", repassword);
            model.addAttribute("mess1", "Xác nhận lại mật khẩu");
            return "/user/signup";
        }
        else {
        	dao.insertAccount(username, password,email);
        	model.addAttribute("email", email);
            model.addAttribute("user", username);
            model.addAttribute("mess5", "Đăng ký thành công");
            return "/user/signup";
        }
	}
	
	@GetMapping(value = "/search")
	public String search (Model model,HttpServletRequest request,HttpSession session) {
		session = request.getSession(false);
		String string = request.getParameter("search");
		DAO dao = new DAO();
		ArrayList<Book> list1 = new ArrayList<>();
		list1 = dao.getBookBySearch(string);
		ArrayList<Category> list2 = dao.getAllCategory(0);
		
		// get list Top
		ArrayList<Book> listT = new ArrayList<>();
		listT = dao.getTopSell();
		ArrayList<Book> listT1 = new ArrayList<>();
		ArrayList<Book> listT2 = new ArrayList<>();
		ArrayList<Book> listT3 = new ArrayList<>();
		ArrayList<Book> listT4 = new ArrayList<>();
		ArrayList<Book> listT5 = new ArrayList<>();
		ArrayList<Book> listT6 = new ArrayList<>();
		for (int i = 0; i < listT.size(); i++) {
			if (i<3) {
				listT1.add(listT.get(i));
			}
			else if (i>=3 && i<6) {
				listT2.add(listT.get(i));
			}
			else if ( i>=6 && i<9) {
				listT3.add(listT.get(i));
			}
			else if (i>=9 && i<12) {
				listT4.add(listT.get(i));
			}
			else if (i>=12 && i<15) {
				listT5.add(listT.get(i));
			}
			else {
				listT6.add(listT.get(i));
			}
		}
		model.addAttribute("listT1", listT1);
		model.addAttribute("listT2", listT2);
		model.addAttribute("listT3", listT3);
		model.addAttribute("listT4", listT4);
		model.addAttribute("listT5", listT5);
		model.addAttribute("listT6", listT6);
		model.addAttribute("listC", list2);
		
		// phan trang
		int page,numpage = 18;
		if (list1.size()==0) {
			model.addAttribute("err", "Không tìm thấy kết quả");
			Account sc = (Account) session.getAttribute("account");
			String mess1 = (String) session.getAttribute("mess");
			model.addAttribute("mess", mess1);
			model.addAttribute("account", sc);
			model.addAttribute("search", 1);
			return "/user/home";
		}
		
			int num = (list1.size()%18==0)?list1.size()/18:list1.size()/18+1;
			if(num==0) {
				num=1;
			}
			String str = request.getParameter("page");
			if (str==null) {
				page = 1;
			}
			else {
				page = Integer.parseInt(str);
			}
			if (page>num) {
				page = num;
			}
			int start,end;
			start = (page-1)*numpage;
			end = Math.min(page*numpage, list1.size());
			ArrayList<Book> list = dao.getListByPage(list1, start, end);		
			ArrayList<Integer> numlist = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				numlist.add(i+1);
			}
			model.addAttribute("listS", list);
			model.addAttribute("page", page);
			model.addAttribute("numlist", numlist);
			model.addAttribute("str", string);
			model.addAttribute("search", 1);
		
		
		//session account
		String mess1 = (String) session.getAttribute("mess");
		model.addAttribute("mess", mess1);
		LinkedList<String> pageHistory = (LinkedList<String>) session.getAttribute("pageHistory");
		if (pageHistory == null) {
			pageHistory = new LinkedList<>();
		}
		try{
		if(!pageHistory.getFirst().equalsIgnoreCase("search?search="+string))
		    pageHistory.addFirst("search?search="+string);
		}catch(Exception e){
			pageHistory = new LinkedList<String>();
			pageHistory.add("home");
		}
		int maxHistorySize = 10;
		while (pageHistory.size() > maxHistorySize) {
			pageHistory.removeLast();
		}
		session.setAttribute("pageHistory", pageHistory);
		return "/user/home";
	}

	@GetMapping(value = "/topsale")
	public String topsale (Model model,HttpServletRequest request,HttpSession session) {
		session = request.getSession(false);
		// String string = request.getParameter("search");
		DAO dao = new DAO();
		ArrayList<Book> list1 = new ArrayList<>();
		list1 = dao.getTopSell();
		ArrayList<Category> list2 = dao.getAllCategory(0);
		
		// get list Top
		ArrayList<Book> listT = new ArrayList<>();
		listT = dao.getTopSell();
		ArrayList<Book> listT1 = new ArrayList<>();
		ArrayList<Book> listT2 = new ArrayList<>();
		ArrayList<Book> listT3 = new ArrayList<>();
		ArrayList<Book> listT4 = new ArrayList<>();
		ArrayList<Book> listT5 = new ArrayList<>();
		ArrayList<Book> listT6 = new ArrayList<>();
		for (int i = 0; i < listT.size(); i++) {
			if (i<3) {
				listT1.add(listT.get(i));
			}
			else if (i>=3 && i<6) {
				listT2.add(listT.get(i));
			}
			else if ( i>=6 && i<9) {
				listT3.add(listT.get(i));
			}
			else if (i>=9 && i<12) {
				listT4.add(listT.get(i));
			}
			else if (i>=12 && i<15) {
				listT5.add(listT.get(i));
			}
			else {
				listT6.add(listT.get(i));
			}
		}
		model.addAttribute("listT1", listT1);
		model.addAttribute("listT2", listT2);
		model.addAttribute("listT3", listT3);
		model.addAttribute("listT4", listT4);
		model.addAttribute("listT5", listT5);
		model.addAttribute("listT6", listT6);
		model.addAttribute("listC", list2);
		
		// phan trang
		int page,numpage = 18;
		// if (list1.size()==0) {
		// 	model.addAttribute("err", "Không tìm thấy kết quả");
		// 	Account sc = (Account) session.getAttribute("account");
		// 	String mess1 = (String) session.getAttribute("mess");
		// 	model.addAttribute("mess", mess1);
		// 	model.addAttribute("account", sc);
		// 	model.addAttribute("search", 1);
		// 	return "/user/home";
		// }
		
			int num = (list1.size()%18==0)?list1.size()/18:list1.size()/18+1;
			if(num==0) {
				num=1;
			}
			String str = request.getParameter("page");
			if (str==null) {
				page = 1;
			}
			else {
				page = Integer.parseInt(str);
			}
			if (page>num) {
				page = num;
			}
			int start,end;
			start = (page-1)*numpage;
			end = Math.min(page*numpage, list1.size());
			ArrayList<Book> list = dao.getListByPage(list1, start, end);		
			ArrayList<Integer> numlist = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				numlist.add(i+1);
			}
			model.addAttribute("listS", list);
			model.addAttribute("page", page);
			model.addAttribute("numlist", numlist);
			// model.addAttribute("str", string);
			// model.addAttribute("search", 1);
		
		
		//session account
		String mess1 = (String) session.getAttribute("mess");
		model.addAttribute("mess", mess1);
		model.addAttribute("currentPage", "topsale");

		LinkedList<String> pageHistory = (LinkedList<String>) session.getAttribute("pageHistory");
		if (pageHistory == null) {
			pageHistory = new LinkedList<>();
		}
		try{
		if(!pageHistory.getFirst().equalsIgnoreCase("topsale"))
			pageHistory.addFirst("topsale");
		}catch(Exception e){
			pageHistory = new LinkedList<String>();
			pageHistory.add("home");
		}
		int maxHistorySize = 10;
		while (pageHistory.size() > maxHistorySize) {
			pageHistory.removeLast();
		}
		session.setAttribute("pageHistory", pageHistory);
		return "/user/home";
	}

	@GetMapping(value = "/topdiscount")
	public String topdiscount (Model model,HttpServletRequest request,HttpSession session) {
		session = request.getSession(false);
		// String string = request.getParameter("search");
		DAO dao = new DAO();
		ArrayList<Book> list1 = new ArrayList<>();
		list1 = dao.getTopDiscount();
		ArrayList<Category> list2 = dao.getAllCategory(0);
		
		// get list Top
		ArrayList<Book> listT = new ArrayList<>();
		listT = dao.getTopSell();
		ArrayList<Book> listT1 = new ArrayList<>();
		ArrayList<Book> listT2 = new ArrayList<>();
		ArrayList<Book> listT3 = new ArrayList<>();
		ArrayList<Book> listT4 = new ArrayList<>();
		ArrayList<Book> listT5 = new ArrayList<>();
		ArrayList<Book> listT6 = new ArrayList<>();
		for (int i = 0; i < listT.size(); i++) {
			if (i<3) {
				listT1.add(listT.get(i));
			}
			else if (i>=3 && i<6) {
				listT2.add(listT.get(i));
			}
			else if ( i>=6 && i<9) {
				listT3.add(listT.get(i));
			}
			else if (i>=9 && i<12) {
				listT4.add(listT.get(i));
			}
			else if (i>=12 && i<15) {
				listT5.add(listT.get(i));
			}
			else {
				listT6.add(listT.get(i));
			}
		}
		model.addAttribute("listT1", listT1);
		model.addAttribute("listT2", listT2);
		model.addAttribute("listT3", listT3);
		model.addAttribute("listT4", listT4);
		model.addAttribute("listT5", listT5);
		model.addAttribute("listT6", listT6);
		model.addAttribute("listC", list2);
		
		// phan trang
		int page,numpage = 18;
		// if (list1.size()==0) {
		// 	model.addAttribute("err", "Không tìm thấy kết quả");
		// 	Account sc = (Account) session.getAttribute("account");
		// 	String mess1 = (String) session.getAttribute("mess");
		// 	model.addAttribute("mess", mess1);
		// 	model.addAttribute("account", sc);
		// 	model.addAttribute("search", 1);
		// 	return "/user/home";
		// }
		
			int num = (list1.size()%18==0)?list1.size()/18:list1.size()/18+1;
			if(num==0) {
				num=1;
			}
			String str = request.getParameter("page");
			if (str==null) {
				page = 1;
			}
			else {
				page = Integer.parseInt(str);
			}
			if (page>num) {
				page = num;
			}
			int start,end;
			start = (page-1)*numpage;
			end = Math.min(page*numpage, list1.size());
			ArrayList<Book> list = dao.getListByPage(list1, start, end);		
			ArrayList<Integer> numlist = new ArrayList<>();
			for (int i = 0; i < num; i++) {
				numlist.add(i+1);
			}
			model.addAttribute("listS", list);
			model.addAttribute("page", page);
			model.addAttribute("numlist", numlist);
			// model.addAttribute("str", string);
			// model.addAttribute("search", 1);
		
		
		//session account
		String mess1 = (String) session.getAttribute("mess");
		model.addAttribute("mess", mess1);
		model.addAttribute("currentPage", "topdiscount");
		LinkedList<String> pageHistory = (LinkedList<String>) session.getAttribute("pageHistory");
		if (pageHistory == null) {
			pageHistory = new LinkedList<>();
		}
		try{
			if(!pageHistory.getFirst().equalsIgnoreCase("topdiscount"))
				pageHistory.addFirst("topdiscount");
		}catch(Exception e){
			pageHistory = new LinkedList<String>();
			pageHistory.add("home");
		}
		int maxHistorySize = 10;
		while (pageHistory.size() > maxHistorySize) {
			pageHistory.removeLast();
		}
		session.setAttribute("pageHistory", pageHistory);
		return "/user/home";
	}


	@GetMapping("/prePage")
    public String navigateToPrePage(Model model,HttpServletRequest request,HttpSession session) {
		session = request.getSession(false);
		String currentPage = request.getParameter("currentPage");
        LinkedList<String> pageHistory = (LinkedList<String>) session.getAttribute("pageHistory");
		System.out.println(pageHistory);
		if (pageHistory == null) {
			pageHistory = new LinkedList<>();
		}
		if (currentPage.contains("detail")){
			if (pageHistory.getFirst().contains("detail"))
				pageHistory.removeFirst();
		}else if(currentPage.contains("addcart")){
			if(pageHistory.getFirst().contains("addcart"))
				pageHistory.removeFirst();
		}else{
			pageHistory.removeFirst();
		}
		String prePage = pageHistory.getFirst();
		System.out.println(prePage);

		if(prePage == null) {
			prePage = "home";
		}
        // Chuyển hướng đến trang trước đó
        return "redirect:/"+prePage;
    }
}
