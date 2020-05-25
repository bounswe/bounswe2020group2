const isoLanguages = require('./isoLanguages.json')

exports.checkTranslationResponse = function (response, sourceLanguage, targetLanguage, originalText) {
    const translation = response.data[0][0][0]
    const sourceText = response.data[0][0][1]
    const sourceLang = exports.getSourceLangName(response.data[2])
    const isAlternativeSourceExist = response.data[8] ? response.data[8][0].length > 1 : false

    const alternativeSourceLangs = []
    if (isAlternativeSourceExist) {
        for (let i = 0; i < response.data[8][0].length; i++) {
            alternativeSourceLangs.push(exports.getSourceLangName(response.data[8][0][i]))
        }
    }

    return {
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

exports.getSourceLangName = function (sl) {
    let sourceLangName = 'lang_not_found'
    const keys = Object.keys(isoLanguages)
    for (let i = 0; i < keys.length; i++) {
        if (sl == keys[i]) {
            sourceLangName = isoLanguages[keys[i]].name
        }
    }
    return sourceLangName
}
