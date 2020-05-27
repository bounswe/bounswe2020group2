const axios = require("axios");
// Imports the Google Cloud client library
const vision = require("@google-cloud/vision");

// Id of the getflix in google cloud storage
const projectId = "brilliant-era-276800";
// Location of the getflix in google cloud storage
const location = "europe-west1";
const client = new vision.ProductSearchClient();
async function createProduct(req, res) {
  const requestType = req.body.type;

  // Creates a product and then adds it to the product set
  if (requestType == "product") {
    const {
      id: productId, // Display name of the product
      name: productDisplayName, // Category of the product
      category: productCategory, // Id of the product set
      setId: productSetId, // Description of the product
      description: productDesc, // Color label of the product
      color: color, // Style label of the product
      style: style, // Gets body of request
    } = req.body;

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

    // Creates the product via 'createProduct' method of the Google Product
    // Search API

    await client.createProduct(createRequest).catch((err) => {
      res.json({
        statusCode: 400,
        statusText: "The product or the product set does not exist.",
      });
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
  }
  // Creates corresponding image of the product
  else if (requestType == "refImage") {
    // Gets body of the request
    const productId = req.body.id;
    // Determines the path of the product via 'productPath' function of Google Product Search API
    const formattedParent = client.productPath(projectId, location, productId);
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

async function updateProduct(req, res) {
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
  const [updatedProduct] = await client.updateProduct(request).catch((err) => {
    // Two types of possible errors, which are NOT_FOUND and NOT_VALID
    if (err == "NOT_FOUND") {
      res.json({ statusCode: 400, statusText: "The product does not exist." });
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

async function deleteProduct(req, res) {
  const requestType = req.body.type;
  // Deletes the product
  if (requestType == "product") {
    // Gets id of the product from the body of the request
    const productId = req.body.id;
    // Resource path that represents Google Cloud Platform location.
    const formattedName = client.productPath(projectId, location, productId);
    // Calls deleteProduct function of Google Product Search API
    client.deleteProduct({ name: formattedName }).catch((err) => {
      res.json({ statusCode: 404, statusText: "The product does not exist." });
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
    res.json({ statusCode: 200, statusText: "The product is deleted." });
  }
}

module.exports = async function (req, res) {
  // Creates a client
  const client = new vision.ProductSearchClient();

  // to config file
  switch (req.method) {
    case "POST":
      return createProduct(req, res);
      break;
    case "PATCH":
      return updateProduct(req, res);
      break;
    case "DELETE":
      return deleteProduct(req, res);
      break;
  }
};
