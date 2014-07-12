
var allow = true;
var startUrl;
var pollUrl;
var siteUrl;

function Poll() {

	this.start = function start(start, poll, url) {
		startUrl = start;
		pollUrl = poll;
		siteUrl = url;
		
		$.ajax({
			url : startUrl,
			type : "POST",
			data : "{\"site\" : " + "\"" + siteUrl + "\"}",
			contentType: 'application/json',
			success: function() {
				console.log("Crawling start...");
				setInterval(function() {
					if (allow === true) {
						allow = false;
						getUpdate();
					}
				}, 500);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("Start - the following error occured: " + textStatus, errorThrown);
			}
		});
	};
	
	function getUpdate() {
		
		console.log("Get update...");
																	
		if (request) {
			request.abort();	// abort any pending request
		}
		
		var request = $.ajax({
			url : pollUrl,
			type : "get",
		});

		request.done(function(message) {
			console.log("Received a message");
			
			var update = getUpdate(message);
			$('#emailTable > tbody:last').append(update);
		});
		
		function getUpdate(message) {

			var update = "<tr>" + 
							"<td>" + message.email + "</td>" + 
						 "</tr>";
			
			return update;
		};
		

		request.fail(function(jqXHR, textStatus, errorThrown) {
			console.log("Polling - the following error occured: " + textStatus, errorThrown);
		});

		request.always(function() {
			allow = true;
			
			index++;
		});
	};	
};