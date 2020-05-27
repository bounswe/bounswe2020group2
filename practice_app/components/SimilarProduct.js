import React, { useState } from "react";
import axios from "axios";
const CSS_COLOR_NAMES = [
  "AliceBlue",
  "AntiqueWhite",
  "Aqua",
  "Aquamarine",
  "Azure",
  "Beige",
  "Bisque",
  "Black",
  "BlanchedAlmond",
  "Blue",
  "BlueViolet",
  "Brown",
  "BurlyWood",
  "CadetBlue",
  "Chartreuse",
  "Chocolate",
  "Coral",
  "CornflowerBlue",
  "Cornsilk",
  "Crimson",
  "Cyan",
  "DarkBlue",
  "DarkCyan",
  "DarkGoldenRod",
  "DarkGray",
  "DarkGrey",
  "DarkGreen",
  "DarkKhaki",
  "DarkMagenta",
  "DarkOliveGreen",
  "DarkOrange",
  "DarkOrchid",
  "DarkRed",
  "DarkSalmon",
  "DarkSeaGreen",
  "DarkSlateBlue",
  "DarkSlateGray",
  "DarkSlateGrey",
  "DarkTurquoise",
  "DarkViolet",
  "DeepPink",
  "DeepSkyBlue",
  "DimGray",
  "DimGrey",
  "DodgerBlue",
  "FireBrick",
  "FloralWhite",
  "ForestGreen",
  "Fuchsia",
  "Gainsboro",
  "GhostWhite",
  "Gold",
  "GoldenRod",
  "Gray",
  "Grey",
  "Green",
  "GreenYellow",
  "HoneyDew",
  "HotPink",
  "IndianRed",
  "Indigo",
  "Ivory",
  "Khaki",
  "Lavender",
  "LavenderBlush",
  "LawnGreen",
  "LemonChiffon",
  "LightBlue",
  "LightCoral",
  "LightCyan",
  "LightGoldenRodYellow",
  "LightGray",
  "LightGrey",
  "LightGreen",
  "LightPink",
  "LightSalmon",
  "LightSeaGreen",
  "LightSkyBlue",
  "LightSlateGray",
  "LightSlateGrey",
  "LightSteelBlue",
  "LightYellow",
  "Lime",
  "LimeGreen",
  "Linen",
  "Magenta",
  "Maroon",
  "MediumAquaMarine",
  "MediumBlue",
  "MediumOrchid",
  "MediumPurple",
  "MediumSeaGreen",
  "MediumSlateBlue",
  "MediumSpringGreen",
  "MediumTurquoise",
  "MediumVioletRed",
  "MidnightBlue",
  "MintCream",
  "MistyRose",
  "Moccasin",
  "NavajoWhite",
  "Navy",
  "OldLace",
  "Olive",
  "OliveDrab",
  "Orange",
  "OrangeRed",
  "Orchid",
  "PaleGoldenRod",
  "PaleGreen",
  "PaleTurquoise",
  "PaleVioletRed",
  "PapayaWhip",
  "PeachPuff",
  "Peru",
  "Pink",
  "Plum",
  "PowderBlue",
  "Purple",
  "RebeccaPurple",
  "Red",
  "RosyBrown",
  "RoyalBlue",
  "SaddleBrown",
  "Salmon",
  "SandyBrown",
  "SeaGreen",
  "SeaShell",
  "Sienna",
  "Silver",
  "SkyBlue",
  "SlateBlue",
  "SlateGray",
  "SlateGrey",
  "Snow",
  "SpringGreen",
  "SteelBlue",
  "Tan",
  "Teal",
  "Thistle",
  "Tomato",
  "Turquoise",
  "Violet",
  "Wheat",
  "White",
  "WhiteSmoke",
  "Yellow",
  "YellowGreen",
];

function getFormValues(event) {
  const formData = new FormData(event.target);
  event.preventDefault();

  const inputs = {};

  for (let [key, value] of formData.entries()) {
    inputs[key] = value;
  }

  return inputs;
}

