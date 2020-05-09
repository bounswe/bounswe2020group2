import axios from 'axios'


export default (req, res) => {
    // req object contains whatever information we can get from the request that comes from the client (from the browser)
    // res object is used tow write our reponse
    const { query, method } = req

    switch (method) {
        case 'GET': {
        	// This is not important for now
        	var productID = query.pid 
        	// Creating a random product price
        	var productPrice = Math.random(250)
        	// hold client's ip adress
        	var clientIP = query.ip == null ? "" : query.ip

        	// This is a Promise that holds the geographic data such as ip, location, currency code etc.
        	var geoData = axios.get("https://json.geoiplookup.io/"+clientIP)

        	// When you fetch the geoData, use it the update response JSON
        	geoData.then(response => {
        		clientCurrency = response.data.currency_code
        		res.json({
        			currency: clientCurrency
        		})
        	})
            // ...
            res.statusCode = 200
        } break
        default: {
            res.statusCode = 200
            res.send('please use GET')
            break
        }
    }
}
