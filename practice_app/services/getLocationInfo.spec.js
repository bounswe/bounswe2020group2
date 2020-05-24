const { processLocationInfo, getLanguage, getCurrency, getCountry } = require('./getLocationInfo')

describe('getCountry', () => {
    test('returns country value as expected', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [
                    {
                        symbol: 'a',
                        code: 'b',
                        name: 'c',
                    },
                ],
                languages: {
                    tur: 'Turkish',
                    en: 'English',
                },
            },
        }

        const output = getCountry(input)
        expect(output).toStrictEqual(input.country)
    })
})

describe('getCurrency', () => {
    test('returns expected result on valid input', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [
                    {
                        symbol: 'a',
                        code: 'b',
                        name: 'c',
                    },
                ],
                languages: {
                    tur: 'Turkish',
                    en: 'English',
                },
            },
        }

        const expected = {
            symbol: 'a',
            code: 'b',
            name: 'c',
        }

        const output = getCurrency(input)
        expect(output).toStrictEqual(expected)
    })

    test('returns nothing when currencies have 0 elements', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [],
                languages: {
                    tur: 'Turkish',
                    en: 'English',
                },
            },
        }

        const output = getCurrency(input)
        expect(output).toStrictEqual(undefined)
    })

    test('returns nothing when currencies does not exist', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                languages: {
                    tur: 'Turkish',
                    en: 'English',
                },
            },
        }

        const output = getCurrency(input)
        expect(output).toStrictEqual(undefined)
    })

    test('returns nothing when country_module does not exist', () => {
        const input = {
            country: 'Turkey',
        }

        const output = getCurrency(input)
        expect(output).toStrictEqual(undefined)
    })
})

describe('getLanguage', () => {
    test('returns expected language on valid input', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [
                    {
                        symbol: 'a',
                        code: 'b',
                        name: 'c',
                    },
                ],
                languages: {
                    tur: 'Turkish',
                    en: 'English',
                },
            },
        }

        const output = getLanguage(input)
        expect(output).toStrictEqual(input.country_module.languages.tur)
    })

    test('returns nothing when country_module does not exist.', () => {
        const input = {
            country: 'Turkey',
        }

        const output = getLanguage(input)

        expect(output).toStrictEqual(undefined)
    })

    test('returns nothing when country_module.languages doesnt exist.', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [
                    {
                        symbol: 'a',
                        code: 'b',
                        name: 'c',
                    },
                ],
            },
        }

        const output = getLanguage(input)

        expect(output).toStrictEqual(undefined)
    })

    test('returns nothin when country_module.languages has 0 elements', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [
                    {
                        symbol: 'a',
                        code: 'b',
                        name: 'c',
                    },
                ],
                languages: {},
            },
        }

        const output = getLanguage(input)

        expect(output).toStrictEqual(undefined)
    })
})

// describe, test and expect functions are globals defined by Jest framework inside test files
describe('processLocationInfo', () => {
    test('returns valid on valid input', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [
                    {
                        symbol: 'a',
                        code: 'b',
                        name: 'c',
                    },
                ],
                languages: {
                    tur: 'Turkish',
                },
            },
        }

        const output = processLocationInfo(input)

        expect(output.valid).toStrictEqual(true)
    })

    test('returns invalid on no country', () => {
        const input = {}

        const output = processLocationInfo(input)

        expect(output.valid).toStrictEqual(false)
    })

    test('returns invalid on no currencies', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [],
            },
        }

        const output = processLocationInfo(input)

        expect(output.valid).toStrictEqual(false)
    })

    test('returns invalid on bad data shape', () => {
        const input = {
            countrde: {
                flksdjf: [{ a: 2 }, 'hello'],
            },
            text: {},
            hello: 'there',
        }

        const output = processLocationInfo(input)

        expect(output.valid).toStrictEqual(false)
    })

    test('returns the country from the input', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [
                    {
                        symbol: 'a',
                        code: 'b',
                        name: 'c',
                    },
                ],
            },
        }

        const output = processLocationInfo(input)

        expect(output.country).toStrictEqual(input.country)
    })

    test('contains the property currency: {symbol, code, name}', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [
                    {
                        symbol: 'a',
                        code: 'b',
                        name: 'c',
                    },
                ],
            },
        }

        const output = processLocationInfo(input)

        expect(Object.keys(output.currency)).toEqual(['symbol', 'code', 'name'])
    })

    test('uses the first currency in the array', () => {
        const input = {
            country: 'Turkey',
            country_module: {
                currencies: [
                    {
                        symbol: 'a',
                        code: 'b',
                        name: 'c',
                    },
                    {
                        symbol: '1',
                        code: '2',
                        name: '3',
                    },
                ],
            },
        }

        const output = processLocationInfo(input)

        expect(output.currency.symbol).toEqual('a')
    })

    test('always contains the boolean property "valid"', () => {
        const output = processLocationInfo({})

        expect(Object.keys(output)).toContain('valid')
        expect(typeof output.valid).toBe('boolean')
    })
})