export default function SimilarProduct({}) {
  const [similarProducts, setSimilarProducts] = useState(undefined);
  const [allProducts, setAllProducts] = useState(undefined);

  const onCreateProductSet = async (event) => {
    const inputs = getFormValues(event);

    // checks whether all the inputs are given
    if (
      inputs.product_set_title === "" ||
      inputs.product_set_display_title === ""
    ) {
      alert("please fill set id and name");
      return;
    }

    // sends the input values to getProductSet function by using axios.post
    axios.post("./api/getProductCatalog", {
      id: inputs.product_set_title,
      name: inputs.product_set_display_title,
      type: "productSet",
    });

    // informs the user
    alert("product set has created successfully");
  };
  const toBase64 = (file) =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });

  const onGetSimilarProducts = async (event) => {
    const { setId, category, file, filter } = getFormValues(event);
    const imageBase64 = await toBase64(file);
    // checks whether the style given by user is valid
    if (
      ![
        "style=women",
        "style=men",
        "style=children",
        "style=home",
        "style=general",
        "stylepackaged",
      ].includes(filter)
    ) {
      alert(
        "Invalid style name. Valid ones are: women, men, children, home, general and packaged"
      );
      return;
    }
    const { data } = await axios.post("./api/getSimilarProducts", {
      setId,
      category,
      imageBase64,
      filter,
    });
    setSimilarProducts(data);
  };

  // onclick method for adding a product to a product set
  const onCreateProduct = async (event) => {
    const inputs = getFormValues(event);
    // checks whether all the inputs are given
    if (
      inputs.product_title === "" ||
      inputs.product_display_title === "" ||
      inputs.category_title === "" ||
      inputs.product_set_id_title === "" ||
      inputs.description_title === "" ||
      inputs.color_title === "" ||
      inputs.style_title === ""
    ) {
      alert(
        "please fill product set id, product id, name, category, description, color and style"
      );
      return;
    }

    // checks whether the category given by user is valid
    if (
      ![
        "apparel",
        "homegoods",
        "toys",
        "apparel-v2",
        "homegoods-v2",
        "toys-v2",
        "packagedgoods-v1",
      ].includes(inputs.category_title)
    ) {
      alert(
        "Invalid category name. Valid ones are: apparel, homegoods, toys, apparel-v2, homegoods-v2, toys-v2 and packagedgood-v1"
      );
      return;
    }
    if (
      !CSS_COLOR_NAMES.map((v) => v.toLowerCase()).includes(
        inputs.color_title.toLowerCase()
      )
    ) {
      alert("The color name must be one of the CSS color.");
      return;
    }

    // checks whether the style given by user is valid
    if (
      !["women", "men", "children", "home", "general", "packaged"].includes(
        inputs.style_title
      )
    ) {
      alert(
        "Invalid style name. Valid ones are: women, men, children, home, general and packaged"
      );
      return;
    }

    // sends the input values to getProductSet function by using axios.post
    axios.post("./api/getProductCatalog", {
      id: inputs.product_title,
      name: inputs.product_display_title,
      category: inputs.category_title,
      type: "product",
      setId: inputs.product_set_id_title,
      description: inputs.description_title,
      color: inputs.color_title,
      style: inputs.style_title,
    });
    // informs the user
    alert("Product has added succesfully");
  };

  // onclick method for updating a product
  const onUpdate = async (event) => {
    const inputs = getFormValues(event);
    // checks whether all the inputs are given
    if (
      inputs.updated_title === "" ||
      inputs.key_title === "" ||
      inputs.value_title === ""
    ) {
      alert("please fill product id, key and key value");
      return;
    }
    if (
      inputs.key_title.toLowerCase() == "style" &&
      !["women", "men", "children", "home", "general", "packaged"].includes(
        inputs.value_title
      )
    ) {
      alert(
        "Invalid style name. Valid ones are: women, men, children, home, general and packaged"
      );
      return;
    }
    if (
      inputs.key_title.toLowerCase() == "color" &&
      !CSS_COLOR_NAMES.map((v) => v.toLowerCase()).includes(
        inputs.value_title.toLowerCase()
      )
    ) {
      alert("The color name must be one of the CSS color.");
      return;
    }

    // sends the input values to getProductSet function by using axios.patch
    try {
      await axios.patch("./api/getProductCatalog", {
        id: inputs.updated_title,
        key: inputs.key_title,
        value: inputs.value_title,
      });
      // informs the user
      alert("product has updated succesfully");
    } catch (err) {
      console.log(err);
    }
  };

  const onDeleteProduct = async (event) => {
    const inputs = getFormValues(event);
    // checks whether all the input is given
    if (inputs.delete_title === "") {
      alert("please fill product id");
      return;
    }

    // sends the input value to getProdcutSet function by using axios.delete
    await axios.delete("./api/getProductCatalog", {
      data: {
        type: "product",
        id: inputs.delete_title,
      },
    });

    // informs the user
    alert("product has deleted succesfully");
  };
  // onclick method for deleting a product set
  const onDeleteProductSet = async (event) => {
    const inputs = getFormValues(event);
    // checks whether the input is given
    if (inputs.delete_set_title === "") {
      alert("please fill product set id");
      return;
    }

    // sends the input value to getProdcutSet function by using axios.delete
    try {
      await axios.delete("./api/getProductCatalog", {
        data: {
          type: "productSet",
          setId: inputs.delete_set_title,
        },
      });
      // informs the user
      alert("product set has deleted succesfully");
    } catch (err) {
      console.log(err);
    }
  };

  // onclick method for adding a reference image for a product
  const onRefImg = async (event) => {
    const inputs = getFormValues(event);

    // checks whether all the input values are given
    if (inputs.img_product_title === "" || inputs.img_uri_title === "") {
      alert("Please fill product id and reference uri");
      return;
    }

    // sends the input value to getProdcutSet function by using axios.post
    await axios
      .post("./api/getProductCatalog", {
        uri: inputs.img_uri_title,
        type: "refImage",
        id: inputs.img_product_title,
      })
      .then(function (response) {
        resultStatus = response.status;
        resultStatusText = response.statusText;
      });

    // informs the user
    alert("product image has added succesfully");
  };

  return (
    <div className="container">
      <h1>Are you vendor?</h1>
      <h2 class="vendor">Do you want to create your own product set?</h2>
      <form onSubmit={onCreateProductSet}>
        <label>
          SetID:
          <input
            type="text"
            placeholder="Add set id"
            name="product_set_title"
            required
          />
        </label>
        <label>
          SetName:
          <input
            type="text"
            placeholder="Add name"
            name="product_set_display_title"
            required
          />
        </label>
        <input type="submit" value="Create" />
      </form>

      <h2 class="vendor">
        Do you want to add your product into your product set?
      </h2>
      <form onSubmit={onCreateProduct}>
        <label>
          ProductID:
          <input
            type="text"
            placeholder="Add product id"
            name="product_title"
            required
          />
        </label>
        <label>
          SetID:
          <input
            type="text"
            placeholder="Add set id"
            name="product_set_id_title"
            required
          />
        </label>
        <label>
          ProductName:
          <input
            type="text"
            placeholder="Add name"
            name="product_display_title"
            required
          />
        </label>
        <label>
          Category:
          <input
            type="text"
            placeholder="Add category"
            name="category_title"
            required
          />
        </label>
        <label>
          Description:
          <input
            type="text"
            placeholder="Write description"
            name="description_title"
            required
          />
        </label>
        <label>
          Color:
          <input
            type="text"
            placeholder="Write color"
            name="color_title"
            required
          />
        </label>
        <label>
          Style:
          <input
            type="text"
            placeholder="Write style"
            name="style_title"
            required
          />
        </label>
        <input type="submit" value="Add" />
      </form>
      <h2>
        We are curious about how your product looks like! Do you want to add its
        image?
      </h2>
      <form onSubmit={onRefImg}>
        <label>
          ProductID:
          <input
            type="text"
            placeholder="Add product id"
            name="img_product_title"
            required
          />
        </label>
        <label>
          ReferenceUri:
          <input
            type="text"
            placeholder="Add reference image"
            name="img_uri_title"
            required
          />
        </label>
        <input type="submit" value="Add" />
      </form>
      <h2>Aren't you happy with your product? Try to update its labels!</h2>
      <form onSubmit={onUpdate}>
        <label>
          ProductID:
          <input
            type="text"
            placeholder="Add product id"
            name="updated_title"
            required
          />
        </label>
        <label>
          Key:
          <input type="text" placeholder="Add key" name="key_title" required />
        </label>
        <label>
          KeyValue:
          <input
            type="text"
            placeholder="Add value"
            name="value_title"
            required
          />
        </label>
        <input type="submit" value="Update" />
      </form>

      <h2>Say goodbye to your product!</h2>
      <form onSubmit={onDeleteProduct}>
        <label>
          ProductID:
          <input
            type="text"
            placeholder="Add product id"
            name="delete_title"
            required
          />
        </label>
        <input type="submit" value="Delete" />
      </form>

      <h2>We will miss you! Are you sure about deleting your product set?</h2>
      <form onSubmit={onDeleteProductSet}>
        <label>
          ProductSetID:
          <input
            type="text"
            placeholder="Add product set id"
            name="delete_set_title"
            required
          />
        </label>
        <input type="submit" value="Delete" />
      </form>
      <h1>Are you customer?</h1>
      <h2>
        Do you want to find out which brands are in Getflix? Let's list all
        product sets!
      </h2>
      <form target="_blank" action="/api/getSets" method="GET">
        <input type="submit" value="List" />
      </form>
      <h2>
        Hmm! There are lots of products in our website! Take a look all of them!
      </h2>
      <form target="_blank" action="/api/getAllProducts" method="GET">
        <input type="submit" value="List" />
      </form>
      <h2>
        Are you interested only in products of your favorite vendor? Really??{" "}
      </h2>
      <form target="_blank" action="/api/getProductsInSet" method="GET">
        <label>
          SetID:
          <input type="text" placeholder="Add set id" name="setId" required />
        </label>
        <input type="submit" value="List" />
      </form>

      <h2>Do you want to filter them?</h2>
      <form target="_blank" action="/api/getProductsByCategory" method="GET">
        <label>
          SetID:
          <input type="text" placeholder="Add set id" name="setId" required />
        </label>
        <label>
          Category:
          <input
            type="text"
            placeholder="Add category name"
            name="category"
            required
          />
        </label>
        <input type="submit" value="Filter" />
      </form>

      <h2 class="special">
        Let's play a game! You give us an image of a product, we give our
        products sorted by similarity percentages!
      </h2>
      <form onSubmit={onGetSimilarProducts}>
        <label>
          ProductSetID:
          <input
            type="text"
            placeholder="Add product set id"
            name="setId"
            required
          />
        </label>
        <label>
          Category:
          <input
            type="text"
            placeholder="Add category"
            name="category"
            required
          />
        </label>
        <label>
          File:
          <input
            type="file"
            placeholder="Add path of file"
            name="file"
            required
          />
        </label>
        <label>
          Filter:
          <input type="text" placeholder="Add filter" name="filter" required />
        </label>
        <input type="submit" value="List" />
      </form>
      {similarProducts &&
        similarProducts.map((similarProduct) => {
          return (
            <ul key={similarProduct.id}>
              <li>Product id: {similarProduct.id} </li>
              <li>Product name: {similarProduct.name} </li>
              <li>Product description: {similarProduct.description} </li>
              <li>Product category: {similarProduct.category} </li>
              <li>Product similarity score: {similarProduct.score} </li>
            </ul>
          );
        })}
    </div>
  );
}
