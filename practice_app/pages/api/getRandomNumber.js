export default (req, res) => {
    // req object contains whatever information we can get from the request that comes from the client (from the browser)
    // res object is used tow write our reponse
    const { query, method } = req

    switch (method) {
        case 'GET': {
            // ...
            res.statusCode = 200
            res.json(Math.random())
        } break
        default: {
            res.statusCode = 200
            res.send('please use GET')
            break
        }
    }
}
