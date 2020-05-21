const LocationInfoService = require('../../services/getLocationInfo')

export default async (req, res) => {
    try {
        const { query, method } = req

        if (method != 'GET') {
            res.statusCode = 400
            return res.send()
        }

        const { statusCode, body } = await LocationInfoService.getLocationInfo({
            lat: query.lat,
            lng: query.lng,
        })

        res.statusCode = statusCode
        res.json(body)
    } catch (e) {
        console.error(e)
        res.statusCode = 500
        res.json({ error: 'An unknown error occured' })
    }
}
