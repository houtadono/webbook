package com.example.webbook.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.example.webbook.entity.Account;
import com.example.webbook.entity.Book;
import com.example.webbook.entity.Cart;
import com.example.webbook.entity.Category;
import com.example.webbook.entity.Item;
import com.example.webbook.entity.Order;
import com.example.webbook.entity.OrderLine;
import com.example.webbook.entity.Review;

public class DAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public ArrayList<Book> getAllBook() {
		ArrayList<Book> list = new ArrayList<>();
		String query = "select * from Book";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getInt(10),
						rs.getInt(11), rs.getInt(12)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	public ArrayList<Book> getAllBookByCid(int cid) {
		ArrayList<Book> list = new ArrayList<>();
		String query = "select * from Book where cid=?";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, cid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getInt(10),
						rs.getInt(11), rs.getInt(12)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	public ArrayList<Book> getAllBookForAdmin() {
		ArrayList<Book> list = new ArrayList<>();
		String query = "select Book.id,Book.title,Book.author,Book.day,Book.page,Book.cid,Category.name,Book.sold,Book.quantity \r\n"
				+ "from Book inner join Category on Book.cid = Category.id";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			int stt = 1;
			while (rs.next()) {
				Book b = new Book(stt, rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5),
						rs.getInt(6), rs.getString(7), rs.getInt(8));
				b.setQuantity(rs.getInt(9));

				list.add(b);

				stt++;
			}
		} catch (Exception e) {
		}
		return list;
	}

	public ArrayList<Book> getAllBookForAdminBySearch(String str) {
		ArrayList<Book> list = new ArrayList<>();
		String query = "select Book.id,Book.title,Book.author,Book.day,Book.page,Book.cid,Category.name,Book.sold \r\n"
				+ "from Book inner join Category on Book.cid = Category.id and Book.title like ?";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + str + "%");
			rs = ps.executeQuery();
			int stt = 1;
			while (rs.next()) {
				list.add(new Book(stt, rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5),
						rs.getInt(6), rs.getString(7), rs.getInt(8)));
				stt++;
			}
		} catch (Exception e) {
		}
		return list;
	}

	public Book getBookById(int id) {
		String query = "select Book.id,Book.title,Book.author,[description],[day],\r\n"
				+ "[page],[name],[image],Book.quantity,Book.sold,Book.price, Book.discount \r\n"
				+ "from Book inner join Category on Book.cid = Category.id and Book.id = ?";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getDate(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10),
						rs.getInt(11));
				book.setDiscount(rs.getInt(12));
				return book;
			}
		} catch (Exception e) {
		}
		return null;
	}

	public int checkBookExists(String title, String author) {
		String query = "select * from Book where Book.title = ? and Book.author = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, title);
			ps.setString(2, author);
			rs = ps.executeQuery();
			while (rs.next()) {
				return 1;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	public int checkBookExistsforEdit(String title, String author, int bid) {
		String query = "select * from Book where Book.title = ? and Book.author = ? and Book.id != ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, title);
			ps.setString(2, author);
			ps.setInt(3, bid);
			rs = ps.executeQuery();
			while (rs.next()) {
				return 1;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	public void insertBook(Book b) {
		String query = "insert into Book values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, b.getTitle());
			ps.setString(2, b.getAuthor());
			ps.setString(3, b.getDescription());
			ps.setDate(4, b.getDay());
			ps.setInt(5, b.getPage());
			ps.setInt(6, b.getCid());
			ps.setString(7, b.getImage());
			ps.setInt(8, b.getQuantity()); // TODO
			ps.setInt(9, 0); // sold
			ps.setInt(10, b.getPrice());
			ps.setInt(11, b.getDiscount());

			rs = ps.executeQuery();
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public void updateBook(Book b, int bid) {
		if (b.getImage() == null) {
			String query = "update Book set title = ?,author = ?,[description] = ?,\r\n"
					+ "[day] = ?,[page] = ?,cid = ?,[price] = ?, [quantity] = ?, [discount] = ? where Book.id = ?";
			try {
				conn = new SQLConnection().getConnection();
				ps = conn.prepareStatement(query);
				ps.setString(1, b.getTitle());
				ps.setString(2, b.getAuthor());
				ps.setString(3, b.getDescription());
				ps.setDate(4, b.getDay());
				ps.setInt(5, b.getPage());
				ps.setInt(6, b.getCid());
				ps.setInt(7, b.getPrice());
				ps.setInt(8, b.getQuantity());
				ps.setInt(9, b.getDiscount());
				ps.setInt(10, bid);
				rs = ps.executeQuery();
				ps.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			String query = "update Book set title = ?,author = ?,[description] = ?,\r\n"
					+ "[day] = ?,[page] = ?,cid = ?,[price] = ?, [quantity] = ?, [discount] = ?,[image] = ?   where Book.id = ?";

			try {
				conn = new SQLConnection().getConnection();
				ps = conn.prepareStatement(query);
				ps.setString(1, b.getTitle());
				ps.setString(2, b.getAuthor());
				ps.setString(3, b.getDescription());
				ps.setDate(4, b.getDay());
				ps.setInt(5, b.getPage());
				ps.setInt(6, b.getCid());
				ps.setInt(7, b.getPrice());
				ps.setInt(8, b.getQuantity());
				ps.setInt(9, b.getDiscount());
				ps.setString(10, b.getImage());
				ps.setInt(11, bid);

				rs = ps.executeQuery();
				ps.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public int getQuantityBookAtUnderById(int id) {
		String query = "select count(*) from Book where Book.id <= ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {

		}
		return 0;
	}

	public ArrayList<Book> getBookBySearch(String str) {
		ArrayList<Book> list = new ArrayList<>();
		String query = "select*from Book where title like ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + str + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getInt(9),
						rs.getInt(10), rs.getInt(11), rs.getInt(12));
				list.add(book);
			}
		} catch (Exception e) {
		}
		return list;
	}

	public void updateBookSold(int bid, int quantity) {
		String query = "update Book set Book.sold = Book.sold + ?,Book.quantity = Book.quantity - ?\r\n"
				+ "where Book.id = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, quantity);
			ps.setInt(2, quantity);
			ps.setInt(3, bid);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public void deleteBookById(int bid) {
		String query = "delete Book where Book.id = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, bid);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public void updateBookSoldforDeleteOrder(int bid, int quantity) {
		String query = "update Book set Book.sold = Book.sold - ?,Book.quantity = Book.quantity + ?\r\n"
				+ "where Book.id = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, quantity);
			ps.setInt(2, quantity);
			ps.setInt(3, bid);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public Category getCategoryByCid(int cid) {
		String query = "select * from Category where Category.id = ?";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, cid);
			rs = ps.executeQuery();
			while (rs.next()) {
				return new Category(rs.getInt(1), rs.getString(2));
			}
		} catch (Exception e) {
		}
		return null;
	}

	public ArrayList<Category> getAllCategory(int k) {
		ArrayList<Category> list = new ArrayList<>();
		String query = "select * from Category where Category.id != ?";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, k);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Category(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	public ArrayList<Category> getAllCategoryExcpet(int k) {
		DAO dao = new DAO();
		ArrayList<Category> list = new ArrayList<>();
		Category c = dao.getCategoryByCid(k);
		list.add(c);

		String query = "select * from Category where Category.id != ?";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, k);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Category(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	// Phân trang sách
	public ArrayList<Book> getListByPage(ArrayList<Book> list, int start, int end) {
		ArrayList<Book> arr = new ArrayList<>();
		for (int i = start; i < end; i++) {
			arr.add(list.get(i));
		}
		return arr;
	}

	// Phan trang Review
	public ArrayList<Review> getListReviewByPage(ArrayList<Review> list, int start, int end) {
		ArrayList<Review> arr = new ArrayList<>();
		for (int i = start; i < end; i++) {
			arr.add(list.get(i));
		}
		return arr;
	}

	public ArrayList<Order> getListOrderByPage(ArrayList<Order> list, int start, int end) {
		ArrayList<Order> arr = new ArrayList<>();
		for (int i = start; i < end; i++) {
			arr.add(list.get(i));
		}
		return arr;
	}

	public ArrayList<Book> getTopSell() {
		ArrayList<Book> list = new ArrayList<>();
		String query = "SELECT top 18 * FROM Book ORDER BY Book.sold DESC";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getInt(9),
						rs.getInt(10), rs.getInt(11), rs.getInt(12));
				list.add(book);
			}
		} catch (Exception e) {
		}
		return list;
	}

	public ArrayList<Book> getTopDiscount() {
		ArrayList<Book> list = new ArrayList<>();
		String query = "SELECT * FROM Book WHERE Book.discount > 0 ORDER BY Book.discount DESC ";
		try {
			conn = new SQLConnection().getConnection(); // Mở kết nối với SQL
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getString(8),
						rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12));
				list.add(book);
			}
		} catch (Exception e) {
			// Xử lý ngoại lệ nếu cần
		}
		return list;
	}

	// Get Account
	public Account getAccount(String user, String pass) {
		String query = "select*from [User] where [username] = ? and password = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user);
			ps.setString(2, pass);
			rs = ps.executeQuery();
			if (rs.next()) {
				Account ac = new Account(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getInt(5));
				return ac;
			}
		} catch (Exception e) {
		}
		return null;
	}

	public int getAccountByUserName(String user) {
		String query = "select*from [User] where [username] = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user);
			rs = ps.executeQuery();
			if (rs.next()) {
				return 1;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	public int getAccountByEmail(String mail) {
		String query = "select*from [User] where [email] = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, mail);
			rs = ps.executeQuery();
			if (rs.next()) {
				return 1;
			}
		} catch (Exception e) {
		}
		return -1;
	}

	public void insertAccount(String user, String pass, String email) {
		String query = "insert into [User] values(?,?,?,?)";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user);
			ps.setString(2, pass);
			ps.setString(3, email);
			ps.setInt(4, 2);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public void insertReview(Review rv) {
		String query = "insert into Review values(?,?,?,?,?)";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, rv.getUid());
			ps.setInt(2, rv.getBid());
			ps.setString(3, rv.getContent());
			ps.setInt(4, rv.getRating());
			ps.setString(5, rv.getDay());
			rs = ps.executeQuery();
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public ArrayList<Review> getReviewById(int id) {
		ArrayList<Review> list = new ArrayList<>();
		String query = "select username,content,rating,[day] \r\n"
				+ "from Review inner join [User] on Review.uid = [User].id and bid = ? ";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Review(rs.getString(1), rs.getString(2), rs.getInt(3),
						String.valueOf(rs.getDate(4))));
			}
		} catch (Exception e) {
		}
		return list;
	}

	public int getLastOrderId() {
		String query = "select top(1)Orders.id from Orders order by Orders.id desc";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
		}
		return 0;
	}

	public void insertOrder(Order o) {
		String query = "insert into Orders values(?,?,?,?,?,?,?,?)";

		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, o.getUid());
			ps.setInt(2, o.getQuantity());
			ps.setString(3, o.getDay());
			ps.setInt(4, o.getTotalMoney());
			ps.setInt(5, o.getStatus());
			ps.setString(6, o.getName());
			ps.setString(7, o.getPhone());
			ps.setString(8, o.getAddress());
			rs = ps.executeQuery();
			System.out.println(ps);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void updateOrder(int sl, int money, int oid) {
		String query = "update Orders set quantity = quantity - ?,totalMoney = totalMoney-? where Orders.id = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, sl);
			ps.setInt(2, money);
			ps.setInt(3, oid);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public void insertOrderLine(OrderLine o) {
		String query = "insert into OrderLine values(?,?,?,?,?)";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, o.getOid());
			ps.setInt(2, o.getBid());
			ps.setInt(3, o.getQuantity());
			ps.setInt(4, o.getTotalMoney());
			ps.setInt(5, o.getPrice());
			rs = ps.executeQuery();
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public Cart getCartByUid(int uid){
		Cart cart = new Cart();
		String queryOrder = "select Orders.id,Orders.quantity from Orders where Orders.uid = ? and Orders.status = -2";
		try {
	            conn = new SQLConnection().getConnection();//mo ket noi voi sql
	            ps = conn.prepareStatement(queryOrder);
	            ps.setInt(1, uid);
	            rs = ps.executeQuery();
	            if (rs.next()) {
					cart.setUid(rs.getInt(1));		
					cart.setSize(rs.getInt(2));
	            }else{
					String insertOrderQuery = "INSERT INTO Orders (uid, status, quantity) VALUES (?, -2, 0)";
					ps = conn.prepareStatement(insertOrderQuery);
					ps.setInt(1, uid);
					
					int affectedRows = ps.executeUpdate();
					if (affectedRows > 0) {
						ResultSet generatedKeys = ps.getGeneratedKeys();
						if (generatedKeys.next()) {
							cart.setUid(generatedKeys.getInt(1));
							cart.setSize(0); 
						}
					}
				}
	    } catch (Exception e) {
	    }
		if(cart.getSize() > 0){
			String queryOrderLine = "select Orders.id,Book.id, Book.image,Book.title,Book.author,Category.name,Book.price, Book.discount, "
				+ "OrderLine.quantity,OrderLine.totalMoney\r\n"
				+ "from Book inner join Category on Book.cid = Category.id inner join\r\n"
				+ "OrderLine on Book.id = OrderLine.bid inner join Orders on OrderLine.oid = Orders.id and oid = ?";
			try {
				conn = new SQLConnection().getConnection();
				ps = conn.prepareStatement(queryOrderLine);
				ps.setInt(1, cart.getUid());
				rs = ps.executeQuery();
				int size = 0;
				int money = 0;
				while (rs.next()) {
					Item i = new Item();
					Book book = new Book();
					book.setId(rs.getInt(2));
					book.setImage(rs.getString(3));
					book.setTitle(rs.getString(4));
					book.setAuthor(rs.getString(5));
					book.setCategory(rs.getString(6));
					book.setPrice(rs.getInt(7));					
					book.setDiscount(rs.getInt(8));

					int num = rs.getInt(9);
					size += num;
					Item item = new Item(book, num, book.getPrice()*(100-book.getDiscount())/100 );
					money += item.getPrice() * item.getQuantity();
					cart.addItem(item);
				}
				cart.setTotalMoney(money);
				cart.setSize(size);
			} catch (Exception e) {
			}
		}
	    return cart;
	 }

	public ArrayList<OrderLine> getAllOrderLineByOid(int oid) {
		ArrayList<OrderLine> list = new ArrayList<>();
		String query = "select Orders.id,Book.id, Book.image,Book.title,Book.author,Category.name,OrderLine.price ,OrderLine.quantity,OrderLine.totalMoney\r\n"
				+ "from Book inner join Category on Book.cid = Category.id inner join\r\n"
				+ "OrderLine on Book.id = OrderLine.bid inner join Orders on OrderLine.oid = Orders.id and oid = ?";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, oid);
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderLine a = new OrderLine(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
				System.out.println(a);
				list.add(a);
			}
		} catch (Exception e) {
		}
		return list;
	}

	public void detleteOrderLineByOidAndBid(int oid, int bid) {
		String query = "delete OrderLine where OrderLine.oid = ? and OrderLine.bid = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, oid);
			ps.setInt(2, bid);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public void detleteOrderLineByOid(int oid) {
		String query = "delete OrderLine where OrderLine.oid = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, oid);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public ArrayList<Order> getAllOrderStatus0ByUid(int uid) {
		ArrayList<Order> list = new ArrayList<>();
		String query = "select Orders.id,Orders.quantity,Orders.date,Orders.totalMoney,Orders.status, Orders.name, Orders.phone, Orders.address\r\n"
				+ "from Orders where Orders.uid = ? and Orders.status = 0";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, uid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order o = new Order(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
				o.setName(rs.getString(6));
				o.setPhone(rs.getString(7));
				o.setAddress(rs.getString(8));
				list.add(o);
			}
		} catch (Exception e) {
		}
		return list;
	}

	public ArrayList<Order> getAllOrderStatusByAdmin(int status) {
		ArrayList<Order> list = new ArrayList<>();
		String query = "select Orders.id,[User].username,Orders.date,Orders.quantity,Orders.totalMoney,Orders.status, Orders.name,Orders.phone,Orders.address\r\n"
				+ "from Orders inner join [User] on Orders.uid = [User].id and Orders.status = ?";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, status);
			rs = ps.executeQuery();
			int stt = 1;
			while (rs.next()) {
				Order o = new Order(stt, rs.getInt(1), rs.getString(2), String.valueOf(rs.getDate(3)),
						rs.getInt(4), rs.getInt(5), rs.getInt(6));
				o.setName(rs.getString(7));
				o.setPhone(rs.getString(8));
				o.setAddress(rs.getString(9));
				list.add(o);
				stt++;
			}
		} catch (Exception e) {
		}
		return list;
	}

	public ArrayList<Order> getAllOrderBySearch(int status, String username) {
		ArrayList<Order> list = new ArrayList<>();
		String query = "select Orders.id,[User].username,Orders.date,Orders.quantity,Orders.totalMoney,Orders.status, Orders.name,Orders.phone,Orders.address\r\n"
				+ "from Orders inner join [User] on Orders.uid = [User].id and Orders.status = ? and [User].username like ?";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, status);
			ps.setString(2, "%" + username + "%");
			rs = ps.executeQuery();
			int stt = 1;
			while (rs.next()) {
				Order o = new Order(stt, rs.getInt(1), rs.getString(2), String.valueOf(rs.getDate(3)),
						rs.getInt(4), rs.getInt(5), rs.getInt(6));
				o.setName(rs.getString(7));
				o.setPhone(rs.getString(8));
				o.setAddress(rs.getString(9));
				list.add(o);
				stt++;
			}
		} catch (Exception e) {
		}
		return list;
	}

	public ArrayList<Order> getAllOrderStatus1ByUid(int uid) {
		ArrayList<Order> list = new ArrayList<>();
		String query = "select Orders.id,Orders.quantity,Orders.date,Orders.totalMoney,Orders.status, Orders.name,Orders.phone,Orders.address\r\n"
				+ "from Orders where Orders.uid = ? and Orders.status = 1";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, uid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order o = new Order(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
				o.setName(rs.getString(6));
				o.setPhone(rs.getString(7));
				o.setAddress(rs.getString(8));
				list.add(o);
			}
		} catch (Exception e) {
		}
		return list;
	}

	public ArrayList<Order> getAllOrderDeletedByUid(int uid) {
		ArrayList<Order> list = new ArrayList<>();
		String query = "select Orders.id,Orders.quantity,Orders.date,Orders.totalMoney,Orders.status, Orders.name,Orders.phone,Orders.address\r\n"
				+ "from Orders where Orders.uid = ? and Orders.status = -1";
		try {
			conn = new SQLConnection().getConnection();// mo ket noi voi sql
			ps = conn.prepareStatement(query);
			ps.setInt(1, uid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order o = new Order(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
				o.setName(rs.getString(6));
				o.setPhone(rs.getString(7));
				o.setAddress(rs.getString(8));
				list.add(o);
			}
		} catch (Exception e) {
		}
		return list;
	}

	public void changeStatusOrderByID(int code) {
		String query = "update Orders set status = -1 where id = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, code);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public void changeStatusOrderByIDForAdmin(int code) {
		String query = "update Orders set status = 1 where id = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, code);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public void restoreOrderByID(int code) {
		String query = "update Orders set status = 0 where id = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, code);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public void detleteOrderHistory(int code) {
		String query = "delete Orders  where id = ?";
		try {
			conn = new SQLConnection().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, code);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

	public void saveFile(String upLoadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path path = Paths.get(upLoadDir);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = path.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {

			throw new IOException("khong tim thay file");
		}
	}

	public static void main(String[] args) {
		DAO dao = new DAO();

		dao.deleteBookById(116);
	}
}
