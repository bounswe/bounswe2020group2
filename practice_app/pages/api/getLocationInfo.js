const axios = require('axios')

export default (req, res) => {
  // req object contains whatever information we can get from the request that comes from the client (from the browser)
  // res object is used tow write our reponse

  // REMEMBER: You can always console.log (either in the server or in the browser, depending on where the code is
  // executed) to see what each object looks like!

  // http://api.positionstack.com/v1/reverse?access_key=ae7fc0efbe65c249d2ca580ec51b3fdd&query=40.7638435,-73.9729691&country_module=1

  const { query, method } = req

  if (method != 'GET') {
    res.statusCode = 400
    return res.send()
  }
  const params = `${query.lat},${query.lng}`
  const uri = `http://api.positionstack.com/v1/reverse?access_key=ae7fc0efbe65c249d2ca580ec51b3fdd&query=${params}&country_module=1`
  axios
    .get(uri)
    .then(response => {
      const values = response.data.data[0]
      if (values.country == null) {
        const result = {
          valid: false,
          message: 'No country exists at given location.',
        }
        res.json(result)
        res.statusCode = 200
        return res.send()
      }
      const currency = {
        symbol: values.country_module.currencies[0].symbol,
        code: values.country_module.currencies[0].code,
        name: values.country_module.currencies[0].name,
      }

      const result = {
        valid: true,
        country: values.country,
        currency,
      }
      res.json(result)
      res.statusCode = 200
      res.send()
    })
    .catch(err => console.log(err))
}
