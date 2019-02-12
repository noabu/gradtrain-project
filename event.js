
var newEvent = function() {	
    var setEvent = function(id) {
		num=id.substring(4);
		$("#contain").append("<div id ='event'></div>");
		$("#event").html(createEvent());
		$("#OK").click(function(){
			var name= document.getElementById("i1").value;
			var study= document.getElementById("i2").value;
			var work= document.getElementById("i3").value;
			var live =document.getElementById("i4").value;
			$("#event").remove();
			change(name, study, work, live, num);
			
		});
    };

	var createEvent = function(){
		messege="<h1>Edit Details </h1>"+"<hr/>"+"   Name: <input id='i1' type='text' size='15'>"+"&nbsp"+
		"Study: <input id='i2' type='text' size='15'>"+"</br>"+"</br>"+"Work: <input id='i3' type='text' size='15'>   Live: <input id='i4' type='text' size='15'><br>"+"</br>"+"<button type='button' id='OK'>"
		+"OK"+"</button>"
		return messege;
	};
	
	var change = function(name, study, work, live, id){
		var Excel = require('exceljs');
		var workbook = new Excel.Workbook();

		workbook.xlsx.readFile('TestExcel.xlsx')
			.then(function() {
				var worksheet = workbook.getWorksheet(1);
				var row = worksheet.getRow(5);
				row.getCell(1).value = 5; // A5's value set to 5
				row.commit();
				return workbook.xlsx.writeFile('new.xlsx');
			})
		var tr_id="#tr_"+id;
		var i=1;
		$(tr_id).find('td').each(function() {
			if(i%5==1){
				if (! name.length == 0)
					$(this).html(name); //add the name to the first col
			}
			else if(i%5==2){
				if (! study.length == 0)
					$(this).html(study); //add the study year to the seconed col
			}
			else if(i%5==3){
				if (! work.length == 0)
					$(this).html(work); //add the work place to the 3 col.
			}
			else if(i%5==4){
				if (! live == 0)
					$(this).html(live);
			}
			else{  
				console.log("finish");
			}	  
			i++; //the next col
		});
	};
	
    return { setEvent : setEvent };
}();
