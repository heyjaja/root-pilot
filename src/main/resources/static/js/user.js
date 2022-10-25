const user = {
    save : async function() {
        if(!confirm("회원가입을 하시겠습니까?")) {
            return;
        }

        const data = commons.getFormData("signup-form");

        await fetch("/auth/signup", {
            method: "POST",
            headers: {
                'content-type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify(data),
        }).then((response) => response.json())
        .then((data) => {
            if(data.error) throw new Error(data.message);

            alert("회원가입이 완료됐습니다.")
            window.location.href="/board";
        })
        .catch((error) => alert(error));
    },

    login : async function() {

        const data = commons.getFormData('login-form');

        await fetch("/auth/signin", {
            method: "POST",
            headers: {
                'content-type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify(data),
        }).then((response) => response.json())
        .then((data) => {
            if(data.error) throw new Error(data.message);

            setToken(data.accessToken, 1);
            window.location.href = "/board";
        })
        .catch((error) => alert(error));


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
    }
}

function setToken(token, days) {
    commons.setCookie("accessToken", token, days);
}