package com.anime.jpa.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anime.jpa.entity.Order;
import com.anime.jpa.entity.hangnam;
import com.anime.jpa.entity.hangngay;
import com.anime.jpa.entity.sumhangngay;

@Repository
public interface ReportReponsitory extends JpaRepository<Order, Long> {
	@Query("select new hangngay(o.product.name, o.order.id, sum(o.price), count(o.quantity)) from OrderDetail o where CONVERT(date, o.order.createDate, 111)  =?1 group by o.product.name,o.order.id")
	List<hangngay> reportngay(Date date);

	@Query("select new sumhangngay(count(o.quantity),sum(o.price)) from OrderDetail o where CONVERT(date, o.order.createDate, 111) =?1 ")
	List<sumhangngay> reporttongngay(Date date);

	@Query("select new hangnam(sum(o.price), count(o.quantity),DAY(o.order.createDate)) from OrderDetail o where MONTH(o.order.createDate) =?1 group by DAY(o.order.createDate)")
	List<hangnam> reportthang(int i);

	@Query("select new sumhangngay(count(od.quantity),sum(od.price)) from OrderDetail od join Order o on od.order.id = o.id where MONTH(o.createDate) = ?1")
	List<sumhangngay> reporttongthang(int i);

	@Query("select new hangnam(sum(o.price), count(o.quantity),MONTH(o.order.createDate)) from OrderDetail o where YEAR(o.order.createDate) =?1 group by MONTH(o.order.createDate)")
	List<hangnam> reportnam(int i);

	@Query("select new sumhangngay(count(o.quantity),sum(o.price)) from OrderDetail o where YEAR(o.order.createDate) =?1 ")
	List<sumhangngay> reporttongnam(int i);
}
