// Imports the Google Cloud client library
const vision = require("@google-cloud/vision");
// Id of the getflix in Google Cloud Storage
const projectId = "brilliant-era-276800";
// Location of the getflix in Google Cloud Storage
const location = "europe-west1";
// Imports file system module to read images
const fs = require("fs");

function decodeBase64Image(dataString) {
  var matches = dataString.match(/^data:([A-Za-z-+\/]+);base64,(.+)$/),
    response = {};

  if (matches.length !== 3) {
    return new Error("Invalid input string");
  }

  response.type = matches[1];
  response.data = new Buffer(matches[2], "base64");

  return response;
}

module.exports = async function (req, res) {
  // Creates a product client
  const productSearchClient = new vision.ProductSearchClient();
  // Creates an image client
  const imageAnnotatorClient = new vision.ImageAnnotatorClient();

  const {
    setId: productSetId,
    category: productCategory,
    imageBase64,
    filter,
  } = req.body;

  // Determines the path of the product set via 'productSetPath' function of Google Product Search API
  const productSetPath = productSearchClient.productSetPath(
    projectId,
    location,
    productSetId
  );
  // The image data as base64-encoded text
  const content = decodeBase64Image(imageBase64).data;

  // fs.writeFileSync('./image.jpg', content)

  // const content = fs.readFileSync(filePath, 'base64')
  // Initializes the request object with the corresponding form.
  const request = {
    image: { content: content },
    features: [{ type: "PRODUCT_SEARCH" }],
    imageContext: {
      productSearchParams: {
        productSet: productSetPath,
        productCategories: [productCategory],
        filter: filter,
      },
    },
  };

  // Async function call to image client via it's 'batchAnnotateImages'
  // function
  try {
    const [response] = await imageAnnotatorClient.batchAnnotateImages({
      requests: [request],
    });

    let results = response["responses"][0]["productSearchResults"];
    // Checks whether results is empty or not
    if (!results)
      return res.json({
        statusCode: 404,
        statusText: "Results cannot be found.",
      });

    // Contains all products similar to image given by end user
    results = results["results"];

    const json = results.map((result) => {
      return {
        id: result.product.name.split("/").pop(-1),
        name: result.product.displayName,
        description: result.product.description,
        category: result.product.productCategory,
        score: result.score,
      };
    });
    res.json(json);
  } catch (err) {
    console.log(err);
    res.json({
      statusCode: 400,
      statusText: "The size of page is greater than 100 or less than 1.",
    });
  }
};
