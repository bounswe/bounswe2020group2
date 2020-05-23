const getProductCatalog = (req, res) =>
  new Promise(async (resolve) => {
    const axios = require("axios");
    // Imports the Google Cloud client library
    const vision = require("@google-cloud/vision");
    // Creates a client
    const client = new vision.ProductSearchClient();
    // Id of the getflix in google cloud storage
    const projectId = "brilliant-era-276800";
    // Location of the getflix in google cloud storage
    const location = "europe-west1";
    // Method of the request
    const method = req.method;

    switch (method) {
      case 'POST': {
        // create product function call
        
        // create product set function call

        // add reference image function call
        
      }
      case 'PATCH': {
        // update product function call
      
      }
      case 'DELETE': {
        // delete product function call

        // delete product set function call
      }  
        
    }
  });
module.exports = getProductCatalog;
