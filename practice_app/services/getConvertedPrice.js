const axios = require('axios')

function getCountry(data) {
    return data.country_name
}

function getCurrency(data) {
    return data.currency_code
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

exports.getInfoFromIP = async function ({ip}) {
    console.log('Getting the information of the ip', {ip})

    const params = `${ip}`
    const uri = `https://json.geoiplookup.io/${ip}`

    const { data } = await axios.get(uri)

    return exports.processIPInfo(data)
}

exports.getConvertedPrice = async function ({ip, srcCurrency, price}) {

    const ipdata = exports.getInfoFromIP({ip})
    ipdata.then(ipdata => {console.log(ipdata)})
    

    return {statusCode: 200, body: {}}
}
