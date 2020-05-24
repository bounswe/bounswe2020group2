const axios = require('axios')
const { checkTranslationResponse } = require('./getTranslationModule')

export default async (req, res) => {
    // req object contains whatever information we can get from the request that comes from the client (from the browser)
    // res object is used tow write our reponse
    const { query, method } = req

    switch (method) {
        case 'GET':
            {
                console.log(query)
                let target = query.countryCode == null ? '' : query.countryCode
                target = target.toLowerCase()
                const text = query.text == null ? '' : query.text
                let sourceLang = query.sl == null ? '' : query.sl

                await axios
                    .get(
                        `https://translate.googleapis.com/translate_a/single?client=gtx&sl=${sourceLang}&tl=${target}&dt=t&q=${text}`,
                    )
                    .then(response => {
                        const values = checkTranslationResponse(response, sourceLang, target, text)
                        const responseText = values.translation
                        sourceLang = values.sourceLang
                        res.statusCode = 200
                        res.json({
                            answered: 'yes',
                            sourceLanguage: sourceLang,
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
