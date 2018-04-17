$(window).load(function() {
    //link the pagination template(html) with a JS function to get the page number
    var page = 1;
    var offset = 10*(page-1);
    var limit = 3;
    var html = '';
    var tagString='';
    $.ajax({
        url: "/articles/?page=" + offset + "&size=" + limit,
        type: "GET",
        success: function(result){
            console.log(result.content);
            for(var i = 0;i < result.content.length ; i++){

                for(var j=0;j<result.content.length;j++)
                {
                    console.log(result.content[i]);
                }
                html += '<div><div><h2 style="text-decoration-style: double; size: 20px;color: white;">' + result.content[i].title + '</h2></div><br/><p style="color: white;">' + result.content[i].content + '</p><small style="color: dodgerblue; size: 20px" class="author"></small> <p></p> <p> </p> <div> <div class="center-block"> <a class="btn-theme button1" href="/article/' + result.content[i].id + '">Read more</a></div></div></div>';

                tagString='';
            }
            $('#article-content').append(html);
        },
        error: function (error) {
            console.log(error);
        }
    });
});
