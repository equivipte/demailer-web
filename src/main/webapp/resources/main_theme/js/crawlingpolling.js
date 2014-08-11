
var allow = true;
var allowScanning = true;
var startUrl;
var pollUrl;
var siteUrl;
var scanningUrl;
var crawlingStatusUrl;

function Poll() {

	this.start = function start(start, poll, url, scanning, crawlingStatus) {
		allow = true;
		startUrl = start;
		pollUrl = poll;
		scanningUrl = scanning;
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
				    if(allowScanning === true) {
				        allowScanning = false;
						getUpdateUrlScanning();
				    }
				}, 500);

				setInterval(function() {
					if (allow === true) {
						allow = false;
						getUpdate();
					}
				}, 1000);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("Start - the following error occured: " + textStatus, errorThrown);
			}
		});
	};

	function getUpdateUrlScanning() {
	    console.log("Get url scanning...");

	    if (requestScanning) {
            requestScanning.abort();	// abort any pending request
        }

        var requestScanning = $.ajax({
            url : scanningUrl,
            type : "get",
        });

        requestScanning.done(function(message) {
            console.log("Received a scanning url message");

            $('.scanning span').text(message.url);
        });

        requestScanning.fail(function(jqXHR, textStatus, errorThrown) {
            console.log("Request scanning - the following error occured: " + textStatus, errorThrown);
        });

        requestScanning.always(function() {
            allowScanning = true;
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
	};

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