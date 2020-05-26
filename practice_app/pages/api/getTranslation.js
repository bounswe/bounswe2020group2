const axios = require('axios')
const isoConv = require('iso-language-converter')
const { checkTranslationResponse } = require('./getTranslationModule')

export default async (req, res) => {
    // req object contains whatever information we can get from the request that comes from the client (from the browser)
    // res object is used tow write our reponse
    const { query, method } = req

    switch (method) {
        case 'GET':
            {
                console.log(query)
                if (!query.sl || !query.countryCode) {
                    res.json({
                        answered: false,
                    })
                    return
                }
                let target = isoConv(query.countryCode, { from: 2, to: 1 })
                target = target.toLowerCase()
                const { text } = query
                let sourceLang = query.sl

                await axios
                    .get(
                        `https://translate.googleapis.com/translate_a/single?client=gtx&sl=${sourceLang}&tl=${target}&dt=t&q=${text}`,
                    )
                    .then(response => {
                        const values = checkTranslationResponse(response, sourceLang, target, text)
                        if (!values.isInputValid) {
                            res.json({
                                answered: false,
                            })
                            return
                        }
                        const responseText = values.translation
                        sourceLang = values.sourceLang
                        res.statusCode = 200
                        res.json({
                            answered: true,
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
