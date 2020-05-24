import axios from 'axios'

const { checkApiResponseForCountry } = require('./getStatisticsModule')

export default (req, res) =>
    new Promise(async resolve => {
        // req object contains whatever information we can get from the request that comes from the client (from the browser)
        // res object is used tow write our reponse
        const { query, method } = req

        switch (method) {
            case 'GET':
                {
                    const countryName = query.country

                    axios
                        .get(
                            'https://api.apify.com/v2/key-value-stores/tVaYRsPHLjNdNBu7S/records/LATEST?disableRedirect=true',
                        )
                        .then(response => {
                            const values = checkApiResponseForCountry(response, countryName)

                            let { deathToll1, recovered1, answered1, infected1 } = values
                            if (deathToll1 == null) {
                                deathToll1 = 'NA'
                            }
                            if (recovered1 == null) {
                                recovered1 = 'NA'
                            }
                            if (infected1 == null) {
                                infected1 = 'NA'
                            }

                            res.statusCode = 200
                            res.json({
                                answered: answered1,
                                deathToll: deathToll1,
                                recovery: recovered1,
                                infection: infected1,
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
    })
