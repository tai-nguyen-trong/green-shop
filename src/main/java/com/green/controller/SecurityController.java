//package com.green.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.green.dao.AccountDAO;
//import com.green.entity.Account;
//
//import javassist.expr.NewArray;
//
//@Controller
////@RequestMapping("security")
//public class SecurityController {
//	//them để dùng đăng kí
//	@Autowired
//	AccountDAO dao;
//	
//	
//	@RequestMapping("/security/login/form")
//	public String loginForm(Model model) {
//		model.addAttribute("message", "Vui lòng đăng nhập!");
//		return "security/login";
//	}
//	
//	@RequestMapping("/security/login/success")
//	public String loginSuccess(Model model) {
//		model.addAttribute("message", "Đăng nhập thành công!");
//		return "security/login";
//	}
//	
//	@RequestMapping("/security/login/error")
//	public String loginError(Model model) {
//		model.addAttribute("message", "Sai thông tin đăng nhập!");
//		return "security/login";
//	}
//	
//	@RequestMapping("/security/unauthorized")
//	public String unauthorized(Model model) {
//		model.addAttribute("message", "Không có quyền truy xuất!");
//		return "security/login";
//	}
//	
//	@RequestMapping("/security/logoff/success")
//	public String logoffSuccess(Model model) {
//		model.addAttribute("message", "Bạn đã đăng xuất!");
//		return "security/login";
//	}
//	
//	
//	
//	//dang ky
//	@RequestMapping("/security/create/form")
//	public String createaccount(Model model) {
//		model.addAttribute("message", "Vui lòng đăng ký!");
//		return "security/create";
//	}
//	
//	//vld code
//	@RequestMapping("/security/vldcode/form")
//	public String vldcode(Model model) {
//		model.addAttribute("message", "Vui lòng nhập mã code!");
//		return "security/vldcode";
//	}
//	
//	
//	
//	
//	//test dang ki
//	@GetMapping("/security/register")
//	public String showSignForm(Model model) {
//		model.addAttribute("user", new Account());
//		return "security/signup_form";
//	}
//	
//	@PostMapping("/security/register/success")
//	public String dangKiThanhCong(Account account) {
//		dao.save(account);
//		return "security/login";
//	}
//}
//










package com.green.controller;

import java.util.Optional;

import javax.mail.Message.RecipientType;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.dao.AccountDAO;
import com.green.entity.Account;
import com.green.service.MailService;
import com.green.service.ParamService;
import com.green.service.SessionService;
import com.green.util.HTMLUtil;
import com.green.util.Random;

@Controller
@RequestMapping("/security")
public class SecurityController {
	//them để dùng đăng kí
	@Autowired AccountDAO dao;
	
	//Thêm để sử dụng chức năng đổi mk
	@Autowired
	SessionService sessionservice;
	@Autowired
	ParamService paramService;
	
	private @Autowired MailService send;
	
	@RequestMapping("/login/form")
	public String loginForm(Model model) {
		model.addAttribute("message", "Vui lòng đăng nhập!");
		return "security/login";
	}
	
	@RequestMapping("/login/success")
	public String loginSuccess(Model model) {
		model.addAttribute("message", "Đăng nhập thành công!");
		return "/layout/index";
	}
	
	@RequestMapping("/login/error")
	public String loginError(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập!");
		return "security/login";
	}
	
	@RequestMapping("/unauthorized")
	public String unauthorized(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất!");
		return "security/login";
	}
	
	@RequestMapping("/logoff/success")
	public String logoffSuccessf(Model model) {
		model.addAttribute("message", "Bạn đã đăng xuất!");
		return "security/login";
	}

	@GetMapping("/forgot-pass") String getForm(Model m) {
		m.addAttribute("message", "<div class='text-primary'>Nhập email đã đăng kí tài khoản người dùng<div>");
		return "security/forgotPass";
	}
	
