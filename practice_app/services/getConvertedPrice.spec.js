const { processCurrencyInfo } = require('./getConvertedPrice')
const { processIPInfo } = require('./getConvertedPrice')

// describe, test and expect functions are globals defined by Jest framework inside test files
describe('processIPInfo', () => {
    test('returns valid on valid input', () => {
        const input = {
            "country_name": "Turkey",
            "currency_code": "TRY",
        }

        const output = processIPInfo(input)

        expect(output.valid).toStrictEqual(true)
    })

    test('returns invalid on no country name', () => {
        const input = {
            "currency_code": "TRY"
        }

        const output = processIPInfo(input)

        expect(output.valid).toStrictEqual(false)
    })

    test('returns invalid on no currency code', () => {
        const input = {
            "country_name": "Turkey"
        }

        const output = processIPInfo(input)

        expect(output.valid).toStrictEqual(false)
    })

    test('returns invalid on bad data shape', () => {
        const input = {
            counter_name: "Turkia",
            currency_coder: "TRyi"
        }

        const output = processIPInfo(input)

        expect(output.valid).toStrictEqual(false)
    })

    test('returns the currency code from the input', () => {
        const input = {
            "ip": "159.146.10.4",
            "isp": "TurkNet Iletisim Hizmetleri",
            "org": "TurkNet Iletisim Hizmetleri A.S.",
            "hostname": "159.146.10.4",
            "latitude": 41.0082,
            "longitude": 28.9784,
            "postal_code": "34122",
            "city": "Istanbul",
            "country_code": "TR",
            "country_name": "Turkey",
            "continent_code": "AS",
            "continent_name": "Asia",
            "region": "Istanbul",
            "district": "",
            "timezone_name": "Europe\/Istanbul",
            "connection_type": "Cable\/DSL",
            "asn_number": 12735,
            "asn_org": "TurkNet Iletisim Hizmetleri A.S.",
            "asn": "AS12735 - TurkNet Iletisim Hizmetleri A.S.",
            "currency_code": "TRY",
            "currency_name": "Turkish Lira",
            "success": true,
            "premium": false
        }

        const output = processIPInfo(input)

        expect(output.currency).toStrictEqual(input.currency_code)
    })

    test('returns the country from the input', () => {
        const input = {
            "ip": "159.146.10.4",
            "isp": "TurkNet Iletisim Hizmetleri",
            "org": "TurkNet Iletisim Hizmetleri A.S.",
            "hostname": "159.146.10.4",
            "latitude": 41.0082,
            "longitude": 28.9784,
            "postal_code": "34122",
            "city": "Istanbul",
            "country_code": "TR",
            "country_name": "Turkey",
            "continent_code": "AS",
            "continent_name": "Asia",
            "region": "Istanbul",
            "district": "",
            "timezone_name": "Europe\/Istanbul",
            "connection_type": "Cable\/DSL",
            "asn_number": 12735,
            "asn_org": "TurkNet Iletisim Hizmetleri A.S.",
            "asn": "AS12735 - TurkNet Iletisim Hizmetleri A.S.",
            "currency_code": "TRY",
            "currency_name": "Turkish Lira",
            "success": true,
            "premium": false
        }

        const output = processIPInfo(input)

        expect(output.country).toEqual(input.country_name)
    })

    test('always contains the boolean property "valid"', () => {
        const output = processIPInfo({})

        expect(Object.keys(output)).toContain('valid')
        expect(typeof output.valid).toBe('boolean')
    })
})
