package com.anime.controller.admin;

import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anime.jpa.entity.hangngay;
import com.anime.jpa.repo.OrderRepo;
import com.anime.jpa.repo.ReportReponsitory;
import com.anime.jpa.repo.UserRepo;

@Controller
@RequestMapping("/anime-shop/admin")
public class AdminHomeController {
	@Autowired
	ReportReponsitory reponsitory;
	@Autowired
	UserRepo userReponsitory;

	@Autowired
	OrderRepo orderRepo;

	@GetMapping("/home-index")
	public String doShowHomeIndex(Model model) {
		Map<String, Double> surveyMap = new LinkedHashMap<>();
		List<hangngay> list = reponsitory.reportngay(
				Date.from(java.time.LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		for (int i = 0; i < list.size(); i++) {
			surveyMap.put(list.get(i).getName(), list.get(i).getSum());

		}
		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("report", reponsitory.reportngay(
				Date.from(java.time.LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		model.addAttribute("ngay", java.time.LocalDate.now());
		model.addAttribute("sum", reponsitory.reporttongngay(
				Date.from(java.time.LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
		
		
		return "admin/index";
	}
}
