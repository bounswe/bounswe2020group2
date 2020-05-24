const { checkTranslationResponse } = require('./getTranslationModule')

describe('checkTranslationResponse', () => {
    test('when there are multiple possible source languages and you are in Turkey', () => {
        const output = checkTranslationResponse(
            {
                data: [
                    [['çiğ et', 'carne cruda', null, null, 1, null, null, [[], []], [[], []]]],
                    null,
                    'ro',
                    null,
                    null,
                    null,
                    0.3571465,
                    [],
                    [['ro', 'it'], null, [0.3571465, 0.3513913], ['ro', 'it']],
                ],
            },
            'auto',
            'tr',
            'carne cruda',
        )
        expect(output.isAlternativeSourceExist).toBe(true)
        expect(output.alternativeSourceLangs.length).toBe(2)
        expect(output.sourceLanguage).toBe('auto')
        expect(output.sourceLang).toBe('Romanian & Moldavian & Moldovan')
        expect(output.targetLanguage).toBe('tr')
    })

    test('when autodetection is selected and the input is in Slovene, and you are in US', () => {
        const output = checkTranslationResponse(
            {
                data: [
                    [
                        [
                            'media devices have been blocked',
                            'predstavnostne naprave so bile blokirane',
                            null,
                            null,
                            3,
                            null,
                            null,
                            [[]],
                            [[['fe806a13c76d8f1e3792dc4e152ae298', 'tea_SouthSlavicB_bebsbghrsrsluk_en_2020q1.md']]],
                        ],
                    ],
                    null,
                    'sl',
                    null,
                    null,
                    null,
                    1.0,
                    [],
                    [['sl'], null, [1.0], ['sl']],
                ],
            },
            'auto',
            'en',
            'carne cruda',
        )
        expect(output.sourceLanguage).toBe('auto')
        expect(output.sourceLang).toBe('Slovene')
        expect(output.isAlternativeSourceExist).toBe(false)
        expect(output.targetLanguage).toBe('en')
    })

    test('when the source is selected as French and you are in Japan', () => {
        const output = checkTranslationResponse(
            {
                data: [
                    [
                        [
                            '時間は究極の敵',
                            "Le temps est l'ennemi ultime",
                            null,
                            null,
                            3,
                            null,
                            null,
                            [[], []],
                            [
                                [['6b11bd6ba9341f0271941e7df664d056', 'fr_en_2019q4.md']],
                                [['4db6eb5e1ad4b2d8a5de46fc1adb83d2', 'en_ja_2020q1.md']],
                            ],
                        ],
                    ],
                    null,
                    'fr',
                    null,
                    null,
                    null,
                    null,
                    [],
                ],
            },
            'fr',
            'ja',
            "Le temps est l'ennemi ultime",
        )
        expect(output.sourceLanguage).toBe('fr')
        expect(output.sourceLang).toBe('French')
        expect(output.targetLanguage).toBe('ja')
        expect(output.isAlternativeSourceExist).toBe(false)
    })
})
