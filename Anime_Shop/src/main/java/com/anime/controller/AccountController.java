package com.anime.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anime.constants.HashPasswordConstant;
import com.anime.constants.OrderStatusConstant;
import com.anime.jpa.entity.Order;
import com.anime.jpa.entity.OrderDetail;
import com.anime.jpa.entity.Role;
import com.anime.jpa.entity.User;
import com.anime.jpa.entity.UserRole;
import com.anime.jpa.service.MailService;
import com.anime.jpa.service.OrderDetailService;
import com.anime.jpa.service.OrderService;
import com.anime.jpa.service.RoleService;
import com.anime.jpa.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/anime-shop")
@Slf4j
public class AccountController {

	private static final String FOLDER_IMAGE = "assets/images/";

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@Autowired
	private ServletContext application;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private RoleService roleService;

	@GetMapping("/login")
	public String doShowLogin() {
		return "/user/login";
	}

	@GetMapping("/login/error")
	public String doLoginError(Model model) {
		model.addAttribute("message", "Tên người dùng hoặc mật khẩu không chính xác!");
		log.error("Username Or Password Invalid!");
		return "/user/login";
	}

	@GetMapping("/signup")
	public String doShowSignUp() {
		return "/user/signup";
	}

	@RequestMapping("/oauth2/login/success")
	public String oauth2LoginSuccess(OAuth2AuthenticationToken auth) {
		OAuth2User socialUser = auth.getPrincipal();
		String email = socialUser.getAttributes().get("email").toString();
		try {
			User account = userService.findByUsernameOrEmail(email);
			userService.setAccount(account);
			Role role = roleService.findById((long) 3);
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(account);
			userRole.setIsActive(true);
			userRole.setCreateDate(new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			User account = userService.createFromSocial(socialUser);
			userService.setAccount(account);
		}
		return "forward:/anime-shop/index";
	}

	@PostMapping("/signup")
	public String postShowSignUp(@Valid @ModelAttribute("user") User user, Errors errors,
			@RequestParam(value = "password2", required = false) String pw2,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile, Model model) {
		try {
			if (errors.hasErrors()) {
				return "/user/signup";
			} else {
				if (imageFile.getSize() == 0) {
					if (validate(model, user) == true) {
						if (!user.getPassword().equals(pw2)) {
							model.addAttribute("comfirmpassword", "2 mật khẩu không giống nhau!");
							return "user/signup";
						}
						if (userService.findByUsername(user.getUsername()) != null) {
							model.addAttribute("fieldUsername", "Tên đăng nhập đã tồn tại!");
							return "user/signup";
						}
						if (userService.findByEmail(user.getEmail()) != null) {
							model.addAttribute("fieldEmail", "Email đã tồn tại!");
							return "user/signup";
						}
						user.setPassword(HashPasswordConstant.ENCODER.encode(user.getPassword()));
						user.setAvatar("default.jpg");
						userService.doRegister(user);
						return "redirect:/anime-shop/login";
					}
				} else {
					if (validate(model, user)) {
						if (!user.getPassword().equals(pw2)) {
							model.addAttribute("comfirmpassword", "2 mật khẩu không giống nhau!");
							return "user/signup";
						}
						if (userService.findByUsername(user.getUsername()) != null) {
							model.addAttribute("fieldUsername", "Tên đăng nhập đã tồn tại!");
							return "user/signup";
						}
						if (userService.findByEmail(user.getEmail()) != null) {
							model.addAttribute("fieldEmail", "Email đã tồn tại!");
							return "user/signup";
						}
						String path = application.getRealPath("/");
						user.setPassword(HashPasswordConstant.ENCODER.encode(user.getPassword()));
						user.setAvatar(imageFile.getOriginalFilename());
						String filepath = path + FOLDER_IMAGE + user.getAvatar();
						File file = new File(filepath);
						imageFile.transferTo(file);
						userService.doRegister(user);
						return "redirect:/anime-shop/login";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/user/signup";
	}

	@GetMapping("/forgotpassword")
	public String doShowForgotPassword() {
		return "/user/forgotpassword";
	}

	@PostMapping("/forgotpassword")
	public String doForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
		try {
			User userResp = userService.findByEmail(email);
			if (ObjectUtils.isEmpty(userResp)) {
				redirectAttributes.addFlashAttribute("error", "Email không tồn tại!");
			} else {
				Random random = new Random();
				Integer rdPassoword = random.nextInt(999999);
				String newPassword = String.valueOf(rdPassoword);
				userResp.setPassword(HashPasswordConstant.ENCODER.encode(newPassword));

				userService.updatePassword(userResp.getPassword(), userResp.getId());

				mailService.send(email, "Mật Khẩu Mới",
						"<html><body><div style='margin-left: 22%;'><div class='card' style='width: 600px;'><div style='background-color: #252f3d; height: 50px; border-top-left-radius: 10px; border-top-right-radius: 10px;'><h2 style='color: white; text-align: center; padding-top: 10px;;'>Anime Store</h2></div><div class='card-body' style='border: 1px solid rgb(237, 236, 236);'><b style='font-size: 20px; padding-left: 15px;'>Được xác nhận từ địa chỉ email của bạn</b><p style='padding-top: 10px; padding-left: 15px; padding-right: 15px;'>Đây là mật khẩu mới sẽ được sử dụng trong suốt quá trình sử dụng hệ thống và mua sắm của chúng tôi. Vui lòng không được cung cấp mật khẩu hoặc văn bản này ra bên ngoài. Cảm ơn quý khách!</p><div style='text-align: center; padding-bottom: 15px;'><b>Mật khẩu mới</b><h1 style='font-weight: 900; color: red;'>"
								+ rdPassoword
								+ "</h1></div><hr /><div style='margin-bottom: 10px;'><b style='padding-left: 15px; margin-bottom: 10px;'>Bạn muốn đăng nhập? <a href='http://localhost:8080/anime-shop/login'>Đăng nhập tại đây</a></b></div></div></div></div></body></html>");
				redirectAttributes.addFlashAttribute("message", "Vui lòng kiểm tra email của bạn!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/anime-shop/forgotpassword";
	}

	public boolean validate(Model model, @Valid @ModelAttribute("user") User user) {
		if (user.getUsername().length() == 0) {
			model.addAttribute("fieldUsername", "Tên đăng nhập không được bỏ trống!");
			return false;
		}
		if (user.getFullname().length() < 4 || user.getFullname().length() > 20) {
			model.addAttribute("fieldFullname", "Họ tên phải từ 5 đến 20 kí tự!");
			return false;
		}
		if (user.getEmail().length() == 0) {
			model.addAttribute("fieldEmail", "Email không được bỏ trống!");
			return false;
		}
		if (user.getPassword().length() < 4 || user.getFullname().length() > 20) {
			model.addAttribute("fieldPassword", "Mật khẩu phải từ 5 đến 20 kí tự!");
			return false;
		}
		return true;
	}

	@GetMapping("/my-order")
	public String doShowOrders(HttpServletRequest request, Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "6") int pageSize) {
		List<Order> ordersNew = new ArrayList<>();
		List<Order> ordersShipping = new ArrayList<>();
		List<Order> ordersSuccess = new ArrayList<>();
		List<Order> ordersCancel = new ArrayList<>();

		try {
			String username = request.getRemoteUser();
			Page<Order> pageOrdersNew = orderService.findOrdersByOrderStatusAndUsername(OrderStatusConstant.NEW,
					username, PageRequest.of(pageNum - 1, pageSize));
			Page<Order> pageOrdersShipping = orderService.findOrdersByOrderStatusAndUsername(
					OrderStatusConstant.SHIPPING, username, PageRequest.of(pageNum - 1, pageSize));
			Page<Order> pageOrdersSuccess = orderService.findOrdersByOrderStatusAndUsername(OrderStatusConstant.SUCCESS,
					username, PageRequest.of(pageNum - 1, pageSize));
			Page<Order> pageOrdersCancel = orderService.findOrdersByOrderStatusAndUsername(OrderStatusConstant.CANCEL,
					username, PageRequest.of(pageNum - 1, pageSize));

			ordersNew = pageOrdersNew.getContent();
			ordersShipping = pageOrdersShipping.getContent();
			ordersSuccess = pageOrdersSuccess.getContent();
			ordersCancel = pageOrdersCancel.getContent();

			model.addAttribute("totalPagesNew", pageOrdersNew.getTotalPages());
			model.addAttribute("totalPagesShipping", pageOrdersShipping.getTotalPages());
			model.addAttribute("totalPagesSuccess", pageOrdersSuccess.getTotalPages());
			model.addAttribute("totalPagesCancel", pageOrdersCancel.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("ordersNew", ordersNew);
		model.addAttribute("ordersShipping", ordersShipping);
		model.addAttribute("ordersSuccess", ordersSuccess);
		model.addAttribute("ordersCancel", ordersCancel);
		return "/user/myOrder";
	}

	@GetMapping("/order-detail/{id}")
	public String doGetOrderDetail(Model model, @PathVariable("id") Long id,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "6") int pageSize) {
		List<OrderDetail> orderDetails = new ArrayList<>();
		try {
			Page<OrderDetail> pageOrderDetails = orderDetailService.findByOrderId(id,
					PageRequest.of(pageNum - 1, pageSize));
			orderDetails = pageOrderDetails.getContent();
			model.addAttribute("totalPages", pageOrderDetails.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("orderDetails", orderDetails);
		return "/user/orderDetail";
	}
}
