const axios = require('axios')

export default (req, res) => {
    // req object contains whatever information we can get from the request that comes from the client (from the browser)
    // res object is used tow write our reponse
    const { query, method } = req

    switch (method) {
        case 'GET':
            {
                console.log(query)
                let target = query.countryCode == null ? '' : query.countryCode
                const text = query.text == null ? '' : query.text
                target = target.toLowerCase()

                axios
                    .get(
                        `https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=${target}&dt=t&q=${text}`,
                    )
                    .then(response => {
                        const responseText = response.data[0][0][0]
                        res.statusCode = 200
                        res.json({
                            answered: 'yes',
                            translation: responseText,
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
