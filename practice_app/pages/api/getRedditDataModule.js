exports.getPostData = function (response) {
    const values = response.data.children
    const outJSON = []
    for (let i = 0; i < values.length; i++) {
        const tmp = { title: values[i].data.title, permalink: values[i].data.permalink }
        outJSON.push(tmp)
    }
    return outJSON
}
