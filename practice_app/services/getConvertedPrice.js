const axios = require('axios')

function getCountry(data) {
    return data.country_name
}

function getCurrency(data) {
    return data.currency_code
}

function getExchangeRate(data, currencyType) {
    return data.rates[currencyType]
}

exports.processIPInfo = function (data) {
    const country = getCountry(data)
    const currency = getCurrency(data)

    const valid = Boolean(country && currency)

    return {
        valid,
        country,
        currency,
    }
}

exports.processCurrencyInfo = function (data, srcCurrency, tarCurrency) {
    const tarRate = getExchangeRate(data, tarCurrency)
    const srcRate = getExchangeRate(data, srcCurrency)

    const valid = Boolean(tarRate && srcRate)
    let ratio = null
    if (valid) ratio = tarRate / srcRate

    return {
        valid,
        tarRate,
        srcRate,
        ratio,
    }
}

exports.getInfoFromIP = async function ({ ip }) {
    console.log('Getting the information of the ip', { ip })

    const params = `${ip}`
    const uri = `https://json.geoiplookup.io/${ip}`

    const { data } = await axios.get(uri)

    return exports.processIPInfo(data)
}

exports.getInfoCurrency = async function ({ srcCurrency, tarCurrency }) {
    console.log('Getting the information of the currency')

    const uri = 'https://api.exchangeratesapi.io/latest'

    const { data } = await axios.get(uri)

    return exports.processCurrencyInfo(data, srcCurrency, tarCurrency)
}

exports.getConvertedPrice = async function ({ ip, srcCurrency, price }) {
    const ipData = await exports.getInfoFromIP({ ip })

    const tarCurrency = ipData.currency

    const currencyData = await exports.getInfoCurrency({ srcCurrency, tarCurrency })
    const updatedPrice = currencyData.ratio * price

    return { statusCode: 200, body: {price: updatedPrice, currency: tarCurrency } }
}
