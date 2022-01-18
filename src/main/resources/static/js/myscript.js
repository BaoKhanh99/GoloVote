$("#countdown-vote")
    .countdown("2022/01/19", function(event) {
            if (event.elapsed){
                $(this).html.strftime("fuck");
            }else{
                    $(".days").text(
                        event.strftime("%D")
                    );
                    $(".hours").text(
                        event.strftime("%H")
                    );
                    $(".minutes").text(
                        event.strftime("%M")
                    );
                    $(".seconds").text(
                        event.strftime("%S")
                    );
            }

    });