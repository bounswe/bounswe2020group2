const axios = require('axios')
const Config = require('../config')

exports.getCountry = function (values) {
    return values.country
}

exports.getCurrency = function (values) {
    if (!values.country_module) return
    if (!values.country_module.currencies) return
    if (!values.country_module.currencies.length) return

    const chosenCurrency = values.country_module.currencies[0]
    return {
        symbol: chosenCurrency.symbol,
        code: chosenCurrency.code,
        name: chosenCurrency.name,
    }
}

exports.getLanguage = function (values) {
    if (!values.country_module) return
    if (!values.country_module.languages) return

    const languages = Object.values(values.country_module.languages)
    if (!languages.length) return

    return languages[0]
}

exports.processLocationInfo = function (values) {
    if (!values.country) {
        return {
            valid: false,
            message: 'No country exists at given location.',
        }
    }

    const country = exports.getCountry(values)
    const currency = exports.getCurrency(values)
    const language = exports.getLanguage(values)

    const valid = Boolean(country && currency && language)

    return {
        valid,
        country,
        currency,
        language,
    }
}

exports.getLocationInfo = async function ({ lat, lng }) {
    console.log('Getting location info for', { lat, lng })

    const params = `${lat},${lng}`
    const uri = `http://api.positionstack.com/v1/reverse?access_key=${Config.positionStack.apiKey}&query=${params}&country_module=1`

    const { data } = await axios.get(uri)
    const values = data.data[0]

    return { statusCode: 200, body: exports.processLocationInfo(values) }
}
