const user = {
    save : async function(user) {
        if(!confirm("회원가입을 하시겠습니까?")) {
            return;
        }

        const result = await fetch("/auth/signup", {
            method: "POST",
            headers: {
                'content-type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify(user),
        }).then((response) => response.json())
        .then((data) => {
            if(data.error) throw new Error(data.message);

            window.location.href="/board";
        })
        .catch((error) => alert(error));

        return result;
    },

    update : async function(token, user) {
        if(!confirm("회원 정보를 수정하시겠습니까?")) {
            return;
        }

        const result = await fetch("/user/" + user.userId, {
            method: "PATCH",
            headers: {
                'content-type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + token,
            },
            body: JSON.stringify(user),
        }).then((response) => response.json())
        .then((data) => {
            if(data.error) throw new Error(data.message);
            return data;
        })
        .catch((error) => alert(error));

        return result;
    },

    login : async function(user) {
        const token = await fetch("/auth/signin", {
            method: "POST",
            headers: {
                'content-type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify(user),
        }).then((response) => response.json())
        .then((data) => {
            if(data.error) throw new Error(data.message);
            return data;
        })
        .catch((error) => alert(error));

        return token;


    },
    getUser : async function(token) {
        const getUser = await fetch("/user", {
            method: "get",
            headers: {
                'Authorization': 'Bearer ' + token,
            },
        }).then((response) => {
            if(response.redirected) {
                return null;
            }
            return response.json()
        })
        .catch((error) => alert(error));

        return getUser;
    },
    checkName : async function(name) {
        const result = await fetch("/auth/signup/exists-name", {
            method: "post",
            headers: {
                'content-type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify(name),
        }).then(response => response.json())
        .catch(error => alert(error));

        return result;
    },
    checkEmail : async function(email) {
        const result = await fetch("/auth/signup/exists-email", {
            method: "post",
            headers: {
                'content-type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify(email),
        }).then(response => response.json())
        .catch(error => alert(error));

        return result;
    },
}

function setToken(token, days) {
    commons.setCookie("accessToken", token, days);
}