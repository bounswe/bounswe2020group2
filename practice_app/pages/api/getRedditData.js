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
                    .get(
                        'http://www.reddit.com/r/'+countryName+'/top/.json?limit=10&t=month',
                    )
                    .then(response => {
                        const values = response.data.data.children
						
                        res.statusCode = 200
                        res.json({
							answered: 'yes',
							posts: [
							{title: values[0].data.title, permalink: values[0].data.permalink},
							{title: values[1].data.title, permalink: values[1].data.permalink},
							{title: values[2].data.title, permalink: values[2].data.permalink},
							{title: values[3].data.title, permalink: values[3].data.permalink},
							{title: values[4].data.title, permalink: values[4].data.permalink},
							{title: values[5].data.title, permalink: values[5].data.permalink},
							{title: values[6].data.title, permalink: values[6].data.permalink},
							{title: values[7].data.title, permalink: values[7].data.permalink},
							{title: values[8].data.title, permalink: values[8].data.permalink},
							{title: values[9].data.title, permalink: values[9].data.permalink}
							]
						})
						
                    })
                    .catch(error => {
                        console.log(error)
                        res.statusCode = 500
                        res.json({})
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