	// # sài cookie dể không bị ghi đè khi 2 tài khoản quên mk cùng lúc + cookie cho phép lưu trữ trong khoảng thời gian
	private String code;  // nếu sài cookie thì bỏ biến này đi
	@ResponseBody @PostMapping("/forgot-pass") 
	public ResponseEntity<Object> forgotPass(@RequestParam(required = false) String mail, HttpServletResponse res) throws Exception {
		Account a = null;
		
		if((mail+"").isEmpty()) throw new Exception("Chưa nhập email lấy tài khoản!");
		else if((a=dao.GetByMail(mail))!=null) {
code = Random.UppAndLow("GreenStore_", 30); // nếu sài cookie thì setCode này vào cookie
			String subject = "Quên mật khẩu GreenStore";
			
			// Phần này đang làm
			Cookie c = new Cookie(code, a.getUsername());
			c.setMaxAge(180);
			res.addCookie(c);

			// # Nếu sài cookie thì set cookie có 1 khóa random + gửi vào email
			// # lấy khóa đó tìm trong cookie + username sau đó dăng nhập rồi chuyển tới trang đổi mk
			String text = HTMLUtil.forGotPass(a.getUsername(), code, null);
			this.send.sendMimeMessage(subject, text, null, RecipientType.CC, mail);
			return ResponseEntity.ok("{\"mes\": \"Một xác thực được gửi tới email "+mail+"\"}");
		}
		else throw new Exception("Email chưa được đăng ký hệ thống!");
	}
	
	@GetMapping("/forget-pass")
	public String forgetPass(
		@RequestParam(required = false) String username,
		@RequestParam(required = false) String code,
		HttpServletRequest req, Model model
	) throws Exception{
		// # Sài cookie sửa luôn phần này
		// nếu đúng password coi như đã xác thực thành công => chuyển tragn thay đổi mật khẩu
		if(code.equals(this.code)) {
			Optional<Account> o = dao.findById(username);
			if(o.isEmpty()) throw new Exception(username+" không tồn tại!!!");
			// request gửi tới nếu đúng là code mà Server random thì cho phép đổi mật khẩu
			req.login(username, o.get().getPassword()); // đăng nhập tài khoản
			return this.changepassword(model, req); // chuyển trang thay đổi mật khẩu
		} else throw new Exception(code+" không đúng mã được server random!!!\n"+this.code);
	}
	
	// dang ki
	@GetMapping("/register")
	public String showSignForm(Model model) {
		// tạp mới account nhưng chưa có dữ liệu set attribute có khóa là 'user'
		model.addAttribute("user", new Account());
		return "security/signup_form";
	}
	
	@PostMapping("/register/success")
	public String registerAccount(Model m, Account account) {
		// acccount sau khi thay đổi thông tin được sửa trên giao diện
		Optional<Account> o = dao.findById(account.getUsername());
		if(o.isPresent()) {
			m.addAttribute("message", String.format("<div class='text-warning'>Tên đăng nhập <b>%s</b> đã tồn tại!!!<div>", account.getUsername()));
			m.addAttribute("user", account);
			return "security/signup_form";
		}
		try {
			dao.save(account);
			m.addAttribute("message", String.format("<div class='text-success'>Đăng ký tài khoản <b>%s</b> thành công<div>", account.getUsername()));
			return "security/login";
		} catch (IllegalArgumentException e) {
			m.addAttribute("message", "<div class='text-danger'>Lỗi đăng ký tài khoản!<div>");
			e.printStackTrace();
			return "security/signup_form";
		}
	}
	
	
	
	//Đổi mật khẩu
	@GetMapping("/changepassword")
	public String changepassword(Model model, HttpServletRequest req) {
return "security/changePassword";
	}
	
	@PostMapping("/changepassword")
	public String changepassword(HttpServletRequest req) {
		String newPass = req.getParameter("newpassword");
		String cfPass = req.getParameter("cfpassword");
		
		// kiểm tra mật khẩu mới và xác nhận mk
		if(newPass.isEmpty() || cfPass.isEmpty()) req.setAttribute("message", "Không chấp nhận mật khẩu rỗng!");
		else if(newPass.equals(cfPass)) {
			// Lấy tài khoản trong csdl với username đang đăng nhập hệ thống
			Optional<Account> o = dao.findById(req.getRemoteUser());
			// nếu tài khoản tồn tại trong database > thực hiện đỏi mk
			// Kiểm tra cái này có thể đăng nhập oauth2 nếu ko có trong database update sẽ phát sinh lỗi
			if(o.isPresent()) {
				o.get().setPassword(newPass); // đổi mk > lưu cập nhật tài khoản
				if(dao.save(o.get()) != null)req.setAttribute("message", o.get().getUsername()+" đã cập nhật mật khẩu");
			} else System.err.println("Không tìm thấy username"); 
		} else req.setAttribute("message", "Xác Nhận Mật Khẩu Mới Không Đúng");
		
		return "security/changePassword";
	}
}
