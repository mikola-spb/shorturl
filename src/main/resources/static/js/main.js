var onFormSubmit = function(targetUrl) {
    $.ajax({
        url: "/registry",
        type: "POST",
        dataType: "json",
        data: {
            "targetUrl": targetUrl
        }

    }).done(function (data, textStatus, jqXHR) {
        console.debug(data);
        showShortUrl(data.shortUrl);

    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.error(errorThrown);
        showError("Could not create short URL for " + targetUrl);

    });
};

var showShortUrl = function(shortUrl) {
    $("#result").html("Here is your short URL: <a href='" + shortUrl + "' target='_blank'>" + shortUrl + "</a>");
};

var showError = function(message) {
    $("#result").html("<p class='w3-text-red'>" + message + "</p>");
};

$(function() {
    $("#shorten-form").submit(function(event) {
        onFormSubmit($("#input-target-url").val());
        event.preventDefault();
    });
});
