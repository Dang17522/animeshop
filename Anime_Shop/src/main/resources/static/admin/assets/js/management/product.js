let currentPage = 0;
let pageSize = 9;
let lastPage = 0;
let imageUpload;

const getAll = () => {
	let pageProducts = [];
	$.ajax({
		type: "GET",
		url: "/api/products",
		data: {
			page: currentPage,
			size: pageSize
		},
		success: function(resp) {
			pageProducts = resp.content;
			console.log(pageProducts);
			lastPage = resp.totalPages - 1;
			$("#dataProduct").html(pageProducts.map(function(item) {
				return `
					<tr>
					<td>${item.id}</td>
					<td class="truncate-name">${item.name}</td>
					<td>${item.quantity}</td>
					<td>${formatCurrency(item.price)}</td>
					<td>${item.selled}</td>
					<td>${item.discount * 100}%</td>
					<td>${item.nameMain}</td>
					<td>${item.material}</td>
					<td>${item.series}</td>
					<td>${item.status}</td>
					<td>${item.view}</td>
					<td>${item.size}</td>
					<td>${item.category.name}</td>
					<td>${item.unitType.name}</td>
					<td>${item.createdBy ? item.createdBy : 'Trống'}</td>
					<td>${item.updatedBy ? item.updatedBy : 'Trống'}</td>
					<td>${item.createDate ? item.createDate : 'Trống'}</td>
					<td>${item.updateDate ? item.updateDate : 'Trống'}</td>
					<td>
						<a class='mr-4'> <span class='fas fa-pencil-alt text-warning' data-bs-toggle="modal"
											data-bs-target="#editModal" onclick="doEdit(${item.id})"></span> </a> 
						<a class='mr-4 delet'> <span class='fas fa-trash-alt text-danger' onclick="doDelete(${item.id})"></span> </a>
					</td>
					</tr>
					`
			}))
		}
	});
}

const btn_first = () => {
	currentPage = 0;
	getAll();
}

const btn_previous = () => {
	currentPage--;
	getAll();
}

const btn_next = () => {
	if (currentPage >= lastPage) {
		return;
	}
	currentPage++;
	getAll();
}

const btn_last = () => {
	currentPage = lastPage;
	getAll();
}

const doSearch = () => {
	let keyword = $('#keyword_search').val();
	let pageProducts = [];
	$.ajax({
		type: "GET",
		url: "/api/products/search?keyword=" + keyword,
		data: {
			page: currentPage,
			size: pageSize
		},
		success: function(resp) {
			pageProducts = resp.content;
			console.log(pageProducts);
			lastPage = resp.totalPages - 1;
			$("#dataProduct").html(pageProducts.map(function(item) {
				return `
					<tr>
					<td>${item.id}</td>
					<td class="truncate-name">${item.name}</td>
					<td>${item.quantity}</td>
					<td>${formatCurrency(item.price)}</td>
					<td>${item.selled}</td>
					<td>
						<a class='mr-4'> <span class='fas fa-pencil-alt text-warning' data-bs-toggle="modal"
											data-bs-target="#editModal" onclick="doEdit(${item.id})"></span> </a> 
						<a class='mr-4 delet'> <span class='fas fa-trash-alt text-danger' onclick="doDelete(${item.id})"></span> </a>
					</td>
					</tr>
					`
			}))
		}
	});
}

const doCreate = () => {
	let name = $("#name").val();
	let quantity = $("#quantity").val();
	let price = $("#price").val();
	let slug = $("#slug").val();
	let discount = $("#discount").val();
	let nameMain = $("#name_main").val();
	let material = $("#material").val();
	let series = $("#series").val();
	let status = $("#status").val();
	let size = $("#size").val();
	let category = $("#category").val();
	let unitType = $("#unit-type").val();
	let description = $("#description").val();
	let isActive = document.querySelector('input[name="isActive"]:checked').value;
	
	if (name.length === 0) {
		$("#name_error").text("Tên không được để trống!");
		return;
	} else {
		$("#name_error").text("");
	}
	if (quantity.length === 0) {
		$("#quantity_error").text("Số lượng không được bỏ trống!");
		return;
	} else {
		$("#quantity_error").text("");
	}
	if (slug.length === 0) {
		$("#slug_error").text("Mã liên kết không được bỏ trống!");
		return;
	} else {
		$("#slug_error").text("");
	}
	if (price.length === 0) {
		$("#price_error").text("Mã liên kết không được bỏ trống!");
		return;
	} else {
		$("#price_error").text("");
	}
	if (nameMain.length === 0) {
		$("#name_main_error").text("Tên nhân vật không được bỏ trống!");
		return;
	} else {
		$("#name_main_error").text("");
	}
	if (material.length === 0) {
		$("#material_error").text("Chất liệu không được bỏ trống!");
		return;
	} else {
		$("#material_error").text("");
	}
	if (series.length === 0) {
		$("#series_error").text("Seri không được bỏ trống!");
		return;
	} else {
		$("#series_error").text("");
	}
	if (status.length === 0) {
		$("#status_error").text("Tình trạng không được bỏ trống!");
		return;
	} else {
		$("#status_error").text("");
	}
	if (size.length === 0) {
		$("#size_error").text("Kích thước không được bỏ trống!");
		return;
	} else {
		$("#size_error").text("");
	}
	
	let Product = {
		name: name,
		quantity: quantity,
		price: price,
		slug: slug,
		discount: discount,
		size: size,
		nameMain: nameMain,
		material: material,
		series: series,
		status: status,
		category: {
			id: category
		},
		unitType: {
			id: unitType
		},
		description: description,
		isActive: isActive,
		
	}
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/api/categorys",
		data: JSON.stringify(Product),
		dataType: 'json',
		success: function() {
			$("#name").val("");
			document.querySelector('input[name="isActive"]').checked = true;
			Swal.fire("Hoàn thành", "Thêm mới thành công !", "success");
			$("#addModal").modal("hide");
			getAll();
		},
		error: function(e) {
			console.log("ERROR : ", e);
			Swal.fire("Thất bại", "Thêm mới thất bại !", "error");
		}
	});
}

function uploadFile() {
	let fileInput = document.getElementById("fileInput");
	let file = fileInput.files[0];
	let formData = new FormData();
	formData.append("file", file);

	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "/api/uploads/images",
		data: formData,
		processData: false,
		contentType: false,
		cache: false,
		success: function(data) {
			imageUpload = data.name;
			console.log(imageUpload);
		},
		error: function(e) {
			console.log("ERROR : ", e);
		}
	});
}

const formatCurrency = (x) => {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + '(nghìn) VNĐ';
}