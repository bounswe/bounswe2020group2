import axios from 'axios'

export default (req, res) => {
    // req object contains whatever information we can get from the request that comes from the client (from the browser)
    // res object is used tow write our reponse
    const { query, method } = req

    switch (method) {
        case 'GET': {
            // ...
            res.statusCode = 200

            const countryName = query.country

            let deathToll1 = 'No information available for your country'

            axios.get("https://api.apify.com/v2/key-value-stores/tVaYRsPHLjNdNBu7S/records/LATEST?disableRedirect=true")

            .then((response) => {

                
                var i

                for (i = 0; i < response.data.length; i++) 
                {
                    if(response.data[i].country==countryName)
                    {
                        
                        deathToll1= response.data[i].deceased
                        break


                    }
                
                    
                }
                
                res.json({
                    deathToll: deathToll1
                })

              }).catch(function (error) {
                // if there's an error, log it
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
