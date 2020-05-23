const { checkGetRedditData } = require('./getRedditData')

describe('checkGetRedditData', () => {
    test('check if data is collected', () => {
        const { data } = axios.get(`api/getRedditData?country=Turkey`)
        expect(data.answered).toBe('yes')
    })
})
