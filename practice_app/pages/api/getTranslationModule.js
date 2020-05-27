const isoConv = require('iso-language-converter')

exports.checkTranslationResponse = function (response, sourceLanguage, targetLanguage, originalText) {
    if (!originalText) {
        return {
            isInputValid: false,
        }
    }
    const translation = response.data[0][0][0]
    const sourceText = response.data[0][0][1]
    const sourceLang = isoConv(response.data[2], { from: 1, to: 'label' })
    const isAlternativeSourceExist = response.data[8] ? response.data[8][0].length > 1 : false

    const alternativeSourceLangs = []
    if (isAlternativeSourceExist) {
        for (let i = 0; i < response.data[8][0].length; i++) {
            alternativeSourceLangs.push(isoConv(response.data[2], { from: 1, to: 'label' }))
        }
    }

    return {
        isInputValid: true,
        translation,
        sourceLang,
        isAlternativeSourceExist,
        alternativeSourceLangs,
        sourceText,
        originalText,
        sourceLanguage,
        targetLanguage,
    }
}
