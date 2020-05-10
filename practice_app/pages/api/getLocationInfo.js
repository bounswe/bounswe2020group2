const axios = require('axios')

export default (req, res) => {
  const { query, method } = req

  if (method != 'GET') {
    res.statusCode = 400
    return res.send()
  }
  const apiKey = 'ae7fc0efbe65c249d2ca580ec51b3fdd'
  const params = `${query.lat},${query.lng}`
  const uri = `http://api.positionstack.com/v1/reverse?access_key=${apiKey}&query=${params}&country_module=1`
  axios
    .get(uri)
    .then(response => {
      const values = response.data.data[0]
      if (values.country == null) {
        const result = {
          valid: false,
          message: 'No country exists at given location.',
        }
        res.statusCode = 200
        return res.json(result)
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
      res.statusCode = 200
      res.json(result)
    })
    .catch(err => console.log(err))
}
