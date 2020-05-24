exports.getPostData = function (response) {
    const values = response.data.data.children
    var outJSON = []
    for (let i = 0; i < values.length; i++) {
        var tmp = { title: values[i].data.title, permalink: values[i].data.permalink }
        outJSON.push(tmp)
    }
    return outJSON
}
