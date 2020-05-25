const getProductsInSet = (request, response) =>
  new Promise(async (resolve) => {
    // Imports the Google Cloud client library
    const vision = require("@google-cloud/vision");

    // Creates a client
    const client = new vision.ProductSearchClient();

    // Google cloud project id and region name
    const projectId = "brilliant-era-276800";
    const location = "europe-west1";

    // takes the productSetId from the url of request
    var str = request.url;
    var n = str.indexOf("=");
    var n1 = str.indexOf("&");
    var first = str.substring(n + 1, str.length);
    const productSetId = first;

    // gets the product set path
    const productSetPath = client.productSetPath(
      projectId,
      location,
      productSetId
    );

    // HTML Text
    var mytext = "<h1> Products </h1>";

    // function for listing products in a product set
    client
      .listProductsInProductSet({ name: productSetPath })
      .then((responses) => {
        const productsOfSet = responses[0];
        // checks whether there is a product in the set
        if (productsOfSet.length == 0) {
          response.on("finish", resolve);
          response.send({ text: "empty set" });
          return;
        }
        for (const product of productsOfSet) {
          // adds each product in product set as a list element to the HTML text
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

          /*const formattedParent = client.productPath(projectId, location, product.name.split('/').pop());
          client.listReferenceImages({parent: formattedParent}).then(responses => {
            const images = responses[0];
          for (const image of images) {
           console.log(product.name);   
           console.log(image.uri); 
          
          }).catch(err => {
            console.error(err);
          }); */
        }

        // sends response text to HTML page
        if (mytext !== "<h1> Products </h1>") {
          response.on("finish", resolve);
          response.send("<p>" + mytext + "</p>");
        }
      })
      .catch((err) => {
        console.error(err);
      });
  });
module.exports = getProductsInSet;
