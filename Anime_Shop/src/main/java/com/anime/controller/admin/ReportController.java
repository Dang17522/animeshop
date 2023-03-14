package com.anime.controller.admin;

import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anime.jpa.entity.UserReport;
import com.anime.jpa.entity.hangnam;
import com.anime.jpa.entity.hangngay;
import com.anime.jpa.repo.OrderRepo;
import com.anime.jpa.repo.ReportReponsitory;
import com.anime.jpa.repo.UserRepo;

@Controller
@RequestMapping("/anime-shop/admin")
public class ReportController {
	@Autowired
	ReportReponsitory reponsitory;

	@Autowired
	UserRepo userReponsitory;

	@Autowired
	OrderRepo orderRepo;

	@SuppressWarnings("deprecation")
	@RequestMapping("/report/today")
	public String re1(Model model) {
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
		System.out.println(orderRepo.getById((long) 1).getCreateDate());
		return "admin/report/hn";
	}

	@RequestMapping("/report/month")
	public String month(Model model, @RequestParam(value = "month", defaultValue = "2") int month) {
		Map<Integer, Double> surveyMap = new LinkedHashMap<>();
		List<hangnam> list = reponsitory.reportthang(month);
		for (int i = 0; i < list.size(); i++) {
			surveyMap.put(list.get(i).getDate(), list.get(i).getSum());
			System.out.println(list.get(i).getDate());

		}
		model.addAttribute("month", month);
		model.addAttribute("sum", reponsitory.reporttongthang(month));
		model.addAttribute("surveyMap", surveyMap);
		return "admin/report/line";
	}

	@RequestMapping("/report/year")
	public String line(Model model, @RequestParam(value = "year", defaultValue = "2022") int year) {
		Map<Integer, Double> surveyMap = new LinkedHashMap<>();
		List<hangnam> list = reponsitory.reportnam(year);
		for (int i = 0; i < list.size(); i++) {
			surveyMap.put(list.get(i).getDate(), list.get(i).getSum());

		}
		model.addAttribute("year", year);
		model.addAttribute("sum", reponsitory.reporttongnam(year));
		model.addAttribute("surveyMap", surveyMap);
		return "admin/report/reportyear";
	}

	@RequestMapping("/report/user")
	public String report(Model model) {
		Map<String, Long> surveyMap = new LinkedHashMap<>();
		List<UserReport> list = userReponsitory.load();
		for (int i = 0; i < list.size(); i++) {
			surveyMap.put(list.get(i).getFullname(), list.get(i).getCount());

		}
		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("report", userReponsitory.load());
		return "admin/report/user_report";
	}
}
