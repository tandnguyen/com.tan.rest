/**
 * js file for post.html
 * Please use modern web browser as this file will not attempt to be
 * compatible with older browsers. Use Chrome and open javascript console
 * or Firefox with developer console.
 * 
 * jquery is required
 */
$(document).ready(function() {
	/*
	var $put_example = $('#put_example')
		, $SET_PC_PARTS_MAKER = $('#SET_PC_PARTS_MAKER')
		, $SET_PC_PARTS_CODE = $('#SET_PC_PARTS_CODE');
	*/
	var $put_example = $('#put_example')
	, $SET_FIRST_NAME = $('#SET_FIRST_NAME')
	, $SET_LAST_NAME = $('#SET_LAST_NAME');	
	getInventory();
	
	$(document.body).on('click', ':button, .UPDATE_BTN', function(e) {
		//console.log(this);
		/*
		var $this = $(this)
			, FIRST_NAME = $this.val()
			, $tr = $this.closest('tr')
			, PC_PARTS_MAKER = $tr.find('.CL_PC_PARTS_MAKER').text()
			, PC_PARTS_CODE = $tr.find('.CL_PC_PARTS_CODE').text()
			, PC_PARTS_TITLE = $tr.find('.CL_PC_PARTS_TITLE').text()
			, PC_PARTS_AVAIL = $tr.find('.CL_PC_PARTS_AVAIL').text()
			, PC_PARTS_DESC = $tr.find('.CL_PC_PARTS_DESC').text();
		*/
		var $this = $(this)
		, $tr = $this.closest('tr')
		, ID = $tr.find('.CL_ID').text()
		, FIRST_NAME = $tr.find('.CL_FIRST_NAME').text()
		, LAST_NAME = $tr.find('.CL_LAST_NAME').text()
		, DEPT = $tr.find('.CL_DEPT').text()
		, TITLE = $tr.find('.CL_TITLE').text()
		, BAND = $tr.find('.CL_BAND').text();
		
		/*
		$('#SET_PC_PARTS_PK').val(PC_PARTS_PK);
		$SET_PC_PARTS_MAKER.text(PC_PARTS_MAKER);
		$SET_PC_PARTS_CODE.text(PC_PARTS_CODE);
		$('#SET_PC_PARTS_TITLE').text(PC_PARTS_TITLE);
		$('#SET_PC_PARTS_AVAIL').val(PC_PARTS_AVAIL);
		$('#SET_PC_PARTS_DESC').text(PC_PARTS_DESC);
		
		$('#update_response').text("");
		*/
		$('#SET_ID').val(ID);
		$SET_FIRST_NAME.text(FIRST_NAME);
		$SET_LAST_NAME.text(LAST_NAME);
		$('#SET_DEPT').text(DEPT);
		$('#SET_TITLE').text(TITLE);
		$('#SET_BAND').val(BAND);
		
		$('#update_response').text("");
	});
	
	$put_example.submit(function(e) {
		e.preventDefault(); //cancel form submit
		
		var obj = $put_example.serializeObject()
			, FIRST_NAME = $SET_FIRST_NAME.text()
			, LAST_NAME = $SET_LAST_NAME.text();
		
		updateInventory(obj, FIRST_NAME, LAST_NAME);
	});
});

function updateInventory(obj, first, last) {
	
	ajaxObj = {  
			type: "PUT",
			url: "http://localhost:8700/com.tan.rest/api/v3/employees/" + first + "/" + last,
			data: JSON.stringify(obj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) {
				//console.log(data);
				$('#update_response').text( data[0].MSG );
			},
			complete: function(XMLHttpRequest) {
				//console.log( XMLHttpRequest.getAllResponseHeaders() );
				getInventory();
			}, 
			dataType: "json" //request JSON
		};
		
	return $.ajax(ajaxObj);
}

function getInventory() {
	
	var d = new Date()
		, n = d.getTime();
	
	ajaxObj = {  
			type: "GET",
			url: "http://localhost:8700/com.tan.rest/api/v1/employees", 
			data: "ts="+n, 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success: function(data) { 
				console.log(data);
				var html_string = "";
				
				$.each(data, function(index1, val1) {
					console.log(val1);
					html_string = html_string + templateGetInventory(val1);
				});
				
				$('#get_inventory').html("<table border='1'>" + html_string + "</table>");
			},
			complete: function(XMLHttpRequest) {
				console.log( XMLHttpRequest.getAllResponseHeaders() );
			}, 
			dataType: "json" //request JSON
		};
		
	return $.ajax(ajaxObj);
}

function templateGetInventory(param) {
	return '<tr>' +
				'<td class="CL_FIRST_NAME">' + param.FIRST_NAME + '</td>' +
				'<td class="CL_LAST_NAME">' + param.LAST_NAME + '</td>' +
				'<td class="CL_DEPT">' + param.DEPT + '</td>' +
				'<td class="CL_TITLE">' + param.TITLE + '</td>' +
				'<td class="CL_BAND">' + param.BAND + '</td>' +
				'<td class="CL_PC_PARTS_BTN"> <button class="UPDATE_BTN" value=" ' + param.ID + ' " type="button">Update</button> </td>' +
			'</tr>';
}
