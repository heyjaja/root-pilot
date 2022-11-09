const commons = {
    getFormData : function(formId) {
        let form = document.getElementById(formId);
        form = new FormData(form);
        const formData = {};
        form.forEach((value, key) => formData[key]=value);

        return formData;
    },
    getCookie : function(name) {
        const value = document.cookie.split("; ").find(row => row.startsWith(name));

        return value ? value.substring(name.length+1) : null;
    },
    setCookie : function(name, value, days) {
        let expireDate = new Date();
        expireDate.setDate(expireDate.getDate() + days);

        let cookieValue = name + "=" + value + '; path="/"; expires='+expireDate.toUTCString()+";";

        document.cookie=cookieValue;
    },
    displayTime : function(time) {
        const date = new Date(time);
        const yy = date.getFullYear();
        const mon = date.getMonth()+1;
        const dd = date.getDay();
        const hh = date.getHours();
        const mm = date.getMinutes();

        return yy + "-" + mon + "-"+ (dd < 10 ? "0" + dd : dd) + " "+ hh + ":" + mm;
    }
}


function hideElement(element) {
    element.classList.add("collapse");
}

function showElement(element) {
    element.classList.remove("collapse");
}
