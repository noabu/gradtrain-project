var MainScript = function() {	

/*	var loadPage = function() { 
        $("#cmdOK_1").click(clickButton);
		console.log("here");
		$("#cmd_0").click(click1);
		
	};*/
	/*var createHtml = function(){
		var ht = "<div id='partA'><img src='GradTrain.png' /><header><h1>Who Are You</h1><h2>People search engine</h2></header><div id ='main'><img src='search.png' /><input id='txtInput' placeholder=' name..'/><button id='cmdOK_1'>search</button></div></div><div id='background'><div id = 'partC'><h1>Possible people: </h1><div id='place'></div></div> </div>"
		return ht;
	};*/
		
	var clickButton = function() {
		var input=$("#txtInput").val();
		console.log(input);

	/* set up XMLHttpRequest */
	var url = "TestExcel.xlsx";
	var oReq = new XMLHttpRequest();
	oReq.open("GET", url, true);
	oReq.responseType = "arraybuffer";

	oReq.onload = function(e) {
	  var arraybuffer = oReq.response;

	  /* convert data to binary string */
	  var data = new Uint8Array(arraybuffer);
	  var arr = new Array();
	  for(var i = 0; i != data.length; ++i) arr[i] = String.fromCharCode(data[i]);
	  var bstr = arr.join("");

	  /* Call XLSX */
	  var workbook = XLSX.read(bstr, {type:"binary"});

	  /* DO SOMETHING WITH workbook HERE */
	  var first_sheet_name = workbook.SheetNames[0];
	  /* Get worksheet */
	  var worksheet = workbook.Sheets[first_sheet_name];
	  var table = XLSX.utils.sheet_to_json(worksheet,{raw:true});
	  var matchingResults = table.filter(function(x){ return x.Name == input; });	  
	  var t;
	  if (matchingResults.length == 1)
		  t="<p><span>There is " +matchingResults.length.toString() + " person who is found as possible</span></p>"
	  else
		  t="<p><span>There are "+matchingResults.length.toString()+" people who are found to be possible: </span></p>"
	  var tableTxt=t+"<table><thead><th>Name</th><th>Study</th><th>Work</th><th>Live</th><th>Edit Details</th></thead><tbody>";
	  var row,col;
	  for(row=0;row<matchingResults.length;row++){
		tableTxt=tableTxt+"<tr id='tr_"+row.toString()+"'>";
		for (col=0;col<5;col++){ 
			tableTxt=tableTxt+"<td></td>";				
		}
		tableTxt=tableTxt+"</tr>";
	  }
	  tableTxt=tableTxt+"</tbody></table><div id='in'><p>* The results were taken from an automatic examination carried out as part of a final project at Azrieli College</p></div>"
	  $("#place").html(tableTxt);
	  var i=1; var j=0;
	  $("td").each(function() {
		  if(i%5==1){
			$(this).html(matchingResults[j].Name); //add the name to the first col
		  }
		  else if(i%5==2)
			$(this).html(matchingResults[j].Study); //add the study year to the seconed col
		  else if(i%5==3)
			$(this).html(matchingResults[j].Work); //add the work place to the 3 col.
		  else if(i%5==4)
			$(this).html(matchingResults[j].Live);
		  else{  
			$(this).html("<input class=cb id='cmd_"+j.toString()+"' type='button' value='Edit' onclick='doFunction(this);' />"); 
			//$(this).html("<button class='cb' id='cmd_"+j.toString()+"'>edit</button>"); 
			j++; //the next people
		  }	  
		i++; //the next col
	});
	}
	oReq.send();
	MainScript.initModule($("#contain"));
	};

    var stateMap = { $container : null };	
	var initModule = function($container) {
		stateMap.$container = $container;
		$("#cmdOK_1").click(clickButton);
    };
	
	return{initModule:initModule};	
}();

function doFunction(elem) {
	 newEvent.setEvent(elem.id);
}


$(document).ready(function() {MainScript.initModule($("#contain"));});