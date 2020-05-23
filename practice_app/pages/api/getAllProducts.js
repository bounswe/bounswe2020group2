const getAllProducts = (req,res) => new Promise(
    async resolve => { 
   // Imports the Google Cloud client library
   const vision = require('@google-cloud/vision');

   // Creates a client
   const client = new vision.ProductSearchClient();
 
   // Google cloud project id and region name
   const projectId = 'brilliant-era-276800';
   const location = 'europe-west1';

// Resource path that represents Google Cloud Platform location.
const formattedParent = client.locationPath(projectId, location);

// Initializes HTML text
var mytext = '<h1> Products </h1>';  

// Calls product client of Google Product Search API via listProducts
client.listProducts({parent: formattedParent})
  .then(responses => {
    const products = responses[0];
    // checks whether there is a product
    if(products.length==0) {
        response.on("finish", resolve);
        response.send({"text": "There is no products"})
        return;
    }
    // Adds names of each product to HTML text
    for (const product of products) {
      mytext += '<li>' + product.name + '</li>';
    }
    
    // Sends response text to HTML page
    if(mytext!=='<h1> Products </h1>') {
    response.on("finish", resolve);
    response.send('<p>' +mytext +'</p>');
    }


  })
  .catch(err => {
    // Shows error message
    console.error(err);
  });
})
module.exports = getAllProducts;