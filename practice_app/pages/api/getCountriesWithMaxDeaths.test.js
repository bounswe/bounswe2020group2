const { getTop10DataFor } = require('./getCountriesWithMaxDeathsModule')
const response = require('./examplecoviddata.json')

describe('getTop10DataFor', () => {
    test('whether response has at least 10 countries', () => {
        const data = getTop10DataFor(response)
        expect(data.keys.length).toEqual(10)
    })
    test('whether response has at least 10 death numbers', () => {
        const data = getTop10DataFor(response)
        expect(data.values.length).toEqual(10)
    })
})
