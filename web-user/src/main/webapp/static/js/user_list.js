function go(pageCount) {
    doGo(pageCount);
}

function goForEnter(pageCount) {
    if (event instanceof KeyboardEvent && event.keyCode == "13") {
        doGo(pageCount);
    }
}

function doGo(pageCount) {
    var goNum = document.getElementById("goNum").value;
    if (goNum < 1 || goNum > pageCount) {
        alert("请输入正确的页码")
        return;
    }
    window.location.href="/users?pageNum=" + goNum + "&numPerPage=";
}

function changeNumPerPage(newNumPerPage) {
    window.location.href="/users?pageNum=1&numPerPage=" + newNumPerPage;
}