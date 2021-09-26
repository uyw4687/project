var main = {

    init: function() {
        var _this = this;
        $("#new").click( function() {
            _this.create();
        })
        $("#edit").click( function() {
            _this.edit();
        })
        $("#del").click( function() {
            _this.remove();
        })
    },

    create: function() {
        var data = {
            author: $("#author").val(),
            title: $("#title").val(),
            content: $("#content").val(),
        };
        $.ajax({
            method: "POST",
            url: "/api/posts",
            contentType:"application/json;charset=UTF-8",
            data: JSON.stringify(data),
        }).done(function() {
            alert("게시물을 등록하였습니다.");
            window.location.href = "/";
        }).fail(function(error) {
            alert("게시물이 등록되지 않았습니다.\n" + JSON.stringify(error))
        })
    },

    edit: function() {
        var data = {
            title: $("#title").val(),
            content: $("#content").val(),
        }

        var id = $("#id").val();

        $.ajax({
            method: "PUT",
            url: "/api/posts/"+id,
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data),
        }).done(function() {
            alert("수정을 완료했습니다.");
            window.location.href = "/";
        }).fail(function(error) {
            alert("수정하지 못했습니다.\n" + JSON.stringify(error));
        })
    },

    remove: function() {
        var id = $("#id").text();

        $.ajax({
            method: "DELETE",
            url: "/api/posts/"+id,
        }).done(function() {
            alert("삭제하였습니다.");
            window.location.href = "/";
        }).fail(function(error) {
            alert("삭제하지 못했습니다.\n" + JSON.stringify(error));
        })
    },

}
main.init();