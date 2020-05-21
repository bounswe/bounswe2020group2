const { checking } = require('./getStatisticsModule')

describe('getStatistics tests', () => {
    test('when country is null and answer is not null', () => {
        const output = checking({ countryN: null, answer: 'asd' })
        expect(output.cnull).toBe('yes')
    })

    test('when country is null and answer is null', () => {
        const output = checking({ countryN: null, answer: null })
        expect(output.cnull).toBe('yes')
    })

    test('when country is not null and answer is yes', () => {
        const output = checking({ countryN: 'Turkey', answer: 'yes' })
        expect(output.ansnull).toBe('yes')
    })

    test('when country is not null and answer is null', () => {
        const output = checking({ countryN: 'France', answer: null })
        expect(output.ansnull).toBe('no')
    })
})
