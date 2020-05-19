const axios = require('axios')
const Config = require('../config')

function getCountry(values) {
    return values.country
}

function getCurrency(values) {
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

function getLanguage(values) {
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

    const country = getCountry(values)
    const currency = getCurrency(values)
    const language = getLanguage(values)

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
