import axios from 'axios'
const { getTop10DataFor } = require('./getCountriesWithMaxDeathsModule')
export default (req, res) => {
    // req object contains whatever information we can get from the request that comes from the client (from the browser)
    // res object is used tow write our reponse
    const { query, method } = req
    switch (method) {
        case 'GET': {
            // ...
            res.statusCode = 200
            axios.get("https://api.apify.com/v2/key-value-stores/tVaYRsPHLjNdNBu7S/records/LATEST?disableRedirect=true")
            .then((response) => {
                const data = getTop10DataFor(response)
                res.json({
                    keys: data.keys,
                    values: data.values
                })
            })
            .catch(error => {
                console.log(error)
            })
        } break
        default: {
            res.statusCode = 200
            res.send('please use GET')
            break
        }
    }
}
