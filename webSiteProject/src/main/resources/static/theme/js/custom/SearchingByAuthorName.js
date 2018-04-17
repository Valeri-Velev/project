$(window).load(function() {
    var html = '';
    var author = "Admin";
    $.ajax({
        url:"/index/" + author ,
        type:"GET",
        success:function (result) {
            console.log(result);
            $('#article_title').empty();
            $('#article_title').append(html);
        },
        error: function (error) {
            console.log(error);
        }
    })
});