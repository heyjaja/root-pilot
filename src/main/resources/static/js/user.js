const signinBtn = document.getElementById('signin-btn');
const signupBtn = document.getElementById('signup-btn');

const auth = {
    init : function() {
        const _this = this;

        if(signupBtn) signupBtn.addEventListener("click", _this.save);
        if(signinBtn) signinBtn.addEventListener("click", _this.login);
    },
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
        .catch((error) => alert(JSON.stringify(error)));

        window.location.href = "/board";
    },

    login : async function() {

        const data = commons.getFormData('login-form');

        const token = await fetch("/auth/signin", {
            method: "POST",
            headers: {
                'content-type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify(data),
        }).then((response) => response.json())
        .catch((error) => alert(JSON.stringify(error)));

        setToken(token.accessToken, 1);
        window.location.href = "/board";
    },
}

auth.init();

function setToken(token, days) {
    commons.setCookie("accessToken", token, days);
}