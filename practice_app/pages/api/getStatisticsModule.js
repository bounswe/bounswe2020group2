exports.checking = function (response, countryName) {
    let isCountryNull = 'no'
    if (countryName == null) {
        isCountryNull = 'yes'
        return { isCountryNull }
    }
    if (response == null) {
        return { isCountryNull, answered1: 'no' }
    }
    let i
    let deathToll2
    let recovered2
    let answered2
    let infected2

    for (i = 0; i < response.data.length; i++) {
        if (response.data[i].country == countryName) {
            answered2 = 'yes'
            deathToll2 = response.data[i].deceased
            recovered2 = response.data[i].recovered
            infected2 = response.data[i].infected

            break
        }
    }

    return {
        deathToll1: deathToll2,
        recovered1: recovered2,
        answered1: answered2,
        infected1: infected2,
        isCountryNull,
    }
}
