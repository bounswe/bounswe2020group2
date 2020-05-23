const Config = require('../config')
const axios = require('axios')

exports.processRedditData = function (values) {
    return {
        values
    }
}

exports.getPosts = async function ({ sub, cnt }) {
    console.log('Getting posts from r/ ', { sub, cnt })

    const uri = `http://www.reddit.com/r/${sub}/top.json?count=${cnt}`

    const { data } = await axios.get(uri)
	console.log(data)
    //const values = data.data[0]
    return { statusCode: 200, body: exports.processRedditData(values) }
}
