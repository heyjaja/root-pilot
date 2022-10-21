const saveBtn = document.getElementById('save-btn');
const updateBtn = document.getElementById('update-btn');
const deleteBtn = document.getElementById('delete-btn');
const postId = document.getElementById('id-area');

const post = {
    init : function() {
        const _this = this;

        if(saveBtn) saveBtn.addEventListener("click", _this.save);
        if(updateBtn) updateBtn.addEventListener("click", _this.update);
        if(deleteBtn) deleteBtn.addEventListener("click", _this.delete);
    },
    save : async function() {

        const data = commons.getFormData("save-form");
        const token = commons.getCookie("accessToken");
        console.log(token);

        if(!token) {
            alert("로그인이 필요합니다.");
            return;
        }

        if(!confirm("글을 등록하시겠습니까?")) {
            return;
        }

        await fetch("/posts", {
            method: "POST",
            headers: {
                'content-type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + token,
            },
            body: JSON.stringify(data),
        }).then((response)=>{

            if(!response.ok) {
                alert("오류가 발생했습니다.");
            }

            window.location.href='/board'

        }).catch((error) => alert(JSON.stringify(error)))
    },
    update : async function() {

        const data = commons.getFormData('update-form');
        const token = commons.getCookie("accessToken");

        if(!token) {
            alert("로그인이 필요합니다.");
            return;
        }

        if(!confirm("글을 수정하시겠습니까?")) {
            return;
        }

        await fetch("/posts/"+postId.value, {
            method: "PATCH",
            headers: {
                'content-type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + token,
            },
            body: JSON.stringify(data),
        }).then(() => {
            alert("글이 수정되었습니다.");
            window.location.href='/board/'+postId.value;
        }).catch((error) => alert(JSON.stringify(error)));
    },

    delete : async function() {

        const token = commons.getCookie("accessToken");

        if(!token) {
            alert("로그인이 필요합니다.");
            return;
        }

        if(!confirm("글을 삭제하시겠습니까?")) {
            return;
        }

        await fetch("/posts/"+postId.value, {
            method: "DELETE",
            headers: {
                'content-type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + token,
            },
        }).then((data) => {
            alert("글이 삭제되었습니다.");
            window.location.href='/board';
        }).catch((error) => alert(JSON.stringify(error)));
    },
}

post.init();