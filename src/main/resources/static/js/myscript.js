$("#countdown-vote")
    .countdown("2022/01/23", function(event) {
        console.log(event.elapsed)
            if (event.elapsed){
                $("#timing-header").text("THỜI GIAN ĐÃ KẾT THÚC");
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