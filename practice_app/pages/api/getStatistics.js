import axios from 'axios'

const { checking } = require('./getStatisticsModule')

export default (req, res) => {
    // req object contains whatever information we can get from the request that comes from the client (from the browser)
    // res object is used tow write our reponse
    const { query, method } = req

    switch (method) {
        case 'GET':
            {
                // ...
                res.statusCode = 200

                const countryName = query.country

                let deathToll1
                let recovered1
                let answered1
                let infected1

                axios
                    .get(
                        'https://api.apify.com/v2/key-value-stores/tVaYRsPHLjNdNBu7S/records/LATEST?disableRedirect=true',
                    )

                    .then(response => {
                        let i

                        for (i = 0; i < response.data.length; i++) {
                            if (response.data[i].country == countryName) {
                                answered1 = 'yes'
                                deathToll1 = response.data[i].deceased
                                recovered1 = response.data[i].recovered
                                infected1 = response.data[i].infected

                                break
                            }
                        }

                        const answered2 = checking({ countryN: countryName, answer: answered1 }).ansnull

                        res.statusCode = 200
                        res.json({
                            answered: answered2,
                            deathToll: deathToll1,
                            recovery: recovered1,
                            infection: infected1,
                        })
                    })
                    .catch(error => {
                        res.statusCode = 500

                        console.log(error)
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
