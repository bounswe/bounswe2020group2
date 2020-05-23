exports.getTop10DataFor = function (response) {
    var i;
    var stats = {}
    for (i = 0; i < response.data.length; i++) 
    {
        stats[response.data[i].country] = response.data[i].deceased
    }
    //convert to an array
    var stats_arr = Object.keys(stats).map(function(key) {
        return [key, stats[key]];
      });
    //take top 10 countries with respect to death numbers
    var max_ten_countries = stats_arr.sort(function(first, second) {
        return second[1] - first[1];
      }).slice(0, 10);
    //extract top 10 country names
    var keys1 = Object.keys(max_ten_countries).map(function(key){
        return max_ten_countries[key][0];
    });
    //extract top 10 death numbers
    var values1 = Object.keys(max_ten_countries).map(function(key){
        return max_ten_countries[key][1];
    });
    return {
        keys: keys1,
        values: values1
    }
}