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
        const posts = await fetch("/userpost/" + userId + "?page=" + page, {
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
    },
    getPostsByUserNext : async function(token, userId, postId, keyword) {
        let url = "/userpost/" + userId;
        if(postId && keyword) {
            url += "?postId="+postId + "&keyword="+keyword;
        } else if(postId) {
            url += "?postId="+postId;
        } else if(keyword) {
            url += "?keyword="+keyword;
        }

        const posts = await fetch(url, {
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

const reply = {
    getList: async function(postId, callback, page) {
        let url = "/reply/" + postId;

        if(page) {
            url += "?page="+page;
        }

        await fetch(url, {
            method: "GET",
        }).then((response)=> response.json())
        .then((data) => {
            if(data.error) {
                throw new Error(data.message);
            }
            callback(data);
        })
        .catch((error) => {
            console.log(error);
        });
    },
    save: async function(token, reply) {
        const result = await fetch("/reply", {
            method: "POST",
            headers: {
                'content-type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + token,
            },
            body: JSON.stringify(reply),
        }).then((response)=> response.json())
        .then((data) => {
            if(data.error) {
                throw new Error(data.message);
            }
            return data;
        })
        .catch((error) => alert(error));

        return result;
    },
    delete: async function(token, replyId) {
        const result = await fetch("/reply/" + replyId, {
            method: "DELETE",
            headers: {
                'content-type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + token,
            }
        }).then((response)=> response.json())
        .then((data) => {
            if(data.error) {
                throw new Error(data.message);
            }
            return data;
        })
        .catch((error) => alert(error));

        return result;
    },
    update: async function(token, replyId, reply) {
        const result = await fetch("/reply/" + replyId, {
            method: "PATCH",
            headers: {
                'content-type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + token,
            },
            body: JSON.stringify(reply),
        }).then((response) => response.json())
        .then((data) => {
            if (data.error) {
                throw new Error(data.message);
            }
            return data;
        })
        .catch((error) => alert(error));

        return result;
    },
}

// 무한스크롤
const io = new IntersectionObserver((entry, observer) => {

    const ioTarget = entry[0].target;

    if(entry[0].isIntersecting) {
        io.unobserve(targetLi);
        reply.getList(getPostId, appendReply, ++replyPage);


    }
}, {
    threshold: 0.5
});
