exports.getTop10DataFor = function (response) {
    let i
    const stats = {}
    for (i = 0; i < response.data.length; i++) {
        stats[response.data[i].country] = response.data[i].deceased
    }
    // convert to an array
    const stats_arr = Object.keys(stats).map(key => [key, stats[key]])
    // take top 10 countries with respect to death numbers
    const max_ten_countries = stats_arr.sort((first, second) => second[1] - first[1]).slice(0, 10)
    // extract top 10 country names
    const keys1 = Object.keys(max_ten_countries).map(key => max_ten_countries[key][0])
    // extract top 10 death numbers
    const values1 = Object.keys(max_ten_countries).map(key => max_ten_countries[key][1])
    return {
        keys: keys1,
        values: values1,
    }
}
