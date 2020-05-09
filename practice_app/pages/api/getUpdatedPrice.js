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
        	var productPrice = 60
        	// hold client's ip adress
        	var clientIP = query.ip == null ? "" : query.ip

        	//use two apis synchroniously
	        Promise.all([
				axios.get("https://json.geoiplookup.io/"+clientIP),
				axios.get("https://api.exchangeratesapi.io/latest")
						])
				.then(function (responses) {
					// Get a JSON object from each of the responses
					var takencurrencyType = responses[0].data.currency_code
					var takencurrencyValue = responses[1].data.rates[takencurrencyType]
					var totalpricenum = takencurrencyValue*productPrice
					res.statusCode = 200

					//assign them to response JSon object
					res.json({
        				currencyType: takencurrencyType,
        				totalprice: totalpricenum
        			})
						}).catch(function (error) {
						// if there's an error, log it
					console.log(error);
							});
            // ...
        } break
        default: {
        	res.statusCode = 200
            res.send('please use GET')
            break
        }
    }
}
