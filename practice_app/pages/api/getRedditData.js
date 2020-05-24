const axios = require('axios')

export default (req, res) => {
    // req object contains whatever information we can get from the request that comes from the client (from the browser)
    // res object is used tow write our reponse
    const { query, method } = req

    switch (method) {
        case 'GET':
            {
                const countryName = query.country
                //const countryName = query.count For number of posts
                axios
                    .get('http://www.reddit.com/r/' + countryName + '/top/.json?limit=10&t=month')
                    .then(response => {
                        const values = response.data.data.children
						var outJSON = []
						for(let i = 0; i < values.length; i++){
							var tmp = {title : values[i].data.title,permalink : values[i].data.permalink};
							outJSON.push(tmp);
						}

                        res.statusCode = 200
                        res.json({
                            answered: 'yes',
                            posts:outJSON,
                        })
                    })
                    .catch(error => {
                        console.log(error)
                        res.statusCode = 500
                        res.json({answered: 'no'})
                    })
            }
            break
        default: {
            res.statusCode = 200
            res.send('please use GET')
            break
        }
    }
}
