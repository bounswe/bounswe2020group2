const axios = require("axios");
// Imports the Google Cloud client library
const vision = require("@google-cloud/vision");

module.exports = async function (req, res) {
  // Creates a client
  const client = new vision.ProductSearchClient();
  // Id of the getflix in google cloud storage
  const projectId = "brilliant-era-276800";
  // Location of the getflix in google cloud storage
  const location = "europe-west1";
  // Method of the request
  const method = req.method;

  switch (method) {
    case "POST": {
      const requestType = req.body.type;
      // Creates a product and then adds it to the product set
      if (requestType == "product") {
        const productId = req.body.id;
        // Display name of the product
        const productDisplayName = req.body.name;
        // Category of the product
        const productCategory = req.body.category;
        // Id of the product set
        const productSetId = req.body.setId;
        // Description of the product
        const productDesc = req.body.description;
        // Color label of the product
        const color = req.body.color;
        // Style label of the product
        const style = req.body.style;
        // Resource path that represents Google Cloud Platform location.
        const locationPath = client.locationPath(projectId, location);
        // Initializes the product object
        const product = {
          displayName: productDisplayName,
          productCategory: productCategory,
          description: productDesc,
          productLabels: [
            {
              key: "color",
              value: color,
            },
            {
              key: "style",
              value: style,
            },
          ],
        };
        // Initializes the request object
        const createRequest = {
          parent: locationPath,
          product: product,
          productId: productId,
        };

        // Creates the product via 'createProduct' method of the Google Product Search API
        client.createProduct(createRequest).catch((err) => {
          res.json({ statusCode: 400, statusText: "hello" });
        });
        // Determines the path of the product via 'productPath' method of the Google Product Search API
        const productPath = client.productPath(projectId, location, productId);

        /* Determines the path of the product set the product will be added
                      via 'productPath' method of the Google Product Search API*/
        const productSetPath = client.productSetPath(
          projectId,
          location,
          productSetId
        );

        const addRequest = {
          name: productSetPath,
          product: productPath,
        };

        // Async function call to Google Product Search API in order to add product to the set
        await client.addProductToProductSet(addRequest).catch((err) => {
          res.json({
            statusCode: 400,
            statusText: "The product or the product set does not exist.",
          });
        });
        // Initializes both status message and status code of the succesful response

        //res.on("finish", resolve);
        res.json({
          statusCode: 200,
          statusText: "Product is added to the product set.",
        });
      }
      // Creates product set
      else if (requestType == "productSet") {
        // Gets body of the request
        const productSetId = req.body.id;
        const productSetDisplayName = req.body.name;
        // Resource path that represents Google Cloud Platform location.
        const locationPath = client.locationPath(projectId, location);
        // Initializes the product set object
        const productSet = {
          displayName: productSetDisplayName,
        };
        // Determines the request sended to the Google Product Search API
        const request = {
          parent: locationPath,
          productSet: productSet,
          productSetId: productSetId,
        };
        // Async function call to the Google Product Search API via it's function namely 'createProductSet'
        const [createdProductSet] = await client
          .createProductSet(request)
          .catch((err) => {
            res.json({
              statusCode: 400,
              statusText:
                "Display name is missing, or is longer than 4096 characters.",
            });
          });
        // Initializes both status message and status code of the succesful response
        res.json({ statusCode: 200, statusText: "Product set is created." });
        // res.on("finish", resolve);
      }
      // Creates corresponding image of the product
      else if (requestType == "refImage") {
        // Gets body of the request
        const productId = req.body.id;
        // Determines the path of the product via 'productPath' function of Google Product Search API
        const formattedParent = client.productPath(
          projectId,
          location,
          productId
        );
        // Initializes the object representing image
        const referenceImage = {
          // Uri of the image in the Google Cloud Storage
          uri: req.body.uri,
          name: formattedParent,
        };
        // Initializes the id of the image from that of the product
        const referenceImageId = productId;
        // Creates request object
        const request = {
          parent: formattedParent,
          referenceImage: referenceImage,
          referenceImageId: referenceImageId,
        };
        // Calls the product client of the Google Product Search API via 'createReferenceImage'
        client
          .createReferenceImage(request)
          .then((responses) => {
            // Succesful response
            res.json({
              statusCode: 200,
              statusText: "The reference image is created.",
            });
          })
          .catch((err) => {
            res.json({
              statusCode: 400,
              statusText:
                "The image uri or the product does not exist or the product has already image.",
            });
          });
      }
    }

    // Updates labels of the product existing in any one of the product set.
    case "PATCH": {
      // Gets the body of the request
      const productId = req.body.id;
      const key = req.body.key;
      const value = req.body.value;
      // Resource path that represents Google Cloud Platform location.
      const productPath = client.productPath(projectId, location, productId);
      // Product object in the form of the Google Product Search API
      const product = {
        name: productPath,
        productLabels: [
          {
            key: key,
            value: value,
          },
        ],
      };
      // Initializes the product's field which will be updated
      const updateMask = {
        paths: ["product_labels"],
      };
      // Update request form corresponding function of Google Product Search API
      const request = {
        product: product,
        updateMask: updateMask,
      };
      // Async function call to Google Search API via it's function namely 'updateProduct'
      const [updatedProduct] = await client
        .updateProduct(request)
        .catch((err) => {
          // Two types of possible errors, which are NOT_FOUND and NOT_VALID
          if (err == "NOT_FOUND") {
            res.json({
              statusCode: 400,
              statusText: "The product does not exist.",
            });
          } else {
            res.json({
              statusCode: 400,
              statusText:
                "display_name or the description is missing from the request or longer than 4096 characters or" +
                "category of product does already exist.",
            });
          }
        });
      // Succesful response
      res.json({
        statusCode: 200,
        statusText: `${updatedProduct.name} is updated.`,
      });
    }
    case "DELETE": {
      const requestType = req.body.type;
      // Deletes the product
      if (requestType == "product") {
        // Gets id of the product from the body of the request
        const productId = req.body.id;
        // Resource path that represents Google Cloud Platform location.
        const formattedName = client.productPath(
          projectId,
          location,
          productId
        );
        // Calls deleteProduct function of Google Product Search API
        client.deleteProduct({ name: formattedName }).catch((err) => {
          res.json({
            statusCode: 404,
            statusText: "The product does not exist.",
          });
        });
        // Succesful response
        res.json({ statusCode: 200, statusText: "The product is deleted." });
      }
      // Deletes the product set
      else if (requestType == "productSet") {
        // Gets id of the product set from the body of the request
        const productSetId = req.body.setId;
        // Resource path that represents Google Cloud Platform location.
        const formattedName = client.productSetPath(
          projectId,
          location,
          productSetId
        );
        // Calls deleteProductSet function of Google Product Search API
        client.deleteProductSet({ name: formattedName }).catch((err) => {
          res.json({
            statusCode: 404,
            statusText: "The product set does not exist.",
          });
        });
        // Succesful response

        res.json({
          statusCode: 200,
          statusText: "The product set is deleted.",
        });
      }
    }
  }
};
