exports.checking = function (val) {
    let cnull = 'no'
    let ansnull = 'yes'

    if (val.countryN == null) {
        cnull = 'yes'
        return { cnull, ansnull }
    }

    if (val.answer == null) {
        ansnull = 'no'
        return { cnull, ansnull }
    }

    return { cnull, ansnull }
}
