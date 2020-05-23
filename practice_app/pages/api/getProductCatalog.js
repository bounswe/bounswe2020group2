const getProductSet = (req, res) =>
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




        
    }
  });
module.exports = getProductSet;