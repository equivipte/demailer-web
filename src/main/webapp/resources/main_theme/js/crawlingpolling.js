
var allow = true;
var startUrl;
var pollUrl;
var siteUrl;
var crawlingStatusUrl;

function Poll() {

	this.start = function start(start, poll, url, crawlingStatus) {
		allow = true;
		startUrl = start;
		pollUrl = poll;
		siteUrl = url;
		crawlingStatusUrl = crawlingStatus;
		
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
			getUpdateCrawlerStatus();
		});
		
		function getUpdateCrawlerStatus() {
			console.log("Get update crawling status");
			
			var requestComplete = $.ajax({
				url : crawlingStatusUrl,
				type : "get",
			});
			
			requestComplete.done(function(message) {
				console.log("Received status");
				
				var status = message.status;
				
				if(status) {
					allow = false;
					$("#progress").removeClass("show");
					$("#progress").addClass("hide");
					$("#url").prop('disabled', false);
					$("#crawlingsearch").toggleClass("hide");
					$("#crawlingcancel").toggleClass("hide");
				} else {
					allow = true;
				}
			});
			
			requestComplete.fail(function(jqXHR, textStatus, errorThrown) {
				console.log("Polling - the following error occured: " + textStatus, errorThrown);
			});
			
		};

	};	
};