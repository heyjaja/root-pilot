const post = {
    save : async function(token, post) {
        await fetch("/posts", {
            method: "POST",
            headers: {
                'content-type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + token,
            },
            body: JSON.stringify(post),
        }).then((response)=> response.json())
        .then((data) => {
            if(data.error) {
                throw new Error(data.message);
            }
            window.location.replace('/board');
        })
        .catch((error) => alert(error));
    },
    update : async function(token, post, postId) {
        await fetch("/posts/"+postId, {
            method: "PATCH",
            headers: {
                'content-type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + token,
            },
            body: JSON.stringify(post),
        }).then((response) => response.json())
        .then((data) => {
            if(data.error) {
                throw new Error(data.message);
            }

            window.location.replace('/board/' + postId);
        })
        .catch((error) => alert(error));
    },

    delete : async function(token, postId) {
        await fetch("/posts/"+postId, {
            method: "DELETE",
            headers: {
                'content-type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + token,
            },
        }).then((response)=> response.json())
        .then((data) => {
            if(data.error) {
                throw new Error(data.message);
            }
            window.location.replace('/board/');
        })
        .catch((error) => alert(error));
    },

    getPost : async function(postId) {

        const post = await fetch("/posts/" + postId, {
            method: "GET",
        }).then((response)=> response.json())
        .then((data) => {
            if(data.error) {
                throw new Error(data.message);
            }
            return data;
        })
        .catch((error) => {
            alert(error);
            window.location.href = "/board";
        });

        return post;
    },
    getPostsByUser : async function(token, userId, page) {
        const posts = await fetch("/posts/user/" + userId + "?page=" + page, {
            method: "GET",
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        }).then(response => response.json())
        .then(data => {
            if(data.error) {
                throw new Error(data.message)
            }

            return data;
        })
        .catch(error => alert(error));

        return posts;
    }
}
