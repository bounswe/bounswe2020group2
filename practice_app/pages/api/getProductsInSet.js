export default (request, response) =>
  new Promise(async (resolve) => {
    // Imports the Google Cloud client library
    const vision = require("@google-cloud/vision");

    // Creates a client
    const client = new vision.ProductSearchClient();

    // The id of the project in Google Cloud Storage
    const projectId = "brilliant-era-276800";
    // The location of the project in Google Cloud Storage
    const location = "europe-west1";

    // Takes the productSetId from the url of request
    var str = request.url;
    var n = str.indexOf("=");
    var n1 = str.indexOf("&");
    var first = str.substring(n + 1, str.length);
    const productSetId = first;

    // Gets the product set path
    const productSetPath = client.productSetPath(
      projectId,
      location,
      productSetId
    );

    // HTML Text
    var mytext = "<h1> Products </h1>";

    // Function for listing products in a product set
    client
      .listProductsInProductSet({ name: productSetPath })
      .then((responses) => {
        const productsOfSet = responses[0];
        // Checks whether there is a product in the set
        if (productsOfSet.length == 0) {
          response.on("finish", resolve);
          response.send("<p>There is no product in " + productSetId + "</p>");
          return;
        }
        for (const product of productsOfSet) {
          // Adds each product in product set as a list element to the HTML text
          mytext += "<li>" + "Product name: " + product.name + ", ";
          mytext += "Product id: " + product.name.split("/").pop() + ", ";
          mytext += "Product display name: " + product.displayName + ", ";
          mytext += "Product description: " + product.description + ", ";
          mytext += "Product category: " + product.productCategory + ", ";

          if (product.productLabels.length) {
            mytext += "Product labels: ";
            product.productLabels.forEach((productLabel) => {
              mytext += productLabel.key + ": " + productLabel.value + ", ";
            });
          }
          mytext += "</li>";

          // Sends response text to HTML page
          if (mytext !== "<h1> Products </h1>") {
            response.on("finish", resolve);
            response.send("<p>" + mytext + "</p>");
          }
        }
      })
      .catch((err) => {
        response.on("finish", resolve);
        response.send("<p>There is no product in " + productSetId + "</p>");
      });
  });
