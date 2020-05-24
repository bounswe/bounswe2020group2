const getSets = (request, response) =>
  new Promise(async (resolve) => {
    // Imports the Google Cloud client library
    const vision = require("@google-cloud/vision");

    // Creates a client
    const client = new vision.ProductSearchClient();

    // Google cloud project id and region name
    const projectId = "brilliant-era-276800";
    const location = "europe-west1";

    // Resource path that represents Google Cloud Platform location.
    const formattedParent = client.locationPath(projectId, location);

    // HTML text
    var mytext = "<h1> Sets </h1>";

    // function for listing product sets
    client
      .listProductSets({ parent: formattedParent })
      .then((responses) => {
        const sets = responses[0];
        // checks whether there is a set
        if (sets.length == 0) {
          response.on("finish", resolve);
          response.send({ text: "there is no set" });
          return;
        }
        // adds names of each set to HTML text
        for (const set of sets) {
          mytext += "<li>" + set.name + "</li>";
        }

        // sending response text to HTML page
        if (mytext !== "<h1> Sets </h1>") {
          response.on("finish", resolve);
          response.send("<p>" + mytext + "</p>");
        }
      })
      .catch((err) => {
        // shows error message
        console.error(err);
      });
  });

module.exports = getSets;
