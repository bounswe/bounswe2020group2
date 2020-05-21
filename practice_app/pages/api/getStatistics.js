import axios from 'axios'

const {checking} = require('./getStatisticsModule');

export default (req, res) => {
    // req object contains whatever information we can get from the request that comes from the client (from the browser)
    // res object is used tow write our reponse
    const { query, method } = req

    switch (method) {
        case 'GET': {
            res.statusCode = 200
            
            const countryName = query.country

            axios.get("https://api.apify.com/v2/key-value-stores/tVaYRsPHLjNdNBu7S/records/LATEST?disableRedirect=true")

            .then((response) => {
                
                const values= checking(response,countryName)
            
                let deathToll1 = values.deathToll1
                let recovered1 = values.recovered1
                let answered1 = values.answered1
                let infected1  = values.infected1
  
                res.json({
                    answered: answered1,
                    deathToll: deathToll1,
                    recovery: recovered1,
                    infection:infected1                   
                })
              }).catch(function (error) {                
            res.statusCode = 500
            res.json({})
            console.log(error);
                    });        
        } break
        default: {
            res.statusCode = 200
            res.send('please use GET')
            break
        }
    }
}
