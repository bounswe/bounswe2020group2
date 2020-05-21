const { checkApiResponseForCountry } = require('./getStatisticsModule')

describe('checkApiResponseForCountry', () => {
    test('when country is null', () => {
        const output = checkApiResponseForCountry(null, null)
        expect(output.isCountryNull).toBe('yes')
    })

    test('when country is not null and response is null', () => {
        const output = checkApiResponseForCountry(null, 'Turkey')
        expect(output.isCountryNull).toBe('no')
    })

    test('when country is not null and response is null', () => {
        const output = checkApiResponseForCountry(null, 'Tunisia')
        expect(output.answered1).toBe('no')
    })

    test('when country is not null and response is not null', () => {
        const output = checkApiResponseForCountry(
            {
                data: [
                    {
                        infected: 7728,
                        tested: 'NA',
                        recovered: 4062,
                        deceased: 575,
                        country: 'Algeria',
                        moreData:
                            'https://api.apify.com/v2/key-value-stores/pp4Wo2slUJ78ZnaAi/records/LATEST?disableRedirect=true',
                        historyData: 'https://api.apify.com/v2/datasets/hi0DJXpcyzDwtg2Fm/items?format=json&clean=1',
                        sourceUrl: 'http://covid19.sante.gov.dz/carte',
                        lastUpdatedSource: '2020-05-21T00:00:00.000Z',
                        lastUpdatedApify: '2020-05-21T15:30:00.000Z',
                    },
                ],
            },
            'Algeria',
        )
        expect(output.answered1).toBe('yes')
    })
})
