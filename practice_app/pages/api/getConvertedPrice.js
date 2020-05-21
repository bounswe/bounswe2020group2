const PriceConversionService = require('../../services/getConvertedPrice')

export default async (req, res) => {
    try {
        const { query, method } = req

        if (method != 'GET') {
            res.statusCode = 400
            return res.send()
        }

        
        const { statusCode, body } = await PriceConversionService.getConvertedPrice({
            ip: query.ip,
            srcCurrency: query.srcCurrency,
            price: query.price
        })

        

        res.statusCode = statusCode
        res.json(body)
    } catch (e) {
        console.error(e)
        res.statusCode = 500
        res.json({ error: 'An unknown error occured' })
    }
}
