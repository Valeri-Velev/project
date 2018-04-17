function removeImage(id) {
    console.log(id);
    /*var id = $(".remove-image").attr("id");*/

    var x = document.getElementById("remove-picture-" + id);
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }

    $.ajax({
        url:"/removeImage/" + id,
        method:"GET",
        success: function (result) {
            console.log(result);
        },
        error:function () {
            console.log('Error');
        }
    });
}
